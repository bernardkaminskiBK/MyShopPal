package com.android10_kotlin.myshoppal.ui.activities

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android10_kotlin.myshoppal.R
import com.android10_kotlin.myshoppal.databinding.ActivityCartListBinding
import com.android10_kotlin.myshoppal.firestore.FirestoreClass
import com.android10_kotlin.myshoppal.models.CartItem
import com.android10_kotlin.myshoppal.ui.adapters.CartListAdapter

class CartListActivity : BaseActivity() {

    private lateinit var mBinding: ActivityCartListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityCartListBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setupToolbar()
    }

    private fun setupToolbar() {
        setSupportActionBar(mBinding.toolbarCartList)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        mBinding.toolbarCartList.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    fun successCartItemsList(cartList: ArrayList<CartItem>) {
        hideProgressDialog()

        if (cartList.size > 0) {
            mBinding.rvCartItemsList.visibility = View.VISIBLE
            mBinding.llCheckout.visibility = View.VISIBLE
            mBinding.tvNoCartItemFound.visibility = View.GONE

            setCartListRecyclerView(cartList)

            var subTotal: Double = 0.0

            for(item in cartList) {
                val price = item.price.toDouble()
                val quantity = item.cart_quantity.toInt()

                subTotal += (price * quantity)
            }

            mBinding.tvSubTotal.text = "$$subTotal"
            mBinding.tvShippingCharge.text = "$10.0"

            if(subTotal > 0) {
                mBinding.llCheckout.visibility = View.VISIBLE

                val total = subTotal + 10
                mBinding.tvTotalAmount.text = "$$total"
            } else {
                mBinding.llCheckout.visibility = View.GONE
            }

        } else {
            mBinding.rvCartItemsList.visibility = View.GONE
            mBinding.llCheckout.visibility = View.GONE
            mBinding.tvNoCartItemFound.visibility = View.VISIBLE
        }


    }

    private fun setCartListRecyclerView(cartList: ArrayList<CartItem>) {
        mBinding.rvCartItemsList.layoutManager = LinearLayoutManager(this)
        mBinding.rvCartItemsList.setHasFixedSize(true)

        val adapter = CartListAdapter(this)
        mBinding.rvCartItemsList.adapter = adapter
        adapter.show(cartList)
    }

    private fun getCartItemsList() {
        showProgressDialog(getString(R.string.please_wait))
        FirestoreClass().getCartList(this)
    }

    override fun onResume() {
        super.onResume()
        getCartItemsList()
    }

}