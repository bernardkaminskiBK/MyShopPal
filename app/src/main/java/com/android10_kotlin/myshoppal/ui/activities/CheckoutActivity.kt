package com.android10_kotlin.myshoppal.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android10_kotlin.myshoppal.R
import com.android10_kotlin.myshoppal.databinding.ActivityCheckoutBinding
import com.android10_kotlin.myshoppal.firestore.FirestoreClass
import com.android10_kotlin.myshoppal.models.Address
import com.android10_kotlin.myshoppal.models.CartItem
import com.android10_kotlin.myshoppal.models.Order
import com.android10_kotlin.myshoppal.models.Product
import com.android10_kotlin.myshoppal.ui.adapters.CartListAdapter
import com.android10_kotlin.myshoppal.utils.Constants
import java.util.ArrayList

class CheckoutActivity : BaseActivity() {

    private lateinit var mBinding: ActivityCheckoutBinding
    private lateinit var mProductsList: ArrayList<Product>
    private lateinit var mCartItemsList: ArrayList<CartItem>

    private var mSubTotal: Double = 0.0
    private var mTotalAmount: Double = 0.0

    private var mAddressDetails: Address? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setupToolbar()
        checkIfHasExtra()
        setAddressDetailsToUI()

        getProductsList()

        mBinding.btnPlaceOrder.setOnClickListener {
            placeAnOrder()
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(mBinding.toolbarCheckoutActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        mBinding.toolbarCheckoutActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun checkIfHasExtra() {
        if (intent.hasExtra(Constants.EXTRA_ADDRESS_DETAILS)) {
            mAddressDetails = intent.getParcelableExtra(Constants.EXTRA_ADDRESS_DETAILS)
        }
    }

    private fun setAddressDetailsToUI() {
        if (mAddressDetails != null) {
            mBinding.tvCheckoutAddressType.text = mAddressDetails?.type
            mBinding.tvCheckoutFullName.text = mAddressDetails?.name
            mBinding.tvCheckoutAddress.text =
                "${mAddressDetails!!.address}, ${mAddressDetails!!.zipCode}"
            mBinding.tvCheckoutAdditionalNote.text = mAddressDetails?.additionalNote

            if (mAddressDetails?.otherDetails!!.isNotEmpty()) {
                mBinding.tvCheckoutOtherDetails.text = mAddressDetails?.otherDetails
            }
            mBinding.tvCheckoutMobileNumber.text = mAddressDetails?.mobile_number
        }
    }

    private fun getProductsList() {
        showProgressDialog(getString(R.string.please_wait))
        FirestoreClass().getAllProductsList(this@CheckoutActivity)
    }

    fun successProductsListFromFireStore(productsList: ArrayList<Product>) {
        hideProgressDialog()
        mProductsList = productsList
        getCartItemsList()
    }

    private fun getCartItemsList() {
        showProgressDialog(getString(R.string.please_wait))
        FirestoreClass().getCartList(this@CheckoutActivity)
    }

    fun successCartItemsList(cartList: ArrayList<CartItem>) {
        hideProgressDialog()

        for(product in mProductsList) {
            for(cartItem in cartList) {
                if(product.id == cartItem.product_id) {
                    cartItem.stock_quantity = product.stock_quantity
                }
            }
        }

        mCartItemsList = cartList

        mBinding.rvCartListItems.layoutManager = LinearLayoutManager(this@CheckoutActivity)
        mBinding.rvCartListItems.setHasFixedSize(true)

        val cartListAdapter = CartListAdapter(this@CheckoutActivity, false)
        mBinding.rvCartListItems.adapter = cartListAdapter
        cartListAdapter.show(mCartItemsList)

        for (item in mCartItemsList) {
            val availableQuantity = item.stock_quantity.toInt()
            if (availableQuantity > 0) {
                val price = item.price.toDouble()
                val quantity = item.cart_quantity.toInt()

                mSubTotal += (price * quantity)
            }
        }

        mBinding.tvCheckoutSubTotal.text = "$mSubTotal €"
        mBinding.tvCheckoutShippingCharge.text = "10.0 €"

        if (mSubTotal > 0) {
            mBinding.llCheckoutPlaceOrder.visibility = View.VISIBLE

            mTotalAmount = mSubTotal + 10.0
            mBinding.tvCheckoutTotalAmount.text = "$mTotalAmount €"
        } else {
            mBinding.llCheckoutPlaceOrder.visibility = View.GONE
        }
    }

    private fun placeAnOrder() {
        showProgressDialog(resources.getString(R.string.please_wait))
       if(mAddressDetails != null) {
           val order = Order(
               FirestoreClass().getCurrentUserID(),
               mCartItemsList,
               mAddressDetails!!,
               "My order ${System.currentTimeMillis()}",
               mCartItemsList[0].image,
               mSubTotal.toString(),
               "10.0",
               mTotalAmount.toString(),
           )
           FirestoreClass().placeOrder(this@CheckoutActivity, order)
       }
    }

    fun orderPlacedSuccess() {
        hideProgressDialog()

        Toast.makeText(this@CheckoutActivity, "Your order was placed successfully.", Toast.LENGTH_SHORT)
            .show()

        val intent = Intent(this@CheckoutActivity, DashboardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

}