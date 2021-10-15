package com.android10_kotlin.myshoppal.utils

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.ImageView
import android.widget.Toast
import com.android10_kotlin.myshoppal.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.IOException

class GlideLoader(val context: Context) {

    fun loadPictureIntoView(picture: Any?, imageView: ImageView) {
        picture?.let {
            try {
                Glide.with(context)
                    .load(picture)
                    .centerCrop()
                    .placeholder(R.drawable.ic_user_placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

}