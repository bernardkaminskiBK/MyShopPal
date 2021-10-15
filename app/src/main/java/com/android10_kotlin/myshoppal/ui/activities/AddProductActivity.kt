package com.android10_kotlin.myshoppal.ui.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.android10_kotlin.myshoppal.R
import com.android10_kotlin.myshoppal.databinding.ActivityAddProductBinding
import com.android10_kotlin.myshoppal.firestore.FirestoreClass
import com.android10_kotlin.myshoppal.models.Product
import com.android10_kotlin.myshoppal.utils.Constants
import com.android10_kotlin.myshoppal.utils.GlideLoader
import com.android10_kotlin.myshoppal.utils.Utils

class AddProductActivity : BaseActivity(), View.OnClickListener {

    private lateinit var mBinding: ActivityAddProductBinding

    private var mSelectedProductPicUri: Uri? = null
    private var mProductImageURL: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.ivAddProductImage.setOnClickListener(this)
        mBinding.ivEditProductImage.setOnClickListener(this)
        mBinding.btnAddProduct.setOnClickListener(this)

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
                R.id.iv_add_product_image -> {
                    Utils.askForReadPermissionToSavePhoto(this)
                }
                R.id.iv_edit_product_image -> {
                    Utils.askForReadPermissionToSavePhoto(this)
                }
                R.id.btn_add_product -> {
                    if (validateProductDetails()) {
                        uploadProductImage()
                    }
                }
                else -> {

                }
            }
        }
    }

    private fun uploadProductImage() {
        showProgressDialog(getString(R.string.please_wait))
        FirestoreClass().uploadImageToCloudStorage(
            this,
            mSelectedProductPicUri,
            Constants.PRODUCT_TYPE_IMAGE
        )
    }

    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == Constants.GALLERY) {
            mSelectedProductPicUri = data!!.data

            mBinding.ivEditProductImage.visibility = View.VISIBLE
            mBinding.ivAddProductImage.visibility = View.INVISIBLE

            GlideLoader(this)
                .loadPictureIntoView(mSelectedProductPicUri, mBinding.ivProductImage)
        }
    }

    private fun validateProductDetails(): Boolean {
        return when {
            mSelectedProductPicUri == null -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_select_product_image), true)
                false
            }

            TextUtils.isEmpty(mBinding.etProductTitle.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_product_title), true)
                false
            }

            TextUtils.isEmpty(mBinding.etProductPrice.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_product_price), true)
                false
            }

            TextUtils.isEmpty(mBinding.etProductDescription.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_enter_product_description),
                    true
                )
                false
            }

            TextUtils.isEmpty(mBinding.etProductQuantity.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_enter_product_quantity),
                    true
                )
                false
            }
            else -> {
                true
            }
        }
    }

    fun productUploadSuccess() {
        hideProgressDialog()
        Toast.makeText(this, "Your product was uploaded successfully", Toast.LENGTH_LONG).show()
        finish()
    }

    fun imageUploadSuccess(imageUrl: String) {
        hideProgressDialog()
        mProductImageURL = imageUrl

        saveProductToFirebase()
    }

    private fun saveProductToFirebase() {
        val id = FirestoreClass().getCurrentUserID()
        val userName = Utils.getUserNameSharedPreferences(this)
        val title = mBinding.etProductTitle.text.toString().trim { it <= ' ' }
        val price = mBinding.etProductPrice.text.toString().trim { it <= ' ' }
        val description = mBinding.etProductDescription.text.toString().trim { it <= ' ' }
        val quantity = mBinding.etProductQuantity.text.toString().trim { it <= ' ' }

        val product = Product(id, userName, title, price, description, quantity, mProductImageURL)
        FirestoreClass().uploadProductDetails(this, product)
    }

}