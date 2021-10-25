package com.android10_kotlin.myshoppal.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android10_kotlin.myshoppal.R
import com.android10_kotlin.myshoppal.databinding.ActivityAddEditAddressBinding

class AddEditAddressActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityAddEditAddressBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityAddEditAddressBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setupToolbar()

    }

    private fun setupToolbar() {
        setSupportActionBar(mBinding.toolbarAddEditAddressList)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        mBinding.toolbarAddEditAddressList.setNavigationOnClickListener {
            onBackPressed()
        }
    }

}