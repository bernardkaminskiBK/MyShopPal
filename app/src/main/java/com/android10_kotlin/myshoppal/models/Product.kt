package com.android10_kotlin.myshoppal.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val user_id: String = "",
    val user_name: String = "",
    val title: String = "",
    val price: String = "",
    val description: String = "",
    val stock_quantity: Int = 0,
    val product_image: String = "",
    var id: String = ""
) : Parcelable

