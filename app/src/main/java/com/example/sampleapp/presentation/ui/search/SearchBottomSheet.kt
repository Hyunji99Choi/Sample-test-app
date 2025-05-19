package com.example.sampleapp.presentation.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sampleapp.databinding.BottomSheetSearchBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchBottomSheet: BottomSheetDialogFragment() {

    lateinit var binding: BottomSheetSearchBinding
    private val adapter: SearchPagingAdapter by lazy { SearchPagingAdapter() }

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = BottomSheetSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvSearchList.apply {
            adapter = SearchPagingAdapter()
            layoutManager = GridLayoutManager(
                context,
                2
            )
        }

        binding.etSearch.doAfterTextChanged { text ->
            viewModel.search(text.toString())
        }
    }
}