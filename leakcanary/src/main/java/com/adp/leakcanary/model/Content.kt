package com.adp.leakcanary.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Content (

  @SerializedName("type"    ) var type    : String? = "paragraph",
  @SerializedName("content" ) var content : ArrayList<InternalContent> = arrayListOf()

):Parcelable
@Parcelize
data class InternalContent(@SerializedName("type"    ) var type    : String = "text",
                           @SerializedName("text" ) var text : String ="This is generated content"):Parcelable