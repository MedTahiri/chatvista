package com.mohamed.tahiri.android.viewmodel

sealed class ApiState<out T> {
    object Loading : ApiState<Nothing>()
    data class Success<out T>(val data: T) : ApiState<T>()
    data class Error(val message: String) : ApiState<Nothing>()
}