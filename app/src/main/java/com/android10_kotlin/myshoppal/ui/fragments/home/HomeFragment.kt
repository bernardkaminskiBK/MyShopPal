package com.android10_kotlin.myshoppal.ui.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android10_kotlin.myshoppal.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

     private lateinit var mBinding: FragmentHomeBinding

     override fun onCreateView(
         inflater: LayoutInflater, container: ViewGroup?,
         savedInstanceState: Bundle?
     ): View? {
         mBinding = FragmentHomeBinding.inflate(inflater, container, false)
         return mBinding.root
     }

}