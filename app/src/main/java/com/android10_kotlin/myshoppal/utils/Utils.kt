package com.android10_kotlin.myshoppal.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.provider.MediaStore
import android.widget.ImageView
import com.android10_kotlin.myshoppal.R
import com.android10_kotlin.myshoppal.activities.BaseActivity
import com.android10_kotlin.myshoppal.models.User
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

object Utils {

    private var sharedPreferences: SharedPreferences? = null

    fun saveUserNameSharedPreferences(activity: Activity, user: User) {
        sharedPreferences =
            activity.getSharedPreferences(Constants.MY_SHOP_PAL_PREFERENCES, Context.MODE_PRIVATE)
        editSharedPreferences(user)
    }

    private fun editSharedPreferences(user: User) {
        val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
        editor.putString(Constants.LOGGED_IN_USERNAME, "${user.firstName} ${user.lastName}")
        editor.apply()
    }

    fun getUserNameSharedPreferences(activity: Activity): String {
        val sharedPreferences =
            activity.getSharedPreferences(Constants.MY_SHOP_PAL_PREFERENCES, Context.MODE_PRIVATE)
        return sharedPreferences.getString(Constants.LOGGED_IN_USERNAME, "")!!
    }

    fun showImageChooser(activity: Activity) {
        val galleryIntent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        @Suppress("DEPRECATION")
        activity.startActivityForResult(galleryIntent, Constants.GALLERY)
    }

    fun askForReadPermission(activity: Activity) {
        Dexter.withContext(activity)
            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    showImageChooser(activity)
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

}