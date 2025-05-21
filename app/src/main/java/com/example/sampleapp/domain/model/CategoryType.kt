package com.example.sampleapp.domain.model

import com.example.sampleapp.R

enum class CategoryType(val stringRes: Int) {
    ALL(R.string.category_all),
    BLOG(R.string.category_blog),
    CAFE(R.string.category_cafe),
    WEB(R.string.category_web)
}
