package com.efg.valu.sales

import android.content.Context
import com.efg.valu.sales.di.appComponents
import com.efg.valu.sales.di.networkComponent
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class Initializer(private val context: Context) {

    fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(context)
            modules(
                appComponents +
                        networkComponent
            )
        }
    }
}