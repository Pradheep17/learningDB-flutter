package com.pradheep.roomdb.network.api_resource

import android.content.Context

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val context: Context? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(
        message: String,
        data: T? = null,
        statusCode: Int? = null,
        context: Context? = null
    ) : Resource<T>(data, message, context) {
        init {
            when (statusCode) {
                400, 401, 403, 404 -> {
//                 IntentUtils.redirectToNextActivity<SignInActivity>(context!!,null,true)
                }
            }
        }
    }

    class Loading<T> : Resource<T>()
}

