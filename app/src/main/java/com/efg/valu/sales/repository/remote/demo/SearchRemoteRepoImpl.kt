package com.efg.valu.sales.repository.remote.demo

import com.efg.valu.sales.model.request.BaseRequestFactory
import com.efg.valu.sales.model.request.demo.SearchRequestFactory
import com.efg.valu.sales.model.response.BaseModel
import com.efg.valu.sales.model.response.demo.SearchResponse
import com.efg.valu.sales.model.retrofit.Api
import retrofit2.Response

open class SearchRemoteRepoImpl(var api: Api) : SearchRemoteRepo {
        override suspend fun fetchData(requestFactory: BaseRequestFactory): Response<out BaseModel>? {
        return when (requestFactory) {
            is SearchRequestFactory -> {

                return api.getCountries(requestFactory.getUrl())
            }
            else -> null
        }
    }


}