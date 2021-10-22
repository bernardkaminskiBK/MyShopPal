package com.android10_kotlin.myshoppal.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.android10_kotlin.myshoppal.R
import com.android10_kotlin.myshoppal.databinding.ItemListLayoutBinding
import com.android10_kotlin.myshoppal.firestore.FirestoreClass
import com.android10_kotlin.myshoppal.models.CartItem
import com.android10_kotlin.myshoppal.ui.activities.CartListActivity
import com.android10_kotlin.myshoppal.utils.Constants
import com.android10_kotlin.myshoppal.utils.GlideLoader

class CartListAdapter(private val context: Context) :
    RecyclerView.Adapter<CartListAdapter.ViewHolder>(), View.OnClickListener {

    private var cartItems: List<CartItem> = listOf()

    private var cartItem: CartItem? = null

    class ViewHolder(view: ItemListLayoutBinding) : RecyclerView.ViewHolder(view.root) {
        val cartItemImage = view.ivItemImage
        val cartItemName = view.tvItemName
        val cartItemPrice = view.tvItemPrice
        val cartQuantity = view.tvCartQuantity
        val ibAddAmount = view.ibAddToCart
        val ibRemoveAmount = view.ibRemoveFromCard
        val ibDeleteProduct = view.ibDeleteProduct
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemListLayoutBinding =
            ItemListLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        cartItem = cartItems[position]

        GlideLoader(context).loadPictureIntoView(cartItem!!.image, holder.cartItemImage)
        holder.cartItemName.text = cartItem!!.title
        holder.cartItemPrice.text = "${cartItem!!.price} €"
        holder.cartQuantity.text = cartItem!!.cart_quantity

        checkCartQuantity(cartItem!!.cart_quantity, holder)

        holder.ibDeleteProduct.setOnClickListener(this)
        holder.ibRemoveAmount.setOnClickListener(this)
        holder.ibAddAmount.setOnClickListener(this)

    }

    override fun onClick(v: View) {
            when(v.id) {
               R.id.ib_delete_product -> {
                    when (context) {
                        is CartListActivity -> {
                            context.showProgressDialog(context.getString(R.string.please_wait))
                        }
                    }
                    FirestoreClass().removeItemFromCart(context, cartItem!!.id)
                }
                R.id.ib_remove_from_card -> {
                    cartItem?.let { removeFromCart(it) }
                }
                R.id.ib_add_to_cart -> {
                    cartItem?.let {  addToCart(cartItem!!) }
                }

            }
    }

    private fun removeFromCart(cartItem: CartItem) {
        if (cartItem.cart_quantity == "1") {
            FirestoreClass().removeItemFromCart(context, cartItem.id)
        } else {
            val cartQuantity: Int = cartItem.cart_quantity.toInt()
            val itemHashMap = HashMap<String, Any>()

            itemHashMap[Constants.CART_QUANTITY] = (cartQuantity - 1).toString()

            if (context is CartListActivity) {
                context.showProgressDialog(context.getString(R.string.please_wait))
            }

            FirestoreClass().updateMyCart(context, cartItem.id, itemHashMap)
        }
    }

    private fun addToCart(cartItem: CartItem) {
        val cartQuantity: Int = cartItem.cart_quantity.toInt()

        if (cartQuantity < cartItem.stock_quantity.toInt()) {
            val itemHashMap = HashMap<String, Any>()
            itemHashMap[Constants.CART_QUANTITY] = (cartQuantity + 1).toString()

            if (context is CartListActivity) {
                context.showProgressDialog(context.resources.getString(R.string.please_wait))
            }
            FirestoreClass().updateMyCart(context, cartItem.id, itemHashMap)
        } else {
            if (context is CartListActivity) {
                context.showErrorSnackBar(
                    context.resources.getString(
                        R.string.msg_for_available_stock,
                        cartItem.stock_quantity
                    ),
                    true
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

    fun show(list: List<CartItem>) {
        cartItems = list
        notifyDataSetChanged()
    }

    private fun checkCartQuantity(cartQuantity: String, holder: ViewHolder) {
        if (cartQuantity.toInt() == 0) {
            holder.ibRemoveAmount.visibility = View.GONE
            holder.ibAddAmount.visibility = View.GONE

            holder.cartQuantity.text = context.getString(R.string.out_of_stock)
            holder.cartQuantity
                .setTextColor(ContextCompat.getColor(context, R.color.colorSnackBarError))
        } else {
            holder.ibRemoveAmount.visibility = View.VISIBLE
            holder.ibAddAmount.visibility = View.VISIBLE

            holder.cartQuantity
                .setTextColor(ContextCompat.getColor(context, R.color.colorSecondaryText))

        }
    }



}