package com.android10_kotlin.myshoppal.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.GridLayoutManager
import com.android10_kotlin.myshoppal.R
import com.android10_kotlin.myshoppal.databinding.FragmentProductsBinding
import com.android10_kotlin.myshoppal.firestore.FirestoreClass
import com.android10_kotlin.myshoppal.models.Product
import com.android10_kotlin.myshoppal.ui.activities.AddProductActivity
import com.android10_kotlin.myshoppal.ui.activities.DashboardActivity
import com.android10_kotlin.myshoppal.ui.adapters.DashboardListAdapter
import com.android10_kotlin.myshoppal.ui.adapters.ProductsListAdapter

class ProductsFragment : BaseFragment() {

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

    fun successProductsListFromFirestore(productsList: ArrayList<Product>) {
        hideProgressDialog()

        val rvProductList = mBinding.rvMyProductItems
        val tvNoProduct = mBinding.tvNoProductsFound

        if (productsList.size > 0) {
            rvProductList.visibility = View.VISIBLE
            tvNoProduct.visibility = View.GONE

            rvProductList.layoutManager = GridLayoutManager(requireActivity(), 3)
            rvProductList.setHasFixedSize(true)

            val productListAdapter = ProductsListAdapter(this)
            rvProductList.adapter = productListAdapter
            productListAdapter.show(productsList)
        } else {
            rvProductList.visibility = View.GONE
            tvNoProduct.visibility = View.VISIBLE
        }

    }

    private fun getProductsListFromFirestore() {
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getProductList(this)
    }

    override fun onResume() {
        super.onResume()
        getProductsListFromFirestore()
    }

}