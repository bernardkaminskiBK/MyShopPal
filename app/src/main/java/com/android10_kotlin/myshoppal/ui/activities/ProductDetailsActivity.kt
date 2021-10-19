package com.android10_kotlin.myshoppal.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android10_kotlin.myshoppal.R
import com.android10_kotlin.myshoppal.databinding.ActivityProductDetailsBinding
import com.android10_kotlin.myshoppal.firestore.FirestoreClass
import com.android10_kotlin.myshoppal.models.Product
import com.android10_kotlin.myshoppal.utils.Constants
import com.android10_kotlin.myshoppal.utils.GlideLoader

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityProductDetailsBinding
    private var mProductDetails: Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        if (intent.hasExtra(Constants.PRODUCT_DETAILS)) {
            mProductDetails = intent.getParcelableExtra(Constants.PRODUCT_DETAILS)
            setProductDetailsUI(mProductDetails)
        }

        setupToolbar()

//       getProductDetails()
    }

    private fun setProductDetailsUI(product: Product?) {
        product?.let {
            GlideLoader(this).loadPictureIntoView(
                product.product_image,
                mBinding.ivProductDetailImage
            )
            mBinding.tvProductDetailsTitle.text = it.title
            mBinding.tvProductDetailsPrice.text = it.price
            mBinding.tvProductDetailsDescription.text = it.description
            mBinding.tvProductDetailsStockQuantity.text = it.stock_quantity
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(mBinding.toolbarProductDetailsActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        mBinding.toolbarProductDetailsActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

//    private fun getProductDetails() {
//        showProgressDialog(resources.getString(R.string.please_wait))
//        FirestoreClass().getProductDetails(this@ProductDetailsActivity, mProductId)
//    }
//
//    fun productDetailsSuccess(product: Product) {
//        hideProgressDialog()
//
//        GlideLoader(this@ProductDetailsActivity).loadProductPicture(
//            product.image,
//            iv_product_detail_image
//        )
//
//        tv_product_details_title.text = product.title
//        tv_product_details_price.text = "$${product.price}"
//        tv_product_details_description.text = product.description
//        tv_product_details_stock_quantity.text = product.stock_quantity
//    }


}