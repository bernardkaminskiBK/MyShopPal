package com.android10_kotlin.myshoppal.ui.activities

import android.app.Activity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.android10_kotlin.myshoppal.R
import com.android10_kotlin.myshoppal.databinding.ActivityAddEditAddressBinding
import com.android10_kotlin.myshoppal.firestore.FirestoreClass
import com.android10_kotlin.myshoppal.models.Address
import com.android10_kotlin.myshoppal.utils.Constants

class AddEditAddressActivity : BaseActivity() {

    private lateinit var mBinding: ActivityAddEditAddressBinding

    private var mAddressDetails: Address? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityAddEditAddressBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setupToolbar()

        if (intent.hasExtra(Constants.EXTRA_ADDRESS_DETAILS)) {
            mAddressDetails =
                intent.getParcelableExtra(Constants.EXTRA_ADDRESS_DETAILS)!!
        }

        editAddress()

        mBinding.btnSubmitAddress.setOnClickListener {
            saveAddressToFirestore()
        }

        mBinding.rgType.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.rb_other) {
                mBinding.tilOtherDetails.visibility = View.VISIBLE
            } else {
                mBinding.tilOtherDetails.visibility = View.GONE
            }
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(mBinding.toolbarAddEditAddressList)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        mBinding.toolbarAddEditAddressList.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun validateData(): Boolean {
        return when {

            TextUtils.isEmpty(mBinding.etFullName.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_please_enter_full_name),
                    true
                )
                false
            }

            TextUtils.isEmpty(mBinding.etPhoneNumber.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_please_enter_phone_number),
                    true
                )
                false
            }

            TextUtils.isEmpty(mBinding.etAddress.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_please_enter_address), true)
                false
            }

            TextUtils.isEmpty(mBinding.etZipCode.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_please_enter_zip_code), true)
                false
            }

            mBinding.rbOther.isChecked && TextUtils.isEmpty(
                mBinding.etZipCode.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_please_enter_zip_code), true)
                false
            }
            else -> {
                true
            }
        }
    }

    private fun editAddress() {
        if (mAddressDetails != null) {
            if (mAddressDetails!!.id.isNotEmpty()) {

                mBinding.toolbarTitle.text = resources.getString(R.string.title_edit_address)
                mBinding.btnSubmitAddress.text = resources.getString(R.string.btn_lbl_update)

                mBinding.etFullName.setText(mAddressDetails?.name)
                mBinding.etPhoneNumber.setText(mAddressDetails?.mobile_number)
                mBinding.etAddress.setText(mAddressDetails?.address)
                mBinding.etZipCode.setText(mAddressDetails?.zipCode)
                mBinding.etAdditionalNote.setText(mAddressDetails?.additionalNote)

                when (mAddressDetails?.type) {
                    Constants.HOME -> {
                        mBinding.rbHome.isChecked = true
                    }
                    Constants.OFFICE -> {
                        mBinding.rbOffice.isChecked = true
                    }
                    else -> {
                        mBinding.rbOther.isChecked = true
                        mBinding.tilOtherDetails.visibility = View.VISIBLE
                        mBinding.etOtherDetails.setText(mAddressDetails?.otherDetails)
                    }
                }
            }
        }
    }

    private fun saveAddressToFirestore() {
        val fullName: String = mBinding.etFullName.text.toString().trim { it <= ' ' }
        val phoneNumber: String = mBinding.etPhoneNumber.text.toString().trim { it <= ' ' }
        val address: String = mBinding.etAddress.text.toString().trim { it <= ' ' }
        val zipCode: String = mBinding.etZipCode.text.toString().trim { it <= ' ' }
        val additionalNote: String = mBinding.etAdditionalNote.text.toString().trim { it <= ' ' }
        val otherDetails: String = mBinding.etOtherDetails.text.toString().trim { it <= ' ' }

        if (validateData()) {
            showProgressDialog(resources.getString(R.string.please_wait))

            val addressType: String = when {
                mBinding.rbHome.isChecked -> {
                    Constants.HOME
                }
                mBinding.rbOffice.isChecked -> {
                    Constants.OFFICE
                }
                else -> {
                    Constants.OTHER
                }
            }

            val addressModel = Address(
                FirestoreClass().getCurrentUserID(),
                fullName,
                phoneNumber,
                address,
                zipCode,
                additionalNote,
                addressType,
                otherDetails
            )

            if (mAddressDetails != null && mAddressDetails!!.id.isNotEmpty()) {
                FirestoreClass().updateAddress(
                    this@AddEditAddressActivity,
                    addressModel,
                    mAddressDetails!!.id
                )
            } else {
                FirestoreClass().addAddress(this@AddEditAddressActivity, addressModel)
            }
        }
    }

    fun addUpdateAddressSuccess() {
        hideProgressDialog()
        val notifySuccessMessage: String =
            if (mAddressDetails != null && mAddressDetails!!.id.isNotEmpty()) {
                resources.getString(R.string.msg_your_address_updated_successfully)
            } else {
                resources.getString(R.string.err_your_address_added_successfully)
            }

        Toast.makeText(this, notifySuccessMessage, Toast.LENGTH_SHORT).show()
        setResult(Activity.RESULT_OK)
        finish()
    }

}