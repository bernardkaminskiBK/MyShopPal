package com.android10_kotlin.myshoppal.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android10_kotlin.myshoppal.R
import com.android10_kotlin.myshoppal.databinding.ActivityAddressListBinding
import com.android10_kotlin.myshoppal.firestore.FirestoreClass
import com.android10_kotlin.myshoppal.models.Address
import com.android10_kotlin.myshoppal.ui.adapters.AddressListAdapter
import com.android10_kotlin.myshoppal.utils.Constants
import com.android10_kotlin.myshoppal.utils.Swipe
import java.util.ArrayList

class AddressListActivity : BaseActivity() {

    private lateinit var mBinding: ActivityAddressListBinding

    private var mSelectAddress: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityAddressListBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setupToolbar()
        getAddressList()

        mBinding.tvAddAddress.setOnClickListener {
            moveToAddEditAddressActivity()
        }

        checkIfHasExtra()
    }

    private fun checkIfHasExtra() {
        if (intent.hasExtra(Constants.EXTRA_SELECT_ADDRESS)) {
            mSelectAddress =
                intent.getBooleanExtra(Constants.EXTRA_SELECT_ADDRESS, false)
            if (mSelectAddress) {
                mBinding.toolbarTitle.text = getString(R.string.title_select_address)
            }
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(mBinding.toolbarAddressListActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        mBinding.toolbarAddressListActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun getAddressList() {
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getAddressesList(this@AddressListActivity)
    }

    private fun moveToAddEditAddressActivity() {
        val intent = Intent(this@AddressListActivity, AddEditAddressActivity::class.java)
        @Suppress("DEPRECATION")
        startActivityForResult(intent, Constants.ADD_ADDRESS_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        @Suppress("DEPRECATION")
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.ADD_ADDRESS_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            getAddressList()
        }
    }

    fun successAddressListFromFirestore(addressList: ArrayList<Address>) {
        hideProgressDialog()

        if (addressList.size > 0) {
            mBinding.rvAddressList.visibility = View.VISIBLE
            mBinding.tvNoAddressFound.visibility = View.GONE

            mBinding.rvAddressList.layoutManager = LinearLayoutManager(this)
            mBinding.rvAddressList.setHasFixedSize(true)

            val adapter = AddressListAdapter(this, mSelectAddress)
            mBinding.rvAddressList.adapter = adapter
            adapter.show(addressList)

            if (!mSelectAddress) {
                Swipe.editSwipe(this, mBinding.rvAddressList)
                Swipe.deleteSwipe(this, mBinding.rvAddressList, addressList)
            }
        } else {
            mBinding.rvAddressList.visibility = View.GONE
            mBinding.tvNoAddressFound.visibility = View.VISIBLE
        }
    }

    fun deleteAddressSuccess() {
        hideProgressDialog()

        Toast.makeText(
            this@AddressListActivity,
            resources.getString(R.string.err_your_address_deleted_successfully),
            Toast.LENGTH_SHORT
        ).show()

        getAddressList()
    }

}