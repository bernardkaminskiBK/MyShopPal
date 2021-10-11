package com.android10_kotlin.myshoppal.ui.fragments.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.android10_kotlin.myshoppal.R
import com.android10_kotlin.myshoppal.databinding.FragmentProductsBinding
import com.android10_kotlin.myshoppal.ui.activities.AddProductActivity
import com.android10_kotlin.myshoppal.ui.activities.DashboardActivity

class ProductsFragment : Fragment() {

    private lateinit var mBinding: FragmentProductsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.products_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add_product -> {
                startActivity(Intent(activity, AddProductActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentProductsBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (requireActivity() is DashboardActivity) {
            (activity as DashboardActivity?)?.setToolbarTitle(getString(R.string.products_title))
        }
    }

}