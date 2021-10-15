package com.android10_kotlin.myshoppal.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.GridLayoutManager
import com.android10_kotlin.myshoppal.R
import com.android10_kotlin.myshoppal.databinding.FragmentDashboardBinding
import com.android10_kotlin.myshoppal.firestore.FirestoreClass
import com.android10_kotlin.myshoppal.models.Product
import com.android10_kotlin.myshoppal.ui.activities.DashboardActivity
import com.android10_kotlin.myshoppal.ui.activities.SettingsActivity
import com.android10_kotlin.myshoppal.ui.adapters.DashboardListAdapter


class DashboardFragment : BaseFragment() {

    private lateinit var mBinding: FragmentDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dashboard_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(activity, SettingsActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentDashboardBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        if (requireActivity() is DashboardActivity) {
            (activity as DashboardActivity?)?.setToolbarTitle(getString(R.string.dashboard_title))
        }
    }

    override fun onResume() {
        super.onResume()
        getDashboardItemsList()
    }

    fun successDashboardItemsList(dashboardItemsList: ArrayList<Product>) {
        hideProgressDialog()

        val rvDashboard = mBinding.rvDashboardItems
        val tvNoItems = mBinding.tvNoDashboardItemsFound

        if (dashboardItemsList.size > 0) {
            rvDashboard.visibility = View.VISIBLE
            tvNoItems.visibility = View.GONE

            rvDashboard.layoutManager = GridLayoutManager(requireActivity(), 3)
            rvDashboard.setHasFixedSize(true)

            val dashboardListAdapter = DashboardListAdapter(this)
            rvDashboard.adapter = dashboardListAdapter
            dashboardListAdapter.show(dashboardItemsList)
        } else {
            rvDashboard.visibility = View.GONE
            tvNoItems.visibility = View.VISIBLE
        }

    }

    private fun getDashboardItemsList() {
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getDashboardItemList(this@DashboardFragment)
    }

}