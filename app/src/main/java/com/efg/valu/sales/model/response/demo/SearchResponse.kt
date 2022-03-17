package com.efg.valu.sales.model.response.demo

import androidx.annotation.Keep
import com.efg.valu.sales.model.response.BaseModel
import com.google.gson.annotations.SerializedName
import java.io.Serializable


@Keep
data class SearchResponse(val metadata: SearchResultsData) : BaseModel()

@Keep
data class SearchResultsData(val results: ArrayList<SearchModel>) : Serializable

@Keep
data class SearchModel(
    val name: String,
    val brand: String,
    @SerializedName("max_saving_percentage") val maxSavingPercentage: Int,
    val price: String,
    @SerializedName("special_price") val specialPrice: String,
    val image: String,
    @SerializedName("rating_average") val ratingAverage: Float?
)