package com.efg.valu.sales.repo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.efg.valu.sales.base.BaseUTTest
import com.efg.valu.sales.di.configureTestAppComponent
import com.efg.valu.sales.model.request.searchimages.SearchImageParam
import com.efg.valu.sales.model.request.searchimages.SearchImagesFactory
import com.efg.valu.sales.model.response.searchImages.ImageResponse
import com.efg.valu.sales.model.retrofit.Api
import com.efg.valu.sales.repository.remote.searchimages.SearchImagesRemoteRepoImpl
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import retrofit2.Response
import java.net.HttpURLConnection

@RunWith(JUnit4::class)
class SearchImagesRepoTest : BaseUTTest() {

    //Target
    private lateinit var mRepo: SearchImagesRemoteRepoImpl
    //Inject api service created with koin
   val mAPIService : Api by inject()

    //Inject Mockwebserver created with koin
    val mockWebServer : MockWebServer by inject()


    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @Before
    fun start(){
        super.setUp()

        startKoin{ modules(configureTestAppComponent(getMockWebServerUrl()))}
    }

    @Test
    fun test_login_repo_retrieves_expected_data() =  runBlocking{

        mockNetworkResponseWithFileContent("success_test_response.json", HttpURLConnection.HTTP_OK)
        mRepo = SearchImagesRemoteRepoImpl(mAPIService)

        val param = SearchImageParam("mona")
        val factory = SearchImagesFactory(param)
        val dataReceived: Response<ImageResponse> = mRepo.fetchData(factory) as Response<ImageResponse>

        assertThat(dataReceived.body()?.totalHits).isEqualTo(52)

    }

}