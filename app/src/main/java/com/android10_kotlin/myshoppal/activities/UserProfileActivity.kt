package com.android10_kotlin.myshoppal.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import com.android10_kotlin.myshoppal.R
import com.android10_kotlin.myshoppal.databinding.ActivityUserProfileBinding
import com.android10_kotlin.myshoppal.models.User
import com.android10_kotlin.myshoppal.utils.Constants
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class UserProfileActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityUserProfileBinding

    companion object {
        private const val GALLERY = 1
    }

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

        mBinding.ivUserPhoto.setOnClickListener {
            setUserProfileImage()
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
        Dexter.withContext(this@UserProfileActivity)
            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    val galleryIntent =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    @Suppress("DEPRECATION")
                    startActivityForResult(galleryIntent, GALLERY)
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {

                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                   token?.continuePermissionRequest()
                }

            }).onSameThread().check()
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
        if(resultCode == Activity.RESULT_OK && requestCode == GALLERY) {
            data?.let {
                val selectedPhotoUri = data.data

                Glide.with(this)
                    .load(selectedPhotoUri)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .listener(object: RequestListener<Drawable>{
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {

                            return false
                        }
                    }).into(mBinding.ivUserPhoto)
            }
        }
    }
}