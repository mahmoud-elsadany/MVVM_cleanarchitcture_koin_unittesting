package com.efg.valu.sales.dispatcher.searchimages

import com.efg.valu.sales.dispatcher.demo.SearchDispatcher
import com.efg.valu.sales.repository.local.BaseLocalRepo
import com.efg.valu.sales.repository.remote.demo.SearchRemoteRepo
import com.efg.valu.sales.repository.remote.searchimages.SearchImagesRemoteRepo

class SearchImagesDispatcherImpl (
    override val localRepo: BaseLocalRepo,
    override val remoteRepo: SearchImagesRemoteRepo
) :
    SearchImagesDispatcher