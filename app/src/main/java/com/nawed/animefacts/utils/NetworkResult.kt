package com.nawed.animefacts.utils

sealed class NetworkResult<T>(val data: T? = null, val message: String? = null){
    // define three class for loading, success and failure state
    // when you hit api calls use this generic class

    class Success<T>(data: T?): NetworkResult<T>(data)
    class Error<T>(message: String?, data: T? = null): NetworkResult<T>(data, message)
    class Loading<T>: NetworkResult<T>()

}