package com.example.sampleapp.presentation.util

import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @param interval Debounce 간격
 * @param coroutineScope Debounce를 동작할 코루틴
 * @param onForceClick Debounce와 관련없이 실시간으로 동작하는 로직. ex) check 여부, toggle 여부 등
 * @param onClick Debounce onClick event
 */
class DebounceClickListener(
    private val interval: Long = 250L, // 기본적으로 250ms
    private val coroutineScope: CoroutineScope,
    private val onForceClick: ((View) -> Unit)? = null, // debounce와 관련없이 실시간으로 동작해야하는 코드
    private val onClick: (View) -> Unit
) : View.OnClickListener {

    private var job: Job? = null

    override fun onClick(v: View) {
        onForceClick?.invoke(v)

        job?.cancel()
        job = coroutineScope.launch {
            delay(interval)
            withContext(Dispatchers.Main) {
                try {
                    onClick.invoke(v)
                } catch (e: Exception) {

                }
            }
        }
    }
}

fun View.setDebounceClickListener(
    interval: Long = 250L,
    coroutineScope: CoroutineScope,
    onForceClick: ((View) -> Unit)? = null,
    onClick: (View) -> Unit
) {
    val debounceClickListener = DebounceClickListener(
        interval, coroutineScope, onForceClick, onClick
    )
    setOnClickListener(debounceClickListener)
}
