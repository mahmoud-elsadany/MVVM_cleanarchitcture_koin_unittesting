package com.efg.valu.sales.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.efg.valu.sales.dispatcher.BaseDispatcher
import com.efg.valu.sales.model.request.BaseRequestFactory
import com.efg.valu.sales.model.response.ErrorScreen
import com.efg.valu.sales.model.response.ResponseException
import kotlinx.coroutines.*
import java.lang.reflect.Type

abstract class BaseViewModel() : ViewModel() {

    var errorDialog: MutableLiveData<ErrorScreen> = MutableLiveData()
    var showFullLoading: MutableLiveData<Boolean> = MutableLiveData()

    private var connectJob: Job? = null


    fun fetchData(
        cash: Boolean, type: Type, requestFactory: BaseRequestFactory,
        proceedResponse: (t: Any?) -> Unit
    ) {


        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            if (throwable is ResponseException)
                onError(ErrorScreen(throwable.message, throwable.responseCode))
        }
        showFullLoading.value = true
        connectJob = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = getDispatcher()?.fetchData(cash, type, requestFactory)
            withContext(Dispatchers.Main) {
                proceedResponse(response)
                showFullLoading.value = false
            }
        }
    }

    private fun onError(errorScreen: ErrorScreen) {
        errorDialog.postValue(errorScreen)
        showFullLoading.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        connectJob?.cancel()
    }

    fun clear() {
        onCleared()
    }

    abstract fun getDispatcher(): BaseDispatcher?
}