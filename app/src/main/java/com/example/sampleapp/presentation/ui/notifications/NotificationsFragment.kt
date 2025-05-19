package com.example.sampleapp.presentation.ui.notifications

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.sampleapp.databinding.FragmentNotificationsBinding
import com.example.sampleapp.presentation.ui.BaseFragment
import com.example.sampleapp.presentation.util.repeatOnStarted
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class NotificationsFragment : BaseFragment<FragmentNotificationsBinding>() {

    private val viewModel: NotificationsViewModel by viewModels()

    override fun setUI() {
        super.setUI()

        viewModel.getSearchImages("test")

    }

    override fun setObserver() {
        super.setObserver()

        viewModel.searchResult.observe(viewLifecycleOwner) { result ->
            Snackbar.make(binding.root, result.size.toString(), Snackbar.LENGTH_LONG).show()
        }


        repeatOnStarted(viewLifecycleOwner) {
            viewModel.error.collectLatest { error ->
                displaySnackBar(error.second)
            }
        }

    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNotificationsBinding {
        return FragmentNotificationsBinding.inflate(inflater, container, false)
    }
}