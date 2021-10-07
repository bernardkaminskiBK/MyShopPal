package com.android10_kotlin.myshoppal.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android10_kotlin.myshoppal.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }


}