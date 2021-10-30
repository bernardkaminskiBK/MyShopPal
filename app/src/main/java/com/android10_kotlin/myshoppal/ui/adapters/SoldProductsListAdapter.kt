package com.android10_kotlin.myshoppal.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android10_kotlin.myshoppal.databinding.ItemListSoldProductsLayoutBinding
import com.android10_kotlin.myshoppal.models.SoldProduct
import com.android10_kotlin.myshoppal.ui.activities.SoldProductDetailsActivity
import com.android10_kotlin.myshoppal.utils.Constants
import com.android10_kotlin.myshoppal.utils.GlideLoader

class SoldProductsListAdapter(private val context: Context) :
    RecyclerView.Adapter<SoldProductsListAdapter.ViewHolder>() {

    private var soldProductsList: List<SoldProduct> = listOf()

    class ViewHolder(view: ItemListSoldProductsLayoutBinding) : RecyclerView.ViewHolder(view.root) {
        val soldProductImage = view.ivItemImage
        val soldProductName = view.tvItemName
        val soldProductPrice = view.tvItemPrice
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemListSoldProductsLayoutBinding =
            ItemListSoldProductsLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val soldProduct = soldProductsList[position]

        GlideLoader(context).loadPictureIntoView(soldProduct.image, holder.soldProductImage)
        holder.soldProductName.text = soldProduct.title
        holder.soldProductPrice.text = "${soldProduct.total_amount} €"

        holder.itemView.setOnClickListener {
            val intent = Intent(context, SoldProductDetailsActivity::class.java)
            intent.putExtra(Constants.EXTRA_SOLD_PRODUCT_DETAILS, soldProduct)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return soldProductsList.size
    }

    fun show(list: List<SoldProduct>) {
        soldProductsList = list
        notifyDataSetChanged()
    }
}