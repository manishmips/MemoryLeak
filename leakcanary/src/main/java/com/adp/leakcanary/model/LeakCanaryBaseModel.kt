package com.adp.leakcanary.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LeakCanaryBaseModel(
    val baseUrl: String,
    val userName: String,
    val pass: String,
    val jiraProjectKey: String,
    val parentKey: String,
    val isDebug: Boolean,
    val brandName: String,
    val jiraEnable: Boolean,
    val enableAutoDump: Boolean,
    val issueType: String
) : Parcelable
