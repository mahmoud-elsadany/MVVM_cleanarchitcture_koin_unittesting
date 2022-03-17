package com.efg.valu.sales.viewModel.demo

import androidx.lifecycle.MutableLiveData
import com.efg.valu.sales.dispatcher.BaseDispatcher
import com.efg.valu.sales.dispatcher.demo.SearchDispatcher
import com.efg.valu.sales.model.request.demo.SearchRequestFactory
import com.efg.valu.sales.model.response.demo.SearchModel
import com.efg.valu.sales.model.response.demo.SearchResponse
import com.efg.valu.sales.viewModel.BaseViewModel

class SearchViewModel(private val searchDispatcher: SearchDispatcher) : BaseViewModel() {
    override fun getDispatcher(): BaseDispatcher = searchDispatcher
    val searchResults = MutableLiveData<List<SearchModel>>()

    fun getSearchResults() {
        fetchData(
            false,
            SearchResponse::class.java,
            SearchRequestFactory()
        ){
            if (it is SearchResponse) {
                searchResults.value = it.metadata.results
            }
        }
    }

}