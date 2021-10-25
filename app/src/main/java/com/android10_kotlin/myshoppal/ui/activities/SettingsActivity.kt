package com.android10_kotlin.myshoppal.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.android10_kotlin.myshoppal.R
import com.android10_kotlin.myshoppal.databinding.ActivitySettingsBinding
import com.android10_kotlin.myshoppal.firestore.FirestoreClass
import com.android10_kotlin.myshoppal.models.User
import com.android10_kotlin.myshoppal.utils.Constants
import com.android10_kotlin.myshoppal.utils.GlideLoader
import com.android10_kotlin.myshoppal.utils.Utils
import com.google.firebase.auth.FirebaseAuth

class SettingsActivity : BaseActivity(), View.OnClickListener {

    private lateinit var mBinding: ActivitySettingsBinding
    private lateinit var mUsersDetails: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setupToolbar()

        mBinding.tvEdit.setOnClickListener(this)
        mBinding.btnLogout.setOnClickListener(this)
        mBinding.llAddress.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        v?.let {
            when (it.id) {
                R.id.btn_logout -> {
                    logoutUser()
                }
                R.id.tv_edit -> {
                    moveToProfileActivity()
                }
                R.id.ll_address -> {
                    moveToAddressListActivity()
                }
            }
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(mBinding.toolbarSettingsActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        mBinding.toolbarSettingsActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun getUserDetails() {
        showProgressDialog(getString(R.string.please_wait))
        FirestoreClass().getUserDetails(this)
    }

    fun userDetailsSuccess(user: User) {
        mUsersDetails = user
        hideProgressDialog()
        setUI(user)
    }

    private fun setUI(user: User) {
        GlideLoader(this).loadPictureIntoView(user.image, mBinding.ivUserPhoto)
        mBinding.tvName.text = Utils.getUserNameSharedPreferences(this)
        mBinding.tvGender.text = user.gender
        mBinding.tvEmail.text = user.email
        mBinding.tvMobileNumber.text = user.mobile
    }

    private fun logoutUser() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this@SettingsActivity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun moveToProfileActivity() {
        val intent = Intent(this@SettingsActivity, UserProfileActivity::class.java)
        intent.putExtra(Constants.EXTRA_USER_DETAILS, mUsersDetails)
        startActivity(intent)
    }

    private fun moveToAddressListActivity() {
        startActivity(Intent(this@SettingsActivity, AddressListActivity::class.java))
    }

    override fun onResume() {
        super.onResume()
        getUserDetails()
    }

}