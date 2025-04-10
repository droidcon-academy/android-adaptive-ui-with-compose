package com.droidcon.adaptiveinbox.model

sealed class DataState<T> {
    class Loading<Any>: DataState<Any>()
    data class Success<T>(val data: T) : DataState<T>()
    data class Error<Any>(val message: String) : DataState<Any>()
}