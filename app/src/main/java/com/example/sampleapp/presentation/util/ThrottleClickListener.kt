package com.example.sampleapp.presentation.util

import android.view.View

class ThrottleClickListener(
    private val interval: Long = 300L,
    private val onClick: (View) -> Unit
) : View.OnClickListener {

    private var canClick = true

    override fun onClick(v: View) {
        if (canClick) {
            canClick = false

            onClick.invoke(v)
            v.postDelayed({ canClick = true }, interval)
        }
    }
}

fun View.setThrottleClickListener(interval: Long = 300L, onClick: (View) -> Unit) {
    val throttleClickListener = ThrottleClickListener(interval, onClick)
    setOnClickListener(throttleClickListener)
}