package com.example.sampleapp.presentation.ui.website

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.sampleapp.databinding.FragmentWebsiteBinding
import com.example.sampleapp.presentation.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebsiteFragment : BaseFragment<FragmentWebsiteBinding>() {


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWebsiteBinding {
        return FragmentWebsiteBinding.inflate(inflater, container, false)
    }
}