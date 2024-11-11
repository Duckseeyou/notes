package com.m4.notes

import android.app.Application
import com.m4.notes.utils.PreferenceHelper

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        val sharedPreferences = PreferenceHelper()
        sharedPreferences.unit(this)
    }
}