package com.android10_kotlin.myshoppal.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android10_kotlin.myshoppal.databinding.ItemAddressLayoutBinding
import com.android10_kotlin.myshoppal.models.Address

class AddressListAdapter(private val context: Context) :
    RecyclerView.Adapter<AddressListAdapter.ViewHolder>() {

    private var addresses: List<Address> = listOf()

    class ViewHolder(view: ItemAddressLayoutBinding) : RecyclerView.ViewHolder(view.root) {
        val name = view.tvAddressFullName
        val details = view.tvAddressDetails
        val phoneNumber = view.tvAddressMobileNumber
        val type = view.tvAddressType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemAddressLayoutBinding =
            ItemAddressLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val address = addresses[position]

        holder.name.text = address.name
        holder.type.text = address.type
        holder.details.text = "${address.address}, ${address.zipCode}"
        holder.phoneNumber.text = address.mobile_number
    }


    override fun getItemCount(): Int {
        return addresses.size
    }

    fun show(list: List<Address>) {
        addresses = list
        notifyDataSetChanged()
    }

}