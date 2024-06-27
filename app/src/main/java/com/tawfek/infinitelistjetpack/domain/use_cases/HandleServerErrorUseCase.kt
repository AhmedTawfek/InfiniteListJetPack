package com.tawfek.infinitelistjetpack.domain.use_cases


class HandleServerErrorUseCase() {
    fun getErrorType(errorCode: Int) : ErrorType{
        return when(errorCode){
            400 -> ErrorType.BadRequest
            401 -> ErrorType.Unauthorized
            429 -> ErrorType.TooManyRequests
            in 500..599 -> ErrorType.InternalServerError
            else -> ErrorType.UnknownError
        }
    }
}

sealed class ErrorType {
    object BadRequest : ErrorType()
    object Unauthorized : ErrorType()
    object InternalServerError : ErrorType()
    object TooManyRequests : ErrorType()
    object ServiceUnavailable : ErrorType()
    object NetworkNotAvailable : ErrorType()
    object UnknownError : ErrorType()
}