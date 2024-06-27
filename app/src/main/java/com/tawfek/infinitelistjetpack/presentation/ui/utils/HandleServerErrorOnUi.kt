package com.tawfek.infinitelistjetpack.presentation.ui.utils

import android.content.Context
import com.tawfek.infinitelistjetpack.R
import com.tawfek.infinitelistjetpack.domain.use_cases.ErrorType


fun ErrorType.convertErrorTypeToMessage(context : Context) : String{
    return when (this){
        ErrorType.BadRequest -> context.resources.getString(R.string.BAD_REQUEST)
        ErrorType.Unauthorized -> context.resources.getString(R.string.UNAUTHORIZED)
        ErrorType.InternalServerError -> context.resources.getString(R.string.SERVER_NOT_AVAILABLE)
        ErrorType.TooManyRequests -> context.resources.getString(R.string.TOO_MANY_REQUESTS)
        ErrorType.ServiceUnavailable -> context.resources.getString(R.string.SERVER_NOT_AVAILABLE)
        ErrorType.NetworkNotAvailable -> context.resources.getString(R.string.INTERNET_NOT_AVAILABLE)
        ErrorType.UnknownError -> context.resources.getString(R.string.UNKNOWN_EXCEPTION)
    }
}