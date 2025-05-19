package com.example.sampleapp.presentation.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class StaggeredGridSpacingItemDecoration(
    private val spacingPadding: Int,
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val layoutParams = view.layoutParams as? StaggeredGridLayoutManager.LayoutParams ?: return
        val column = layoutParams.spanIndex

        outRect.left = if (column == 0) 0 else spacingPadding / 2
        outRect.right = if (column == 0) spacingPadding / 2 else 0

        val position = parent.getChildAdapterPosition(view)
        if (position >= 2) {
            outRect.top = spacingPadding
        }
    }
}
