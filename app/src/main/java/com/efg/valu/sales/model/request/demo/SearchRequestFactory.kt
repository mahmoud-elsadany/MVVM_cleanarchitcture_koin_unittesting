package com.efg.valu.sales.model.request.demo

import com.efg.valu.sales.model.request.BaseRequestFactory
import com.efg.valu.sales.model.request.BaseRequestParam
import com.efg.valu.sales.model.retrofit.EndPoints

class SearchRequestFactory : BaseRequestFactory() {
    override fun getEndPoint(): String = EndPoints.SEARCH
    override var baseRequestParam = BaseRequestParam()
}