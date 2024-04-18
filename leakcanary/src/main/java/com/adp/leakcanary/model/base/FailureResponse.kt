package com.adp.leakcanary.model.base

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FailureResponse (val code: Int = 0,
                            val message: String? = null,
                            val status: Result.Status? = null):Parcelable{

}