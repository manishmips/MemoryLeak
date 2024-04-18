package com.adp.leakcanary.model

import android.os.Parcelable
import com.adp.leakcanary.model.Fields
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PostJiraIssue (

  @SerializedName("fields" ) var fields : Fields? = Fields()

):Parcelable