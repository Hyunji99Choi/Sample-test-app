package com.example.sampleapp.presentation.ui.website

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.sampleapp.databinding.FragmentWebsiteBinding
import com.example.sampleapp.presentation.ui.BaseFragment
import com.example.sampleapp.presentation.util.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class WebsiteFragment : BaseFragment<FragmentWebsiteBinding>() {

    private val viewModel: WebsiteViewModel by viewModels()

    override fun setUI() {
        super.setUI()

        binding.composeView.setContent {
            WebSiteScreen(viewModel)
        }
    }

    override fun setObserver() {
        super.setObserver()

        repeatOnStarted(viewLifecycleOwner) {
            viewModel.error.collectLatest { error ->
                displaySnackBar(error.second)
            }
        }
    }



    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWebsiteBinding {
        return FragmentWebsiteBinding.inflate(inflater, container, false)
    }
}