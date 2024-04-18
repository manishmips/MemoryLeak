package com.adp.leakcanary.listener

import com.adp.leakcanary.model.base.BaseResponse
import com.adp.leakcanary.model.base.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class NetworkCallback<T> : Callback<BaseResponse<T>?> {
    abstract fun onResponse(result: Result<T>?)
    override fun onResponse(call: Call<BaseResponse<T>?>, response: Response<BaseResponse<T>?>) {
        if (response.isSuccessful && response.body() != null) {
            onResponse(getSuccessResult(response))
        }
    }

    private fun getSuccessResult(response: Response<BaseResponse<T>?>): Result<T> {
        return Result(
            response.body()!!.data,
            response.body()!!.statusCode,
            response.body()!!.message,
            null,
            Result.Status.SUCCESS
        )
    }

    override fun onFailure(call: Call<BaseResponse<T>?>, t: Throwable) {}

    companion object {
        private const val TAG = "NetworkCallback"
    }
}