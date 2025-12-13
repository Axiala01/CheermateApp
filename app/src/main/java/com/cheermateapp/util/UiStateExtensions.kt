package com.cheermateapp.util

import android.app.Activity
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.cheermateapp.data.repository.UiState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun Activity.showSuccess(message: String, duration: Int = Toast.LENGTH_SHORT) {
    ToastManager.showCustomToast(this, message, duration)
}

fun Activity.showError(message: String, duration: Int = Toast.LENGTH_LONG) {
    ToastManager.showCustomToast(this, message, duration)
}

fun Activity.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    ToastManager.showCustomToast(this, message, duration)
}

fun <T> AppCompatActivity.observeUiState(
    stateFlow: StateFlow<UiState<T>>,
    progressBar: ProgressBar? = null,
    onSuccess: (T) -> Unit,
    onError: (String) -> Unit
) {
    lifecycleScope.launch {
        stateFlow.collect { state ->
            when (state) {
                is UiState.Loading -> progressBar?.visibility = View.VISIBLE
                is UiState.Success -> {
                    progressBar?.visibility = View.GONE
                    onSuccess(state.data)
                }
                is UiState.Error -> {
                    progressBar?.visibility = View.GONE
                    onError(state.message)
                }
                is UiState.Idle -> {
                    // Do nothing
                }
            }
        }
    }
}

fun AppCompatActivity.observeOperationState(
    stateFlow: StateFlow<UiState<String>>,
    progressBar: ProgressBar? = null,
    onSuccess: ((String) -> Unit)? = null
) {
    observeUiState(
        stateFlow = stateFlow,
        progressBar = progressBar,
        onSuccess = { message ->
            showSuccess(message)
            onSuccess?.invoke(message)
        },
        onError = { message ->
            showError(message)
        }
    )
}

class MultiStateObserver(private val activity: AppCompatActivity) {
    private var loadingCount = 0
    private var progressBar: ProgressBar? = null

    fun setProgressBar(progressBar: ProgressBar) {
        this.progressBar = progressBar
    }

    fun <T> observe(
        stateFlow: StateFlow<UiState<T>>,
        onSuccess: (T) -> Unit,
        onError: ((String) -> Unit)? = null
    ) {
        activity.lifecycleScope.launch {
            stateFlow.collect { state ->
                when (state) {
                    is UiState.Idle -> {
                        // Do nothing
                    }
                    is UiState.Loading -> {
                        loadingCount++
                        updateProgressBar()
                    }
                    is UiState.Success -> {
                        loadingCount--
                        updateProgressBar()
                        onSuccess(state.data)
                    }
                    is UiState.Error -> {
                        loadingCount--
                        updateProgressBar()

                        if (onError != null) {
                            onError(state.message)
                        } else {
                            activity.showError(state.message)
                        }
                    }
                }
            }
        }
    }

    private fun updateProgressBar() {
        progressBar?.visibility = if (loadingCount > 0) View.VISIBLE else View.GONE
    }
}

fun <T> StateFlow<UiState<T>>.isLoading(): Boolean {
    return value is UiState.Loading
}

fun <T> StateFlow<UiState<T>>.hasData(): Boolean {
    return value is UiState.Success
}

fun <T> StateFlow<UiState<T>>.getData(): T? {
    return (value as? UiState.Success)?.data
}