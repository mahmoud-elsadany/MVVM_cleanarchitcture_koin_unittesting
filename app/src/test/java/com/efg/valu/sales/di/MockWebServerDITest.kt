package com.efg.valu.sales.di

import org.koin.dsl.module
import okhttp3.mockwebserver.MockWebServer

/**
 * Creates Mockwebserver instance for testing
 */
val MockWebServerDIPTest = module {

    factory {
        MockWebServer()
    }

}