package com.efg.valu.sales.view

import android.os.Bundle
import com.efg.valu.sales.model.response.ErrorScreen
import com.efg.valu.sales.view.util.BaseView
import com.efg.valu.sales.viewModel.BaseViewModel
import android.widget.Toast
import androidx.lifecycle.Observer


abstract class BasicFragment : BaseFragment(), BaseView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getViewModel()?.showFullLoading?.observe(this, showFullLoading)
        getViewModel()?.errorDialog?.observe(this, showErrorUi)
    }

    override fun showError(error: ErrorScreen) {
        if (error.message != null)
            Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
    }

    abstract fun getViewModel(): BaseViewModel?

    private val showFullLoading = Observer<Boolean> {
        if (it)
            showLoading()
        else
            hideLoading()
    }

    private val showErrorUi = Observer<ErrorScreen> { showError(it) }

    override fun onDestroy() {
        super.onDestroy()
        getViewModel()?.clear()
    }
}