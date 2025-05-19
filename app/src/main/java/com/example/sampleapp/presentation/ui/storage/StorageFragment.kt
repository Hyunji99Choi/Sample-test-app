package com.example.sampleapp.presentation.ui.storage

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.graphics.Color
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.sampleapp.databinding.FragmentStorageBinding
import com.example.sampleapp.presentation.ui.BaseFragment
import com.example.sampleapp.presentation.ui.search.SearchPagingAdapter
import com.example.sampleapp.presentation.util.repeatOnStarted
import com.example.sampleapp.presentation.ui.search.SearchViewModel
import com.example.sampleapp.presentation.util.StaggeredGridSpacingItemDecoration
import com.example.sampleapp.R
import com.example.sampleapp.presentation.util.getPagingListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class StorageFragment : BaseFragment<FragmentStorageBinding>() {

    private val viewModel: StorageViewModel by viewModels()
    private val storageAdapter: SearchPagingAdapter by lazy {
        SearchPagingAdapter().apply {
            addLoadStateListener(
                getPagingListener(
                    failEventFun = {
                        displaySnackBar(
                            getString(
                                R.string.get_fail_list_message,
                                getString(R.string.saved_storage),
                            )
                        )
                    },
                    haveItemSize = {
                        storageAdapter.itemCount > 0
                    },
                    hasDataFun = { isData ->
                        binding.layoutNoData.layoutRoot.visibility =
                            if (isData) {
                                View.GONE
                            } else {
                                View.VISIBLE
                            }
                    },
                    loadingView = binding.layoutLoading.layoutRoot
                )
            )
        }
    }


    private val bottomSheetBinding get() = binding.bottomSheetSearch

    private val searchViewModel: SearchViewModel by viewModels()
    private val searchAdapter: SearchPagingAdapter by lazy {
        SearchPagingAdapter(
            onCheckedEvent = { isChecked, data ->
                if (isChecked) {
                    viewModel.savedStorage(data)
                } else {
                    viewModel.deleteStorage(data)
                }
            }
        ).apply {
            addLoadStateListener(
                getPagingListener(
                    failEventFun = {
                        displaySnackBar(
                            getString(
                                R.string.get_fail_list_message,
                                getString(R.string.search_result)
                            ),
                            view = bottomSheetBinding.root
                        )
                    },
                    haveItemSize = {
                        searchAdapter.itemCount > 0
                    },
                    hasDataFun = { isData ->
                        bottomSheetBinding.layoutNoData.layoutRoot.visibility =
                            if (isData) {
                                if (searchViewModel.getTopState()) {
                                    bottomSheetBinding.rvSearchList.scrollToPosition(0)
                                }

                                View.GONE
                            } else {
                                View.VISIBLE
                            }
                    },
                    loadingView = bottomSheetBinding.layoutLoading.layoutRoot
                )
            )
        }
    }

    override fun setUI() {
        super.setUI()

        binding.layoutNoData.tvCenter.text = getString(
            R.string.no_list_data_message,
            getString(R.string.saved_storage)
        )

        // 바텀 시트 설정
        bottomSheetBinding.apply {

            layoutNoData.tvCenter.apply {
                text = getString(
                    R.string.no_list_data_message,
                    getString(R.string.search_result)
                )
                setTextColor(Color.WHITE)
            }


            BottomSheetBehavior.from(root).apply {
                state = BottomSheetBehavior.STATE_COLLAPSED

                // back pressed 콜백
                val backCallback = object : OnBackPressedCallback(false) {
                    override fun handleOnBackPressed() {
                        etSearch.clearFocus()
                        state = BottomSheetBehavior.STATE_COLLAPSED
                    }
                }
                // back pressed 콜백 등록
                requireActivity().onBackPressedDispatcher
                    .addCallback(viewLifecycleOwner, backCallback)

                addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                    override fun onStateChanged(bottomSheet: View, newState: Int) {
                        backCallback.isEnabled = (newState == BottomSheetBehavior.STATE_EXPANDED)
                    }

                    override fun onSlide(bottomSheet: View, slideOffset: Float) {

                    }
                })

                // 검색 입력 창
                etSearch.setOnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        state = BottomSheetBehavior.STATE_EXPANDED
                    }
                }
            }

            etSearch.doAfterTextChanged { text ->
                // 스크롤 정지
                rvSearchList.stopScroll()
                searchViewModel.search(text.toString())
            }

            // 검색 목록
            rvSearchList.apply {
                adapter = searchAdapter
                layoutManager = StaggeredGridLayoutManager(
                    2, StaggeredGridLayoutManager.VERTICAL
                )
                addItemDecoration(
                    StaggeredGridSpacingItemDecoration(
                        resources.getDimensionPixelSize(R.dimen.padding_xs),
                    )
                )
            }
        }

    }


    override fun setAdapter() {
        super.setAdapter()

        binding.rvStorageList.apply {
            adapter = storageAdapter
            layoutManager = StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL
            )
            addItemDecoration(
                StaggeredGridSpacingItemDecoration(
                    resources.getDimensionPixelSize(R.dimen.padding_xs)
                )
            )
        }
    }

    override fun setObserver() {
        super.setObserver()



        repeatOnStarted(viewLifecycleOwner) {
            viewModel.storagePagingData.collectLatest { pagingData ->
                storageAdapter.submitData(pagingData)
            }
        }


        repeatOnStarted(viewLifecycleOwner) {
            searchViewModel.searchList.collectLatest { pagingData ->
                searchAdapter.submitData(pagingData)
            }
        }


        // 현재는 사용되지 않는 에러 피드백
        repeatOnStarted(viewLifecycleOwner) {
            viewModel.error.collectLatest { error ->
                displaySnackBar(error.second)
            }
        }

    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentStorageBinding {
        return FragmentStorageBinding.inflate(inflater, container, false)
    }
}