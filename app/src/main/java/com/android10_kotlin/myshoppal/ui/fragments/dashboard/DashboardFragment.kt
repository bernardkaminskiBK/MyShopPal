package com.android10_kotlin.myshoppal.ui.fragments.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android10_kotlin.myshoppal.databinding.FragmentDashboardBinding


class DashboardFragment : Fragment() {

    private lateinit var mBinding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentDashboardBinding.inflate(inflater, container, false)
        return mBinding.root
    }

}