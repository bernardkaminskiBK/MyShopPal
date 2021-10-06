package com.android10_kotlin.myshoppal.utils

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.android10_kotlin.myshoppal.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class GlideLoader(val context: Context) {

    fun loadProfilePictureAndSaveToStorage(data: Intent?, imageView: ImageView) {
        data?.let {
            val selectedPhotoUri = data.data
            Glide.with(context)
                .load(selectedPhotoUri)
                .centerCrop()
                .placeholder(R.drawable.ic_user_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(object : RequestListener<Drawable> {
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
                }).into(imageView)
        }
    }
}