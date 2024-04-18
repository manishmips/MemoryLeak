package com.adp.leakcanary.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Issuetype (

  @SerializedName("name" ) var name : String? = "Task"

):Parcelable