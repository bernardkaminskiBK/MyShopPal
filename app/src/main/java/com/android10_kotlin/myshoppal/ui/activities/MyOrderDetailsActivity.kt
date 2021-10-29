package com.android10_kotlin.myshoppal.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android10_kotlin.myshoppal.R
import com.android10_kotlin.myshoppal.databinding.ActivityMyOrderDetailsBinding
import com.android10_kotlin.myshoppal.models.Order
import com.android10_kotlin.myshoppal.ui.adapters.CartListAdapter
import com.android10_kotlin.myshoppal.utils.Constants
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class MyOrderDetailsActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMyOrderDetailsBinding
    private lateinit var mMyOrderDetails: Order

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMyOrderDetailsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setupToolbar()
        checkIfHasExtra()
    }

    private fun setupToolbar() {
        setSupportActionBar(mBinding.toolbarMyOrderDetailsActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        mBinding.toolbarMyOrderDetailsActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun checkIfHasExtra() {
        if (intent.hasExtra(Constants.EXTRA_MY_ORDER_DETAILS)) {
            val myOrderDetails: Order =
                intent.getParcelableExtra(Constants.EXTRA_MY_ORDER_DETAILS)!!
            setupUI(myOrderDetails)
        }
    }

    private fun setupUI(orderDetails: Order) {
        mBinding.tvOrderDetailsId.text = orderDetails.title

        val dateFormat = "dd MMM yyyy HH:mm"
        val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())

        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = orderDetails.order_datetime

        val orderDateTime = formatter.format(calendar.time)
        mBinding.tvOrderDetailsDate.text = orderDateTime

        // Get the difference between the order date time and current date time in hours.
        // If the difference in hours is 1 or less then the order status will be PENDING.
        // If the difference in hours is 2 or greater then 1 then the order status will be PROCESSING.
        // And, if the difference in hours is 3 or greater then the order status will be DELIVERED.

        val diffInMilliSeconds: Long = System.currentTimeMillis() - orderDetails.order_datetime
        val diffInHours: Long = TimeUnit.MILLISECONDS.toHours(diffInMilliSeconds)
        Log.e("Difference in Hours", "$diffInHours")

        when {
            diffInHours < 1 -> {
                mBinding.tvOrderStatus.text = resources.getString(R.string.order_status_pending)
                mBinding.tvOrderStatus.setTextColor(
                    ContextCompat.getColor(
                        this@MyOrderDetailsActivity,
                        R.color.colorAccent
                    )
                )
            }
            diffInHours < 2 -> {
                mBinding.tvOrderStatus.text = resources.getString(R.string.order_status_in_process)
                mBinding.tvOrderStatus.setTextColor(
                    ContextCompat.getColor(
                        this@MyOrderDetailsActivity,
                        R.color.colorOrderStatusInProcess
                    )
                )
            }
            else -> {
                mBinding.tvOrderStatus.text = resources.getString(R.string.order_status_delivered)
                mBinding.tvOrderStatus.setTextColor(
                    ContextCompat.getColor(
                        this@MyOrderDetailsActivity,
                        R.color.colorOrderStatusDelivered
                    )
                )
            }
        }

        mBinding.rvMyOrderItemsList.layoutManager = LinearLayoutManager(this@MyOrderDetailsActivity)
        mBinding.rvMyOrderItemsList.setHasFixedSize(true)

        val cartListAdapter =
            CartListAdapter(this@MyOrderDetailsActivity, false)
        mBinding.rvMyOrderItemsList.adapter = cartListAdapter
        cartListAdapter.show(orderDetails.items)

        mBinding.tvMyOrderDetailsAddressType.text = orderDetails.address.type
        mBinding.tvMyOrderDetailsFullName.text = orderDetails.address.name
        mBinding.tvMyOrderDetailsAddress.text =
            "${orderDetails.address.address}, ${orderDetails.address.zipCode}"
        mBinding.tvMyOrderDetailsAdditionalNote.text = orderDetails.address.additionalNote

        if (orderDetails.address.otherDetails.isNotEmpty()) {
            mBinding.tvMyOrderDetailsOtherDetails.visibility = View.VISIBLE
            mBinding.tvMyOrderDetailsOtherDetails.text = orderDetails.address.otherDetails
        } else {
            mBinding.tvMyOrderDetailsOtherDetails.visibility = View.GONE
        }
        mBinding.tvMyOrderDetailsMobileNumber.text = orderDetails.address.mobile_number

        mBinding.tvOrderDetailsSubTotal.text = "${orderDetails.sub_total_amount} €"
        mBinding.tvOrderDetailsShippingCharge.text = "${orderDetails.shipping_charge} €"
        mBinding.tvOrderDetailsTotalAmount.text = "${orderDetails.total_amount} €"
    }

}