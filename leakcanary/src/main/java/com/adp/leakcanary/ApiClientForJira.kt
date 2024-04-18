package com.adp.leakcanary

import com.adp.leakcanary.model.PostJiraIssue
import com.adp.leakcanary.model.base.BaseResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiClientForJira {
    @POST("rest/api/3/issue")
//    @POST("rest/api/3/epic/{epic_key}/issue")
    fun createJiraTicket(@Body postJiraIssue: PostJiraIssue): Call<BaseResponse<Void?>?>?
//    fun createJiraTicket(@Path(value = "epic_key", encoded = true) epic_key:String, @Body postJiraIssue: PostJiraIssue): Call<BaseResponse<Void?>?>?

    @GET("/rest/api/3/issue/:issueIdOrKey?fields=*all&fieldsByKeys=false")
    fun getJiraTicket(): Call<BaseResponse<Void?>?>?

    @PUT("/rest/api/3/issue/:issueIdOrKey")
    fun updateJiraTicket(): Call<BaseResponse<Void?>?>?
}