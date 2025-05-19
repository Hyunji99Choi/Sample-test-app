package com.example.sampleapp.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment<T : ViewBinding> : Fragment() {
    private var _binding: T? = null
    protected val binding: T
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = getViewBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
        setObserver()
        setUI()
    }

    open fun setAdapter() {}
    open fun setUI() {}
    open fun setObserver() {}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun displaySnackBar(
        message: String,
        delay: Int = Snackbar.LENGTH_SHORT,
        view: View = binding.root
    ) {
        Snackbar.make(view, message, delay).show()
    }

    protected abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): T
}
