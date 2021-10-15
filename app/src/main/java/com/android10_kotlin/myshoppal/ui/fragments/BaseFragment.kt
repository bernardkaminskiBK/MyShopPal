package com.android10_kotlin.myshoppal.ui.fragments

import android.app.Dialog
import androidx.fragment.app.Fragment
import com.android10_kotlin.myshoppal.databinding.DialogProgressBinding

open class BaseFragment : Fragment() {

    private lateinit var mProgressDialog: Dialog

    fun showProgressDialog(text: String) {
        mProgressDialog = Dialog(requireActivity())
        val dialogProgressBinding = DialogProgressBinding.inflate(layoutInflater)
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