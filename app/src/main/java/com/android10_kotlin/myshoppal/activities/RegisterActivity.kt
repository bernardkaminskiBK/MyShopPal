package com.android10_kotlin.myshoppal.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.android10_kotlin.myshoppal.R
import com.android10_kotlin.myshoppal.databinding.ActivityRegisterBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterActivity : BaseActivity() {

    private lateinit var mBinding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setupToolbar()

        mBinding.tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        mBinding.btnRegister.setOnClickListener {
            registerUser()
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(mBinding.toolbarRegisterActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        mBinding.toolbarRegisterActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun validateRegisterDetails(): Boolean {
        return when {
            TextUtils.isEmpty(mBinding.etFirstName.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_first_name), true)
                false
            }

            TextUtils.isEmpty(mBinding.etLastName.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_last_name), true)
                false
            }

            TextUtils.isEmpty(mBinding.etEmail.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }

            TextUtils.isEmpty(mBinding.etPassword.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }

            TextUtils.isEmpty(mBinding.etConfirmPassword.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_enter_confirm_password),
                    true
                )
                false
            }

            mBinding.etPassword.text.toString()
                .trim { it <= ' ' } != mBinding.etConfirmPassword.text.toString()
                .trim { it <= ' ' } -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_password_and_confirm_password_mismatch),
                    true
                )
                false
            }
            !mBinding.cbTermsAndCondition.isChecked -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_agree_terms_and_condition),
                    true
                )
                false
            }
            else -> {
//                showErrorSnackBar("Your details are valid.", false)
                true
            }
        }
    }

    private fun registerUser() {

        if (validateRegisterDetails()) {

            showProgressDialog(getString(R.string.please_wait))

            val email: String = mBinding.etEmail.text.toString().trim { it <= ' ' }
            val password: String = mBinding.etEmail.text.toString().trim { it <= ' ' }

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                    OnCompleteListener<AuthResult> { task ->
                        hideProgressDialog()
                        if (task.isSuccessful) {
                            val firebaseUser: FirebaseUser = task.result!!.user!!
                            showErrorSnackBar(
                                "You are registered successfully. Your user id is ${firebaseUser.uid}",
                                false
                            )
                        } else {
                            showErrorSnackBar(task.exception!!.message.toString(), true)
                        }
                    })
        }
    }
}