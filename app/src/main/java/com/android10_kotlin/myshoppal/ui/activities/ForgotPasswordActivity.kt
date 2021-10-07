package com.android10_kotlin.myshoppal.ui.activities

import android.os.Bundle
import android.widget.Toast
import com.android10_kotlin.myshoppal.R
import com.android10_kotlin.myshoppal.databinding.ActivityForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : BaseActivity() {

    private lateinit var mBinding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setupToolbar()

        mBinding.btnSubmit.setOnClickListener {
            resetForgotPasswordByEmail()
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(mBinding.toolbarForgotPassword)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        supportActionBar?.title = ""
        mBinding.toolbarForgotPassword.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun resetForgotPasswordByEmail() {
        val email: String = mBinding.etEmail.text.toString().trim { it <= ' ' }
        if (email.isEmpty()) {
            showErrorSnackBar(getString(R.string.err_msg_enter_email), true)
        } else {
            showProgressDialog(getString(R.string.please_wait))

            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    hideProgressDialog()
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this@ForgotPasswordActivity,
                            getString(R.string.successfully_sent_email),
                            Toast.LENGTH_LONG
                        ).show()

                        finish()
                    } else {
                        showErrorSnackBar(task.exception!!.message.toString(), true)
                    }
                }
        }
    }

}