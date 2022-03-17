package com.efg.valu.sales.repository.remote

import com.efg.valu.sales.model.request.BaseRequestFactory
import com.efg.valu.sales.model.response.BaseModel
import retrofit2.Response

interface BaseRemoteRepo {
      suspend fun  fetchData(requestFactory: BaseRequestFactory): Response<out BaseModel>?
}