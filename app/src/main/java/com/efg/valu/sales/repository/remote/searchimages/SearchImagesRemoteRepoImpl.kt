package com.efg.valu.sales.repository.remote.searchimages

import com.efg.valu.sales.model.request.BaseRequestFactory
import com.efg.valu.sales.model.request.demo.SearchRequestFactory
import com.efg.valu.sales.model.request.searchimages.SearchImageParam
import com.efg.valu.sales.model.request.searchimages.SearchImagesFactory
import com.efg.valu.sales.model.response.BaseModel
import com.efg.valu.sales.model.retrofit.Api
import retrofit2.Response

open class SearchImagesRemoteRepoImpl(var api: Api) : SearchImagesRemoteRepo {


    override suspend fun fetchData(requestFactory: BaseRequestFactory): Response<out BaseModel>? {
        return when (requestFactory) {
            is SearchImagesFactory -> {
                val param: SearchImageParam = requestFactory.baseRequestParam as SearchImageParam
                return api.imageSearch(requestFactory.getUrl(), param.searchText)
            }
            else -> null
        }
    }


}