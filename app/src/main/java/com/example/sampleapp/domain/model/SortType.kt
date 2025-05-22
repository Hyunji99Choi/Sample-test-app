package com.example.sampleapp.domain.model

import com.example.sampleapp.R

enum class SortType(val stringRes: Int, val label: String) {
    ACCURACY(R.string.sort_accuracy, "accuracy"), RECENT(R.string.sort_recency, "recency")
}
