package com.android10_kotlin.myshoppal.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android10_kotlin.myshoppal.databinding.ItemListLayoutBinding
import com.android10_kotlin.myshoppal.models.CartItem
import com.android10_kotlin.myshoppal.utils.GlideLoader

class CartListAdapter(private val context: Context) :
    RecyclerView.Adapter<CartListAdapter.ViewHolder>() {

    private var cartItems: List<CartItem> = listOf()

    class ViewHolder(view: ItemListLayoutBinding) : RecyclerView.ViewHolder(view.root) {
        val cartItemImage = view.ivItemImage
        val cartItemName = view.tvItemName
        val cartItemPrice = view.tvItemPrice
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemListLayoutBinding =
            ItemListLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cartItem = cartItems[position]

        GlideLoader(context).loadPictureIntoView(cartItem.image, holder.cartItemImage)
        holder.cartItemName.text = cartItem.title
        holder.cartItemPrice.text = cartItem.price
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

    fun show(list: List<CartItem>) {
        cartItems = list
        notifyDataSetChanged()
    }

}