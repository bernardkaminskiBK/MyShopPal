package com.android10_kotlin.myshoppal.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(
    val user_id: String = "",
    val name: String = "",
    val mobile_number: String = "",

    val address: String = "",
    val zipCode: String = "",
    val additionalNote: String = "",

    val type: String = "",
    val otherDetails: String = "",
    var id: String = "",
) : Parcelable