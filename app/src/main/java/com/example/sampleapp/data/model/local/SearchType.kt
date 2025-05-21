package com.example.sampleapp.data.model.local

import com.example.sampleapp.domain.model.CategoryType

enum class SearchType(val match: CategoryType) {
    BLOG(CategoryType.BLOG),
    CAFE(CategoryType.CAFE),
    WEB(CategoryType.WEB);

    companion object {
        fun from(category: CategoryType): SearchType? {
            return entries.find { it.match == category }
        }
    }
}
