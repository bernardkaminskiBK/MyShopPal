package com.android10_kotlin.myshoppal.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android10_kotlin.myshoppal.R
import com.android10_kotlin.myshoppal.databinding.ActivityAddressListBinding
import com.android10_kotlin.myshoppal.firestore.FirestoreClass
import com.android10_kotlin.myshoppal.models.Address
import com.android10_kotlin.myshoppal.ui.adapters.AddressListAdapter
import java.util.ArrayList

class AddressListActivity : BaseActivity() {

    private lateinit var mBinding: ActivityAddressListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityAddressListBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setupToolbar()

        mBinding.tvAddAddress.setOnClickListener {
            moveToAddEditAddressActivity()
        }
    }

    override fun onResume() {
        super.onResume()
        getAddressList()
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
        startActivity(Intent(this@AddressListActivity, AddEditAddressActivity::class.java))
    }

    fun successAddressListFromFirestore(addressList: ArrayList<Address>) {
        hideProgressDialog()

        if (addressList.size > 0) {
            mBinding.rvAddressList.visibility = View.VISIBLE
            mBinding.tvNoAddressFound.visibility = View.GONE

            mBinding.rvAddressList.layoutManager = LinearLayoutManager(this)
            mBinding.rvAddressList.setHasFixedSize(true)

            val adapter = AddressListAdapter(this)
            mBinding.rvAddressList.adapter = adapter
            adapter.show(addressList)
        } else {
            mBinding.rvAddressList.visibility = View.GONE
            mBinding.tvNoAddressFound.visibility = View.VISIBLE
        }
    }

}