package com.example.newsday

import android.app.Application
import android.content.Context

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        private lateinit var context: BaseApplication

        @JvmStatic
        fun getContext(): Context {
            return context
        }
    }

}