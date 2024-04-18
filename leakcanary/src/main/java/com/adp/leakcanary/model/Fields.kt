package com.adp.leakcanary.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Fields(

    @SerializedName("project") var project: Project? = Project(),
    @SerializedName("parent") var parent: Parent? = Parent(),
    @SerializedName("summary") var summary: String? = null,
    @SerializedName("description") var description: Description? = Description(),
    @SerializedName("customfield_10060") var customfield10060: Customfield10060? = Customfield10060(),
    @SerializedName("issuetype") var issuetype: Issuetype? = Issuetype(),
    @SerializedName("labels") var labels: ArrayList<String> = arrayListOf(),
):Parcelable