package com.android10_kotlin.myshoppal.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.android10_kotlin.myshoppal.R
import com.android10_kotlin.myshoppal.databinding.ActivitySoldProductDetailsBinding
import com.android10_kotlin.myshoppal.models.SoldProduct
import com.android10_kotlin.myshoppal.utils.Constants
import com.android10_kotlin.myshoppal.utils.GlideLoader
import java.text.SimpleDateFormat
import java.util.*

class SoldProductDetailsActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivitySoldProductDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySoldProductDetailsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setupToolbar()
        checkIfHasExtra()
    }

    private fun setupToolbar() {
        setSupportActionBar(mBinding.toolbarSoldProductDetailsActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        mBinding.toolbarSoldProductDetailsActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun checkIfHasExtra() {
        if (intent.hasExtra(Constants.EXTRA_SOLD_PRODUCT_DETAILS)) {
            val productDetails =
                intent.getParcelableExtra<SoldProduct>(Constants.EXTRA_SOLD_PRODUCT_DETAILS)!!
            setupUI(productDetails)
        }
    }

    private fun setupUI(productDetails: SoldProduct) {
        mBinding.tvSoldProductDetailsId.text = productDetails.order_id

        val dateFormat = "dd MMM yyyy HH:mm"
        val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = productDetails.order_date

        mBinding.tvSoldProductDetailsDate.text = formatter.format(calendar.time)

        GlideLoader(this@SoldProductDetailsActivity).loadPictureIntoView(
            productDetails.image,
            mBinding.ivProductItemImage
        )

        mBinding.tvProductItemName.text = productDetails.title
        mBinding.tvProductItemPrice.text = "${productDetails.price} €"
        mBinding.tvSoldProductQuantity.text = productDetails.sold_quantity.toString()

        mBinding.tvSoldDetailsAddressType.text = productDetails.address.type
        mBinding.tvSoldDetailsFullName.text = productDetails.address.name
        mBinding.tvSoldDetailsAddress.text =
            "${productDetails.address.address}, ${productDetails.address.zipCode}"
        mBinding.tvSoldDetailsAdditionalNote.text = productDetails.address.additionalNote

        if (productDetails.address.otherDetails.isNotEmpty()) {
            mBinding.tvSoldDetailsOtherDetails.visibility = View.VISIBLE
            mBinding.tvSoldDetailsOtherDetails.text = productDetails.address.otherDetails
        } else {
            mBinding.tvSoldDetailsOtherDetails.visibility = View.GONE
        }
        mBinding.tvSoldDetailsMobileNumber.text = productDetails.address.mobile_number

        mBinding.tvSoldProductSubTotal.text = productDetails.sub_total_amount + " €"
        mBinding.tvSoldProductShippingCharge.text = productDetails.shipping_charge + " €"
        mBinding.tvSoldProductTotalAmount.text = productDetails.total_amount + " €"
    }

}