package com.android10_kotlin.myshoppal.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android10_kotlin.myshoppal.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

}