package com.efg.valu.sales.dispatcher

import com.efg.valu.sales.model.request.BaseRequestFactory
import com.efg.valu.sales.model.response.BaseModel
import com.efg.valu.sales.model.response.ResponseException
import com.efg.valu.sales.repository.local.BaseLocalRepo
import com.efg.valu.sales.repository.remote.BaseRemoteRepo
import com.efg.valu.sales.util.network.NetworkUtil
import java.lang.reflect.Type
import java.util.*

interface BaseDispatcher {

    val localRepo: BaseLocalRepo
    val remoteRepo: BaseRemoteRepo


    suspend fun fetchData(cash: Boolean, type: Type, requestFactory: BaseRequestFactory): Any? {
        var errorMessage: String? = null
        var responseCode: String? = null
        val response = try {
            val isNetworkConnected = NetworkUtil.isNetworkAvailable()
            if (isNetworkConnected) {
//                val account =
//                    (getCashedObject(SignInResponse::class.java) as SignInResponse?)?.payload?.data
//                requestFactory.baseRequestParam.account = account
                remoteRepo.fetchData(requestFactory)
            } else {
                errorMessage = NetworkUtil.NETWORK_ERROR_MSG
                responseCode = NetworkUtil.NO_INTERNET_CONNECTION_CODE
                null
            }
        } catch (ex: Exception) {
            errorMessage = NetworkUtil.CLIENT_ERROR_MSG
            responseCode = ""
            ex.printStackTrace()
            null
        }
        if (response != null && response.isSuccessful) {
            val body = response.body()
            if (body is BaseModel) {
                val cache = response.body()
                if (cash) {
                    localRepo.saveObject(cache, type)
                }
                return cache
            }
        } else if (cash) {
            val cashed = localRepo.getCachedObject(type)
            if (cashed != null)
                return cashed
            if (errorMessage == null) {
                errorMessage = NetworkUtil.SERVER_ERROR_MSG
                responseCode = "" + response?.code()
            }
        } else {
            if (errorMessage == null) {
                errorMessage = NetworkUtil.SERVER_ERROR_MSG
                responseCode = "" + response?.code()
            }
        }

        throw ResponseException(
            message = errorMessage,
            responseCode = responseCode,
            endPoint = requestFactory.getEndPoint()
        )
    }

    fun getCashedObject(type: Type): Any? = localRepo.getCachedObject(type)

    fun saveObject(instance: BaseModel?, type: Type, lastModifiedDate: Long = Date().time) =
        localRepo.saveObject(instance, type, lastModifiedDate)
}
