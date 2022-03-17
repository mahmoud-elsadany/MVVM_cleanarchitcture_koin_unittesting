package com.efg.valu.sales.viewModel.searchimages

import androidx.lifecycle.MutableLiveData
import com.efg.valu.sales.dispatcher.BaseDispatcher
import com.efg.valu.sales.dispatcher.searchimages.SearchImagesDispatcher
import com.efg.valu.sales.model.request.searchimages.SearchImageParam
import com.efg.valu.sales.model.request.searchimages.SearchImagesFactory
import com.efg.valu.sales.model.response.searchImages.ImageResponse
import com.efg.valu.sales.model.response.searchImages.ImageResult
import com.efg.valu.sales.viewModel.BaseViewModel

class SearchImagesViewModel(private val searchImagesDispatcher: SearchImagesDispatcher) : BaseViewModel() {
    override fun getDispatcher(): BaseDispatcher = searchImagesDispatcher
    val searchImages = MutableLiveData<List<ImageResult?>>()


    fun getImagesSearch(param: SearchImageParam){

        val factory = SearchImagesFactory(param)

        fetchData(false, ImageResponse::class.java,factory){
            if (it is ImageResponse) {
                searchImages.value = it.hits
            }
        }
    }

}