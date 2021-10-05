package com.android10_kotlin.myshoppal.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android10_kotlin.myshoppal.R
import com.android10_kotlin.myshoppal.databinding.ActivityUserProfileBinding
import com.android10_kotlin.myshoppal.models.User
import com.android10_kotlin.myshoppal.utils.Constants

class UserProfileActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityUserProfileBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setupToolbar()

        if (intent.hasExtra(Constants.EXTRA_USER_DETAILS)) {
            val userDetails: User =
                intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)!!
            setEditTextFields(userDetails)
        }

    }

    private fun setupToolbar() {
        setSupportActionBar(mBinding.toolbarUserProfileActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        mBinding.toolbarUserProfileActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setEditTextFields(userDetails: User) {
        mBinding.etFirstName.isEnabled = false
        mBinding.etFirstName.setText(userDetails.firstName)

        mBinding.etLastName.isEnabled = false
        mBinding.etLastName.setText(userDetails.lastName)

        mBinding.etEmail.isEnabled = false
        mBinding.etEmail.setText(userDetails.email)
    }

}