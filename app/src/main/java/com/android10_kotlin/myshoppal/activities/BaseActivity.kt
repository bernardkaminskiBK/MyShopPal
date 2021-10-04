package com.android10_kotlin.myshoppal.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.android10_kotlin.myshoppal.R
import com.android10_kotlin.myshoppal.databinding.DialogProgressBinding
import com.google.android.material.snackbar.Snackbar

open class BaseActivity : AppCompatActivity() {

    private lateinit var mProgressDialog: Dialog

    fun showErrorSnackBar(message: String, errorMessage: Boolean) {
        val snackBar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view

        if (errorMessage) {
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(this, R.color.colorSnackBarError)
            )
        } else {
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(this, R.color.colorSnackBarSuccess)
            )
        }
        snackBar.show()
    }

    fun showProgressDialog(text: String) {
        mProgressDialog = Dialog(this)
        val dialogProgressBinding =  DialogProgressBinding.inflate(layoutInflater)
        mProgressDialog.setContentView(dialogProgressBinding.root)
        dialogProgressBinding.tvProgressText.text = text

        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)
        mProgressDialog.show()
    }

    fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }

}