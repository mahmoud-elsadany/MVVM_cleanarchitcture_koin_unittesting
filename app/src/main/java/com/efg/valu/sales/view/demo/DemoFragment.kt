package com.efg.valu.sales.view.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.efg.valu.sales.R
import com.efg.valu.sales.databinding.FragmentDemoBinding
import com.efg.valu.sales.model.response.ErrorScreen
import com.efg.valu.sales.model.response.demo.SearchModel
import com.efg.valu.sales.util.extention.*
import com.efg.valu.sales.util.network.NetworkUtil
import com.efg.valu.sales.view.BasicFragment
import com.efg.valu.sales.viewModel.BaseViewModel
import com.efg.valu.sales.viewModel.demo.SearchViewModel
import org.koin.android.ext.android.inject

class DemoFragment : BasicFragment() {

    private val viewModel: SearchViewModel by inject()
    override fun getViewModel(): BaseViewModel = viewModel

    private lateinit var binding: FragmentDemoBinding

    private val searchAdapter = DemoAdapter(arrayListOf())

    override fun showLoading() {
        binding.demoBaseLayout.loadingView.show()
    }

    override fun hideLoading() {
        binding.demoBaseLayout.loadingView.gone()
    }

    override fun showError(error: ErrorScreen) {
        if (NetworkUtil.NO_INTERNET_CONNECTION_CODE == error.responseCode) {
            binding.demoBaseLayout.layoutError.show()
        } else
            super.showError(error)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!::binding.isInitialized) {
            binding = FragmentDemoBinding.inflate(inflater, container, false)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun observeViewModel() {
        viewModel.searchResults.observe(this) {
            it?.let {
                binding.listSearch.show()
                @Suppress("UNCHECKED_CAST")
                val list = it as? List<SearchModel> ?: listOf()
                searchAdapter.updateData(list)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeViewModel()
        viewModel.getSearchResults()
    }

    private fun initUI() {
        binding.listSearch.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = searchAdapter
        }
        binding.demoBaseLayout.btnTryAgain.setOnClickListener {
            binding.demoBaseLayout.layoutError.gone()
            viewModel.getSearchResults()
        }
        binding.demoSubmitFab.setOnClickListener {
            findNavController().navigate(R.id.imageSearchFragment)
        }
    }
}