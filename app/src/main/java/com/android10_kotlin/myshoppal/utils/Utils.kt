package com.android10_kotlin.myshoppal.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.android10_kotlin.myshoppal.models.User

object Utils {

    private var sharedPreferences: SharedPreferences? = null

    fun saveUserNameSharedPreferences(activity: Activity, user: User) {
        sharedPreferences =
            activity.getSharedPreferences(Constants.MY_SHOP_PAL_PREFERENCES, Context.MODE_PRIVATE)
        editSharedPreferences(user)
    }

    private fun editSharedPreferences(user: User) {
        val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
        editor.putString(Constants.LOGGED_IN_USERNAME, "${user.firstName} ${user.lastName}")
        editor.apply()
    }

    fun getUserNameSharedPreferences(activity: Activity): String {
        val sharedPreferences =
            activity.getSharedPreferences(Constants.MY_SHOP_PAL_PREFERENCES, Context.MODE_PRIVATE)
        return sharedPreferences.getString(Constants.LOGGED_IN_USERNAME, "")!!
    }

}