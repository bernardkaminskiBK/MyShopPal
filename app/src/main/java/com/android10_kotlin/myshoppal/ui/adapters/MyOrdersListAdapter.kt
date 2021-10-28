package com.android10_kotlin.myshoppal.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android10_kotlin.myshoppal.databinding.ItemListMyOrderLayoutBinding
import com.android10_kotlin.myshoppal.models.Order
import com.android10_kotlin.myshoppal.ui.activities.MyOrderDetailsActivity
import com.android10_kotlin.myshoppal.utils.Constants
import com.android10_kotlin.myshoppal.utils.GlideLoader

class MyOrdersListAdapter(private val context: Context) :
    RecyclerView.Adapter<MyOrdersListAdapter.ViewHolder>() {

    private var myOrdersList: List<Order> = listOf()

    class ViewHolder(view: ItemListMyOrderLayoutBinding) : RecyclerView.ViewHolder(view.root) {
        val cartItemImage = view.ivItemImage
        val cartItemName = view.tvItemName
        val cartItemPrice = view.tvItemPrice
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemListMyOrderLayoutBinding =
            ItemListMyOrderLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val myOrder = myOrdersList[position]

        GlideLoader(context).loadPictureIntoView(myOrder.image, holder.cartItemImage)
        holder.cartItemName.text = myOrder.title
        holder.cartItemPrice.text = "${myOrder.total_amount} €"

        holder.itemView.setOnClickListener {
            val intent = Intent(context, MyOrderDetailsActivity::class.java)
            intent.putExtra(Constants.EXTRA_MY_ORDER_DETAILS, myOrder)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return myOrdersList.size
    }

    fun show(list: List<Order>) {
        myOrdersList = list
        notifyDataSetChanged()
    }

}