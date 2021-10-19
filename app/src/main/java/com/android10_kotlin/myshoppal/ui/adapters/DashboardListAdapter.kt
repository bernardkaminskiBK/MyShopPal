package com.android10_kotlin.myshoppal.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.android10_kotlin.myshoppal.databinding.CardViewProductsItemListDashboardBinding
import com.android10_kotlin.myshoppal.firestore.FirestoreClass
import com.android10_kotlin.myshoppal.models.Product
import com.android10_kotlin.myshoppal.utils.GlideLoader

class DashboardListAdapter(private val fragment: Fragment) :
    RecyclerView.Adapter<DashboardListAdapter.ViewHolder>() {

    private var products: List<Product> = listOf()

    class ViewHolder(view: CardViewProductsItemListDashboardBinding) : RecyclerView.ViewHolder(view.root) {
        val productImage = view.productImg
        val productTitle = view.productTitle
        val productQuantity = view.productQuantity
        val productPrice = view.productPrice
        val btnAddToCart = view.btnAddToCart

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: CardViewProductsItemListDashboardBinding =
            CardViewProductsItemListDashboardBinding.inflate(
                LayoutInflater.from(fragment.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]

        GlideLoader(fragment.requireContext())
            .loadPictureIntoView(product.product_image, holder.productImage)

        holder.productTitle.text = product.title
        holder.productQuantity.text = "${product.stock_quantity} pc"
        holder.productPrice.text = "$${product.price}"

        if(FirestoreClass().getCurrentUserID() == product.user_id) {
            holder.btnAddToCart.visibility = View.INVISIBLE
        } else {
            holder.btnAddToCart.visibility = View.VISIBLE
        }

    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun show(list: ArrayList<Product>) {
        products = list
        notifyDataSetChanged()
    }

}