package com.android10_kotlin.myshoppal.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android10_kotlin.myshoppal.R
import com.android10_kotlin.myshoppal.databinding.FragmentOrdersBinding
import com.android10_kotlin.myshoppal.firestore.FirestoreClass
import com.android10_kotlin.myshoppal.models.Order
import com.android10_kotlin.myshoppal.ui.activities.DashboardActivity
import com.android10_kotlin.myshoppal.ui.adapters.MyOrdersListAdapter
import java.util.ArrayList

class OrdersFragment : BaseFragment() {

    private lateinit var mBinding: FragmentOrdersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getMyOrdersList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentOrdersBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (requireActivity() is DashboardActivity) {
            (activity as DashboardActivity?)?.setToolbarTitle(getString(R.string.orders_title))
        }
    }

    fun populateOrdersListInUI(ordersList: ArrayList<Order>) {
        hideProgressDialog()

        if (ordersList.size > 0) {
            mBinding.tvNoOrdersFound.visibility = View.GONE
            mBinding.rvMyOrderItems.visibility = View.VISIBLE

            mBinding.rvMyOrderItems.layoutManager = LinearLayoutManager(this.requireContext())
            mBinding.rvMyOrderItems.setHasFixedSize(true)

            val adapter = MyOrdersListAdapter(this.requireContext())
            mBinding.rvMyOrderItems.adapter = adapter
            adapter.show(ordersList)
        } else {
            mBinding.tvNoOrdersFound.visibility = View.VISIBLE
            mBinding.rvMyOrderItems.visibility = View.GONE
        }
    }

    private fun getMyOrdersList() {
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getMyOrdersList(this)
    }

}