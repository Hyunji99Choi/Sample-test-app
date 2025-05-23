package com.example.sampleapp.presentation.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class StaggeredGridSpacingItemDecoration(
    private val spacingPadding: Int,
    private val spanCount: Int = 2,
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION) return

        val layoutParams = view.layoutParams

        // 현재 몇번째 item 인지
        val column = when (layoutParams) {
            is GridLayoutManager.LayoutParams -> position % spanCount
            is StaggeredGridLayoutManager.LayoutParams -> layoutParams.spanIndex
            else -> return
        }

        outRect.left = spacingPadding * column / spanCount
        outRect.right = spacingPadding * (spanCount - 1 - column) / spanCount

        if (position >= spanCount) {
            outRect.top = spacingPadding
        }
    }
}
