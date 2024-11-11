package com.m4.notes.utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper {
    private lateinit var sharedPreferences: SharedPreferences

    fun unit(context: Context){
        sharedPreferences = context.getSharedPreferences("shared", Context.MODE_PRIVATE)
    }

    var showOnBoard: Boolean
        get() = sharedPreferences.getBoolean("onboard", true)
        set(value: Boolean) = sharedPreferences.edit().putBoolean("onboard", value).apply()
}