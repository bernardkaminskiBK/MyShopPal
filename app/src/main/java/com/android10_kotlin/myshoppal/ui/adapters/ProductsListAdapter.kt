package com.android10_kotlin.myshoppal.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.android10_kotlin.myshoppal.R
import com.android10_kotlin.myshoppal.databinding.CardViewProductsItemListBinding
import com.android10_kotlin.myshoppal.models.Product
import com.android10_kotlin.myshoppal.ui.fragments.ProductsFragment
import com.android10_kotlin.myshoppal.utils.GlideLoader

class ProductsListAdapter(private val fragment: Fragment) :
    RecyclerView.Adapter<ProductsListAdapter.ViewHolder>() {

    private var products: List<Product> = listOf()

    class ViewHolder(view: CardViewProductsItemListBinding) : RecyclerView.ViewHolder(view.root) {
        val productImage = view.productImg
        val productTitle = view.productTitle
        val productQuantity = view.productQuantity
        val productPrice = view.productPrice
        val ibMore = view.ibMore
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: CardViewProductsItemListBinding =
            CardViewProductsItemListBinding.inflate(
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

        holder.ibMore.setOnClickListener {
            setPopUpMenu(fragment, holder.ibMore, product)
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun show(list: ArrayList<Product>) {
        products = list
        notifyDataSetChanged()
    }

    private fun setPopUpMenu(fragment: Fragment, imageButton: ImageButton, product: Product) {
        val popup = PopupMenu(fragment.context, imageButton)
        popup.menuInflater.inflate(R.menu.card_view_products_item_list_dashboard_menu, popup.menu)

        popup.setOnMenuItemClickListener {
            if (it.itemId == R.id.action_edit_product) {
                Toast.makeText(fragment.requireContext(), "Edit product", Toast.LENGTH_SHORT).show()
            } else if (it.itemId == R.id.action_delete_product) {
                if (fragment is ProductsFragment) {
                    fragment.deleteProduct(product)
                }
            }
            true
        }
        popup.show()
    }

}