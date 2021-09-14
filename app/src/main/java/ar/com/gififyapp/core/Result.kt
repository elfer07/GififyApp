package ar.com.gififyapp.core

import java.lang.Exception

/**
 * Created by Fernando Moreno on 12/9/2021.
 */
sealed class Result<out T> {
    class Loading<out T>: Result<T>()
    data class  Success<out T>(val data: T): Result<T>()
    data class  Failure(val exception: Exception): Result<Nothing>()
}