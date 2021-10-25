package com.android10_kotlin.myshoppal.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android10_kotlin.myshoppal.R
import com.android10_kotlin.myshoppal.databinding.ActivityCartListBinding
import com.android10_kotlin.myshoppal.firestore.FirestoreClass
import com.android10_kotlin.myshoppal.models.CartItem
import com.android10_kotlin.myshoppal.models.Product
import com.android10_kotlin.myshoppal.ui.adapters.CartListAdapter
import com.android10_kotlin.myshoppal.utils.Constants

class CartListActivity : BaseActivity() {

    private lateinit var mBinding: ActivityCartListBinding
    private lateinit var mProductsList: ArrayList<Product>
    private lateinit var mCartListItems: ArrayList<CartItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityCartListBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setupToolbar()

        mBinding.btnCheckout.setOnClickListener {
            moveToAddressListActivity()
        }
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

        for (product in mProductsList) {
            for (cartItem in cartList) {
                if (product.id == cartItem.product_id) {

                    cartItem.stock_quantity = product.stock_quantity

                    if (product.stock_quantity.toInt() == 0) {
                        cartItem.cart_quantity = product.stock_quantity
                    }
                }
            }
        }

        mCartListItems = cartList

        if (cartList.size > 0) {
            mBinding.rvCartItemsList.visibility = View.VISIBLE
            mBinding.llCheckout.visibility = View.VISIBLE
            mBinding.tvNoCartItemFound.visibility = View.GONE

            setCartListRecyclerView(cartList)

            var subTotal: Double = 0.0

            for (item in mCartListItems) {
                val availableQuantity = item.stock_quantity.toInt()
                if (availableQuantity > 0) {
                    val price = item.price.toDouble()
                    val quantity = item.cart_quantity.toInt()

                    subTotal += (price * quantity)
                }
            }

            mBinding.tvSubTotal.text = "$subTotal €"
            mBinding.tvShippingCharge.text = "10.0 €"

            if (subTotal > 0) {
                mBinding.llCheckout.visibility = View.VISIBLE

                val total = subTotal + 10
                mBinding.tvTotalAmount.text = "$total €"
            } else {
                mBinding.llCheckout.visibility = View.GONE
            }

        } else {
            mBinding.rvCartItemsList.visibility = View.GONE
            mBinding.llCheckout.visibility = View.GONE
            mBinding.tvNoCartItemFound.visibility = View.VISIBLE
        }

    }

    private fun moveToAddressListActivity() {
        val intent = Intent(this@CartListActivity, AddressListActivity::class.java)
        intent.putExtra(Constants.EXTRA_SELECT_ADDRESS, true)
        startActivity(intent)
    }

    fun successProductsListFromFireStore(productList: ArrayList<Product>) {
        hideProgressDialog()
        mProductsList = productList

        getCartItemsList()
    }

    private fun setCartListRecyclerView(cartList: ArrayList<CartItem>) {
        mBinding.rvCartItemsList.layoutManager = LinearLayoutManager(this)
        mBinding.rvCartItemsList.setHasFixedSize(true)

        val adapter = CartListAdapter(this)
        mBinding.rvCartItemsList.adapter = adapter
        adapter.show(cartList)
    }

    private fun getProductItemsList() {
        showProgressDialog(getString(R.string.please_wait))
        FirestoreClass().getAllProductsList(this)
    }

    private fun getCartItemsList() {
        FirestoreClass().getCartList(this)
    }

    override fun onResume() {
        super.onResume()
        getProductItemsList()
    }

    fun itemRemovedSuccess() {
        hideProgressDialog()
        Toast.makeText(this, "Item was successfully removed", Toast.LENGTH_SHORT).show()
        getCartItemsList()
    }

    fun itemUpdateSuccess() {
        hideProgressDialog()
        getCartItemsList()
    }

}