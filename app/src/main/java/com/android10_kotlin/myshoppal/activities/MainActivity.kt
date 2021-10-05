package com.android10_kotlin.myshoppal.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android10_kotlin.myshoppal.databinding.ActivityMainBinding
import com.android10_kotlin.myshoppal.utils.Utils

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.textView.text = Utils.getUserNameSharedPreferences(this)

    }

}