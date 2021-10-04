package com.android10_kotlin.myshoppal.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.android10_kotlin.myshoppal.R
import com.android10_kotlin.myshoppal.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity(), View.OnClickListener {

    private lateinit var mBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.tvForgotPassword.setOnClickListener(this)
        mBinding.btnLogin.setOnClickListener(this)
        mBinding.tvRegister.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        if(view != null) {
            when(view.id) {
                R.id.tv_forgot_password -> {

                }
                R.id.btn_login -> {
                    validateLoginDetails()
                }
                R.id.tv_register -> {
                    startActivity(Intent(this, RegisterActivity::class.java))
                }
            }
        }
    }

    private fun validateLoginDetails(): Boolean {
        return when {
            TextUtils.isEmpty(mBinding.etEmail.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }
            TextUtils.isEmpty(mBinding.etPassword.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }
            else -> {
                showErrorSnackBar("Your details are valid.", false)
                true
            }
        }
    }

}