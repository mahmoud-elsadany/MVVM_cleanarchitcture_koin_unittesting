package com.efg.valu.sales.dispatcher.demo

import com.efg.valu.sales.repository.local.BaseLocalRepo
import com.efg.valu.sales.repository.remote.demo.SearchRemoteRepo

class SearchDispatcherImpl(
    override val localRepo: BaseLocalRepo,
    override val remoteRepo: SearchRemoteRepo
) :
    SearchDispatcher