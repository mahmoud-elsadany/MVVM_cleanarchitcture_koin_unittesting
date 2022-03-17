package com.efg.valu.sales.view.util

import com.efg.valu.sales.model.response.ErrorScreen

interface BaseView {

    fun showLoading()
    fun hideLoading()
    fun showError(error: ErrorScreen)

}