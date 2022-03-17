
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.efg.valu.sales.dispatcher.demo.SearchDispatcherImpl
import com.efg.valu.sales.model.request.demo.SearchRequestFactory
import com.efg.valu.sales.repository.local.BaseLocalRepo
import com.efg.valu.sales.repository.remote.demo.SearchRemoteRepo
import com.efg.valu.sales.repository.remote.demo.SearchRemoteRepoImpl
import com.efg.valu.sales.viewModel.demo.SearchViewModel
import com.google.common.truth.Truth.assertThat
import com.masadany.artbooknavigation.MainCoroutineRule
import com.masadany.artbooknavigation.getOrAwaitValueTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule



@ExperimentalCoroutinesApi
class SearchViewModelAndroidTest {

    //run all in one thread
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    //change asyncrouns data
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    @get:Rule
    var initRule: MockitoRule = MockitoJUnit.rule()


    @Mock
    private lateinit var searchRemoteRepo: SearchRemoteRepo
    @Mock
    private lateinit var localRepo: BaseLocalRepo

    @Mock
    private lateinit var searchRequestFactory: SearchRequestFactory
    private lateinit var searchRemoteRepoImpl: SearchRemoteRepoImpl

    private lateinit var viewModel: SearchViewModel

    @Before
    fun setup(){

        val searchDispatcher = SearchDispatcherImpl(localRepo,searchRemoteRepo)
        viewModel = SearchViewModel(searchDispatcher)
    }

//    @Test
//    fun `getSearchResultsTest`(){
//        viewModel.getSearchResults()
//
//        val value = viewModel.searchResults.getOrAwaitValueTest()
//
//
//        assertThat(value).isNotEmpty()
//    }
}