package com.example.cardiodinamictest


import android.content.Context
import androidx.multidex.MultiDex
import com.google.android.play.core.splitcompat.SplitCompatApplication

class App : SplitCompatApplication() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}