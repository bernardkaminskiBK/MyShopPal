package com.android10_kotlin.myshoppal.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android10_kotlin.myshoppal.R
import com.android10_kotlin.myshoppal.databinding.ActivityAddressListBinding

class AddressListActivity : BaseActivity() {

    private lateinit var mBinding: ActivityAddressListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityAddressListBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setupToolbar()

        mBinding.tvAddAddress.setOnClickListener {
            moveToAddEditAddressActivity()
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(mBinding.toolbarAddressListActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        mBinding.toolbarAddressListActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun moveToAddEditAddressActivity() {
        startActivity(Intent(this@AddressListActivity, AddEditAddressActivity::class.java))
    }

}