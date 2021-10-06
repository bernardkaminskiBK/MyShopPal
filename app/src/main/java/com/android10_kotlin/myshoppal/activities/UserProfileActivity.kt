package com.android10_kotlin.myshoppal.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.android10_kotlin.myshoppal.R
import com.android10_kotlin.myshoppal.databinding.ActivityUserProfileBinding
import com.android10_kotlin.myshoppal.models.User
import com.android10_kotlin.myshoppal.utils.Constants
import com.android10_kotlin.myshoppal.utils.GlideLoader
import com.android10_kotlin.myshoppal.utils.Utils

class UserProfileActivity : AppCompatActivity(), View.OnClickListener {

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

        mBinding.ivUserPhoto.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        view?.let {
            when (it.id) {
                R.id.iv_user_photo -> {
                    setUserProfileImage()
                }
                R.id.btn_save -> {

                }
            }
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

    private fun setUserProfileImage() {
        Utils.askForReadPermission(this@UserProfileActivity)
    }

    private fun setEditTextFields(userDetails: User) {
        mBinding.etFirstName.isEnabled = false
        mBinding.etFirstName.setText(userDetails.firstName)

        mBinding.etLastName.isEnabled = false
        mBinding.etLastName.setText(userDetails.lastName)

        mBinding.etEmail.isEnabled = false
        mBinding.etEmail.setText(userDetails.email)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        @Suppress("DEPRECATION")
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == Constants.GALLERY) {
            GlideLoader(this)
                .loadProfilePictureAndSaveToStorage(data, mBinding.ivUserPhoto)
        }
    }

}
