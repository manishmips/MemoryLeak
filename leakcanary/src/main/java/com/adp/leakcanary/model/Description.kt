package com.adp.leakcanary.model

import android.os.Parcelable
import com.adp.leakcanary.model.Content
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Description(

    @SerializedName("type") var type: String? = "doc",
    @SerializedName("version") var version: Int? = 1,
    @SerializedName("content") var content: ArrayList<Content> = arrayListOf()

):Parcelable