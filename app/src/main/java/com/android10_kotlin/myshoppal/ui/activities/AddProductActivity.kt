package com.android10_kotlin.myshoppal.ui.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.android10_kotlin.myshoppal.R
import com.android10_kotlin.myshoppal.databinding.ActivityAddProductBinding
import com.android10_kotlin.myshoppal.utils.Constants
import com.android10_kotlin.myshoppal.utils.GlideLoader
import com.android10_kotlin.myshoppal.utils.Utils

class AddProductActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mBinding: ActivityAddProductBinding
    private var mSelectedProductPicUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.ivAddUpdateProductImage.setOnClickListener(this)

        setupToolbar()
    }

    private fun setupToolbar() {
        setSupportActionBar(mBinding.toolbarAddProductActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        mBinding.toolbarAddProductActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onClick(v: View?) {
        v?.let {
            when (v.id) {
                R.id.iv_add_update_product_image -> {
                    Utils.askForReadPermissionToSavePhoto(this)
                }
                R.id.btn_add_product -> {

                }
            }
        }
    }

    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == Constants.GALLERY) {
            mSelectedProductPicUri = data!!.data
            mBinding.ivAddUpdateProductImage
                .setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_edit_34))
            GlideLoader(this)
                .loadPictureIntoView(mSelectedProductPicUri, mBinding.ivProductImage)
        }
    }

}