package com.android10_kotlin.myshoppal.utils

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
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
                        Log.e(context.javaClass.simpleName, "Failed load the picture")
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        val imageExtension = MimeTypeMap.getSingleton()
                            .getExtensionFromMimeType(
                                context.contentResolver.getType(
                                    selectedPhotoUri!!
                                )
                            )

                        val sRef: StorageReference =
                            FirebaseStorage.getInstance()
                                .reference.child("Image " + System.currentTimeMillis() + "." + imageExtension)

                        sRef.putFile(selectedPhotoUri)
                            .addOnSuccessListener { taskSnapshot ->
                                taskSnapshot.metadata!!.reference!!.downloadUrl
                                    .addOnSuccessListener {
                                        Toast.makeText(
                                            context,
                                            "Your image was uploaded successfully",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }.addOnFailureListener {
                                        Toast.makeText(
                                            context,
                                            it.message,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                            }
                        return false
                    }
                }).into(imageView)
        }
    }
}