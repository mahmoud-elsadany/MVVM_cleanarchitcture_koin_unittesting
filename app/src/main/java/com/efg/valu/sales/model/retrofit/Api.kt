package com.efg.valu.sales.model.retrofit

import com.efg.valu.sales.model.Constant.API_KEY
import com.efg.valu.sales.model.response.demo.SearchResponse
import com.efg.valu.sales.model.response.searchImages.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface Api {
    @GET
    suspend fun getCountries(@Url url: String): Response<SearchResponse>?


    @GET
    suspend fun imageSearch(
        @Url url: String,
        @Query("q") searchQuery: String,
        @Query("key") apiKey: String = API_KEY,
        ): Response<ImageResponse>
}