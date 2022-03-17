package com.efg.valu.sales.view.searchimages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import com.efg.valu.sales.databinding.FragmentImageSearchBinding
import com.efg.valu.sales.model.request.searchimages.SearchImageParam
import com.efg.valu.sales.model.response.ErrorScreen
import com.efg.valu.sales.util.extention.gone
import com.efg.valu.sales.util.extention.show
import com.efg.valu.sales.util.network.NetworkUtil
import com.efg.valu.sales.view.BasicFragment
import com.efg.valu.sales.viewModel.BaseViewModel
import com.efg.valu.sales.viewModel.searchimages.SearchImagesViewModel
import org.koin.android.ext.android.inject

class ImageSearchFragment : BasicFragment() {

    private val viewModel: SearchImagesViewModel by inject()
    override fun getViewModel(): BaseViewModel = viewModel

    private lateinit var binding: FragmentImageSearchBinding

    private val imagesAdapter = ImagesAdapter(arrayListOf())

    override fun showLoading() {
        binding.imageSearchBaseLayout.loadingView.show()
    }

    override fun hideLoading() {
        binding.imageSearchBaseLayout.loadingView.gone()
    }

    override fun showError(error: ErrorScreen) {
        if (NetworkUtil.NO_INTERNET_CONNECTION_CODE == error.responseCode) {
            binding.imageSearchBaseLayout.layoutError.show()
        } else
            super.showError(error)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!::binding.isInitialized) {
            binding = FragmentImageSearchBinding.inflate(inflater, container, false)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun observeViewModel() {
        viewModel.searchImages.observe(this) {
            it?.let {
                binding.imageSearchRecyclerView.show()

                val list = it.map { imageResult ->
                    imageResult?.previewURL
                }
                imagesAdapter.updateData(list)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeViewModel()
    }

    private fun initUI() {
        binding.imageSearchRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = imagesAdapter
        }

        binding.imageSearchEt.addTextChangedListener {
            if (it.toString().isNotEmpty()){
                viewModel.getImagesSearch(SearchImageParam(it.toString()))
            }
        }

    }
}