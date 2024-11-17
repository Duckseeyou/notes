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
        set(value) = sharedPreferences.edit().putBoolean("onboard", value).apply()

    var isLinearLayout: Boolean
        get() = sharedPreferences.getBoolean("linearlayout", true)
        set(value) = sharedPreferences.edit().putBoolean("linearlayout", value).apply()
}