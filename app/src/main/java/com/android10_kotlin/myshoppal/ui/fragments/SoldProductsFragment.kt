package com.android10_kotlin.myshoppal.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android10_kotlin.myshoppal.R
import com.android10_kotlin.myshoppal.databinding.FragmentSoldProductsBinding
import com.android10_kotlin.myshoppal.ui.activities.DashboardActivity

class SoldProductsFragment : BaseFragment() {

    private lateinit var mBinding: FragmentSoldProductsBinding

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
}