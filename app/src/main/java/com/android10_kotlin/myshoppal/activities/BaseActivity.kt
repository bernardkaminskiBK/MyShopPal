package com.android10_kotlin.myshoppal.activities

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.android10_kotlin.myshoppal.R
import com.google.android.material.snackbar.Snackbar

open class BaseActivity : AppCompatActivity() {

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

}