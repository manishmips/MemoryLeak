package com.adp.leakcanary.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Project (

  @SerializedName("key" ) var key : String? = null

):Parcelable