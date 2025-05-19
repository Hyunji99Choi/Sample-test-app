package com.example.sampleapp.presentation.util

import android.view.View
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState

/**
 * 페이징 어뎁터 리스너
 *
 * @param failEventFun 페이지 목록 로딩 실패 시
 *
 * @param haveItemSize 목록 데이터가 있는지 판단하는 람다 함수
 * @param hasDataFun 데이터가 있을때/없을 때 처리 로직
 *
 * @param loadingView 로딩 화면 view
 */

fun getPagingListener(
    failEventFun: (() -> Unit)? = null,

    haveItemSize: (() -> Boolean)? = null,
    hasDataFun: ((Boolean) -> Unit)? = null,

    loadingView: View? = null,
): (CombinedLoadStates) -> Unit = {
    when (it.source.refresh) {
        is LoadState.Loading -> {
            // 로딩 중 표시
            loadingView?.visibility = View.VISIBLE
        }

        is LoadState.Error -> {
            loadingView?.visibility = View.GONE
            failEventFun?.invoke()
        }

        is LoadState.NotLoading -> {
            loadingView?.visibility = View.GONE

            if (it.source.append.endOfPaginationReached &&
                (haveItemSize?.invoke()?.not() == true)
            ) {
                // 데이터가 없을 때
                hasDataFun?.invoke(false)
            } else {
                // 데이터가 있을 때
                hasDataFun?.invoke(true)
            }
        }
    }
}