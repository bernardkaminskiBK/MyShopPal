package com.android10_kotlin.myshoppal.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android10_kotlin.myshoppal.R
import com.android10_kotlin.myshoppal.databinding.ActivityAddProductBinding

class AddProductActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityAddProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setupToolbar()
    }

    private fun setupToolbar() {
        setSupportActionBar(mBinding.toolbarAddProductActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        mBinding.toolbarAddProductActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

}