package com.android10_kotlin.myshoppal.ui.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.android10_kotlin.myshoppal.R
import com.android10_kotlin.myshoppal.databinding.ActivityUserProfileBinding
import com.android10_kotlin.myshoppal.firestore.FirestoreClass
import com.android10_kotlin.myshoppal.models.User
import com.android10_kotlin.myshoppal.utils.Constants
import com.android10_kotlin.myshoppal.utils.GlideLoader
import com.android10_kotlin.myshoppal.utils.Utils

class UserProfileActivity : BaseActivity(), View.OnClickListener {

    private lateinit var mBinding: ActivityUserProfileBinding

    private var mUserDetails: User? = null
    private var mSelectedProfilePicUri: Uri? = null

    private var mUserImageURL: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        if (intent.hasExtra(Constants.EXTRA_USER_DETAILS)) {
            mUserDetails = intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)
            setUserDataToUserProfileUI(mUserDetails!!)
        }

        mBinding.ivUserPhoto.setOnClickListener(this)
        mBinding.btnSave.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        view?.let {
            when (it.id) {
                R.id.iv_user_photo -> {
                    setUserProfileImage()
                }
                R.id.btn_save -> {
                    if (validateUserProfileDetails()) {
                        showProgressDialog(getString(R.string.please_wait))
                        if (mSelectedProfilePicUri != null) {
                            FirestoreClass()
                                .uploadImageToCloudStorage(
                                    this,
                                    mSelectedProfilePicUri,
                                    Constants.USER_PROFILE_TYPE_IMAGE
                                )
                        } else {
                            prepareUserDataAndSaveToDB()
                        }
                    }
                }
                else -> {

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
        Utils.askForReadPermissionToSavePhoto(this@UserProfileActivity)
    }

    private fun setUserDataToUserProfileUI(userDetails: User) {
        if (mUserDetails!!.profileCompleted == 0) {
            mBinding.tvToolbarTitle.text = getString(R.string.title_complete_profile)

            mBinding.etFirstName.isEnabled = false
            mBinding.etFirstName.setText(userDetails.firstName)

            mBinding.etLastName.isEnabled = false
            mBinding.etLastName.setText(userDetails.lastName)

            mBinding.etEmail.isEnabled = false
            mBinding.etEmail.setText(userDetails.email)
        } else {
            setupToolbar()
            GlideLoader(this).loadPictureIntoView(mUserDetails!!.image, mBinding.ivUserPhoto)
            mBinding.etFirstName.setText(userDetails.firstName)
            mBinding.etLastName.setText(userDetails.lastName)
            mBinding.etEmail.setText(userDetails.email)
            mBinding.etMobileNumber.setText(userDetails.mobile)
            if (userDetails.gender == Constants.MALE) {
                mBinding.rbMale.isChecked = true
            } else {
                mBinding.rbFemale.isChecked = true
            }
        }

    }

    private fun validateUserProfileDetails(): Boolean {
        return when {
            TextUtils.isEmpty(mBinding.etMobileNumber.text.toString().trim() { it <= ' ' }) -> {
                showErrorSnackBar(getString(R.string.err_msg_mobile_number), true)
                false
            }
            else -> {
                true
            }
        }
    }

    private fun prepareUserDataAndSaveToDB() {
        val userHashMap = HashMap<String, Any>()

        if (mUserImageURL.isNotEmpty()) {
            userHashMap[Constants.IMAGE] = mUserImageURL
        }

        val firstName = mBinding.etFirstName.text.toString().trim() { it <= ' ' }
        if (firstName != mUserDetails!!.firstName) {
            userHashMap[Constants.FIRST_NAME] = firstName
        }

        val lastName = mBinding.etLastName.text.toString().trim() { it <= ' ' }
        if (lastName != mUserDetails!!.lastName) {
            userHashMap[Constants.LAST_NAME] = lastName
        }

        val mobileNumber = mBinding.etMobileNumber.text.toString().trim() { it <= ' ' }
        if (mobileNumber.isNotEmpty() && mobileNumber != mUserDetails!!.mobile.toString()) {
            userHashMap[Constants.MOBILE] = mobileNumber
        }

        val gender = if (mBinding.rbMale.isChecked) {
            Constants.MALE
        } else {
            Constants.FEMALE
        }
        userHashMap[Constants.GENDER] = gender

        userHashMap[Constants.COMPLETE_PROFILE] = 1
        FirestoreClass().updateUserProfileData(this, userHashMap)
    }

    fun imageUploadSuccess(imageURL: String) {
        mUserImageURL = imageURL
        prepareUserDataAndSaveToDB()
    }

    fun userProfileUpdateSuccess() {
        hideProgressDialog()
        Toast.makeText(this, getString(R.string.msg_profile_update_success), Toast.LENGTH_SHORT)
            .show()

        startActivity(Intent(this@UserProfileActivity, DashboardActivity::class.java))
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        @Suppress("DEPRECATION")
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == Constants.GALLERY) {
            mSelectedProfilePicUri = data!!.data
            GlideLoader(this)
                .loadPictureIntoView(mSelectedProfilePicUri, mBinding.ivUserPhoto)
        }
    }

}
