package com.android10_kotlin.myshoppal.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android10_kotlin.myshoppal.databinding.ActivityMyOrderDetailsBinding

class MyOrderDetailsActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMyOrderDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMyOrderDetailsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

}