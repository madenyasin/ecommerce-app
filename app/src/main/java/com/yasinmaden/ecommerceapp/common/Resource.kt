package com.yasinmaden.ecommerceapp.common

sealed class Resource<out T>{
    data class Success<out T>(val data: T): Resource<T>()
    data class Error(val exception: Exception) : Resource<Nothing>()
}