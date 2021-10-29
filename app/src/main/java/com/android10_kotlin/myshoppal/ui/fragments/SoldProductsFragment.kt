package com.android10_kotlin.myshoppal.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android10_kotlin.myshoppal.R
import com.android10_kotlin.myshoppal.databinding.FragmentSoldProductsBinding
import com.android10_kotlin.myshoppal.firestore.FirestoreClass
import com.android10_kotlin.myshoppal.models.SoldProduct
import com.android10_kotlin.myshoppal.ui.activities.DashboardActivity
import com.android10_kotlin.myshoppal.ui.adapters.SoldProductsListAdapter
import java.util.ArrayList

class SoldProductsFragment : BaseFragment() {

    private lateinit var mBinding: FragmentSoldProductsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSoldProducts()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentSoldProductsBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (requireActivity() is DashboardActivity) {
            (activity as DashboardActivity?)?.setToolbarTitle(getString(R.string.sold_products_title))
        }
    }

    fun successSoldProductsList(soldProducts: ArrayList<SoldProduct>) {
        hideProgressDialog()

        if (soldProducts.size > 0) {
            mBinding.rvSoldProductItems.visibility = View.VISIBLE
            mBinding.tvNoSoldProductsFound.visibility = View.GONE

            mBinding.rvSoldProductItems.layoutManager = LinearLayoutManager(activity)
            mBinding.rvSoldProductItems.setHasFixedSize(true)

            val soldProductsListAdapter =
                SoldProductsListAdapter(requireActivity())
            mBinding.rvSoldProductItems.adapter = soldProductsListAdapter
            soldProductsListAdapter.show(soldProducts)
        } else {
            mBinding.rvSoldProductItems.visibility = View.GONE
            mBinding.tvNoSoldProductsFound.visibility = View.VISIBLE
        }
    }

    private fun getSoldProducts() {
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getSoldProductsList(this)
    }

}