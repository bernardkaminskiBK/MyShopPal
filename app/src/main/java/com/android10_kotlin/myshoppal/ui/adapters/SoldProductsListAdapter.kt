package com.android10_kotlin.myshoppal.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android10_kotlin.myshoppal.databinding.ItemListSoldProductsLayoutBinding
import com.android10_kotlin.myshoppal.models.SoldProduct
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
        val myOrder = soldProductsList[position]

        GlideLoader(context).loadPictureIntoView(myOrder.image, holder.soldProductImage)
        holder.soldProductName.text = myOrder.title
        holder.soldProductPrice.text = "${myOrder.total_amount} €"

        holder.itemView.setOnClickListener {
//            val intent = Intent(context, SoldProductDetailsActivity::class.java)
//            intent.putExtra(Constants.EXTRA_MY_ORDER_DETAILS, myOrder)
//            context.startActivity(intent)
            Toast.makeText(context, "Sold product details activity", Toast.LENGTH_SHORT).show()
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