package com.adp.leakcanary

import com.adp.leakcanary.model.PostJiraIssue
import com.adp.leakcanary.model.base.BaseResponse
import retrofit2.Call

/**
 * This class is used for calling API's
 */
class NetworkManager private constructor() {

    var apiClient: ApiClientForJira? = null

    /**
     * init block initilaize the api client
     */
    init {
        apiClient = ApiManagerForJIRA.Instance.getInstance().getApiClientInstance();
    }

    /**
     * This function is used to return instance of Network Manager
     */
    object Instance {
        private lateinit var instance: NetworkManager
        val networkManagerInstance: NetworkManager
            get() {
                if (this::instance.isInitialized.not()) {
                    instance = NetworkManager()
                }
                return instance
            }
    }

    /**
     * This function is used to call post jira ticket rest api
     */
    fun postJiraIssue(postJiraIssue: PostJiraIssue): Call<BaseResponse<Void?>?>? {
        return apiClient?.createJiraTicket(postJiraIssue = postJiraIssue)
    }

}