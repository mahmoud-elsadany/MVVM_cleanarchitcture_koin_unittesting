package com.efg.valu.sales.di

import com.efg.valu.sales.dispatcher.demo.SearchDispatcher
import com.efg.valu.sales.dispatcher.demo.SearchDispatcherImpl
import com.efg.valu.sales.dispatcher.searchimages.SearchImagesDispatcher
import com.efg.valu.sales.dispatcher.searchimages.SearchImagesDispatcherImpl
import com.efg.valu.sales.model.retrofit.Service
import com.efg.valu.sales.repository.local.BaseLocalRepo
import com.efg.valu.sales.repository.local.BaseLocalRepoImpl
import com.efg.valu.sales.repository.remote.demo.SearchRemoteRepo
import com.efg.valu.sales.repository.remote.demo.SearchRemoteRepoImpl
import com.efg.valu.sales.repository.remote.searchimages.SearchImagesRemoteRepo
import com.efg.valu.sales.repository.remote.searchimages.SearchImagesRemoteRepoImpl
import com.efg.valu.sales.viewModel.demo.SearchViewModel
import com.efg.valu.sales.viewModel.searchimages.SearchImagesViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val localRepoModule = module {
    single<BaseLocalRepo> { BaseLocalRepoImpl() }
}
val networkModule = module {
    single { Service.getService() }
}

val search = module {
    viewModel { SearchViewModel(get()) }
    single<SearchDispatcher> { SearchDispatcherImpl(get(), get()) }
    single<SearchRemoteRepo> { SearchRemoteRepoImpl(get()) }
}


val searchImage = module {
    viewModel { SearchImagesViewModel(get()) }
    single<SearchImagesDispatcher> { SearchImagesDispatcherImpl(get(), get()) }
    single<SearchImagesRemoteRepo> { SearchImagesRemoteRepoImpl(get()) }
}
