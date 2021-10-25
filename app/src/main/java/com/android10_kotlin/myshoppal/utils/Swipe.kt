package com.android10_kotlin.myshoppal.utils

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.android10_kotlin.myshoppal.R
import com.android10_kotlin.myshoppal.firestore.FirestoreClass
import com.android10_kotlin.myshoppal.models.Address
import com.android10_kotlin.myshoppal.ui.activities.AddressListActivity
import com.android10_kotlin.myshoppal.ui.adapters.AddressListAdapter
import com.myshoppal.utils.SwipeToDeleteCallback

object Swipe {

    fun editSwipe(activity: AddressListActivity, recyclerView: RecyclerView) {
        val editSwipeHandler = object : SwipeToEditCallback(activity) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = recyclerView.adapter as AddressListAdapter
                adapter.notifyEditItem(activity, viewHolder.adapterPosition)
            }
        }
        val editItemTouchHelper = ItemTouchHelper(editSwipeHandler)
        editItemTouchHelper.attachToRecyclerView(recyclerView)
    }

    fun deleteSwipe(activity: AddressListActivity, recyclerView: RecyclerView, addressList: ArrayList<Address>) {
        val deleteSwipeHandler = object : SwipeToDeleteCallback(activity) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                activity.showProgressDialog(activity.resources.getString(R.string.please_wait))
                FirestoreClass().deleteAddress(activity, addressList[viewHolder.adapterPosition].id)
            }
        }
        val deleteItemTouchHelper = ItemTouchHelper(deleteSwipeHandler)
        deleteItemTouchHelper.attachToRecyclerView(recyclerView)
    }

}