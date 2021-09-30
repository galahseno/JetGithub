package dev.seno.jetgithub.utils

sealed class Response<out R> {
    data class Success<out T>(val data: T) : Response<T>()
    data class Error(val errorMessage: String) : Response<Nothing>()
}
