package com.efg.valu.sales.model.request.searchimages

import com.efg.valu.sales.model.Constant
import com.efg.valu.sales.model.request.BaseRequestFactory
import com.efg.valu.sales.model.request.BaseRequestParam
import com.efg.valu.sales.model.retrofit.EndPoints

class SearchImagesFactory(param:SearchImageParam) : BaseRequestFactory() {
    override fun getEndPoint(): String = EndPoints.SEARCHImage
    override var baseRequestParam: BaseRequestParam = param
    override fun getUrl() = Constant.IMAGE_SEARCH_BASE_URL + EndPoints.SEARCHImage
}

data class SearchImageParam(val searchText: String) : BaseRequestParam()