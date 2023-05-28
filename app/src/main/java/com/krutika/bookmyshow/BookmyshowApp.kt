package com.krutika.bookmyshow

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import java.util.*
import javax.inject.Inject

@HiltAndroidApp
class BookmyshowApp : Application() {

    @Inject
    fun getContext(): Context {
        return applicationContext
    }

    override fun onCreate() {
        super.onCreate()
        if (instance == null) {
            instance = this
        }
    }

    companion object {
        var instance: BookmyshowApp? = null
            private set
    }
}