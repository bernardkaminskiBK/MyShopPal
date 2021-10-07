package com.android10_kotlin.myshoppal.ui.fragments.notifications

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android10_kotlin.myshoppal.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private lateinit var mBinding: FragmentNotificationsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return mBinding.root
    }

}