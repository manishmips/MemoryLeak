package com.adp.leakcanary

import android.util.Log
import com.adp.leakcanary.listener.NetworkCallback
import com.adp.leakcanary.model.*
import com.google.gson.Gson
import leakcanary.DefaultOnHeapAnalyzedListener
import leakcanary.OnHeapAnalyzedListener
import org.json.JSONArray
import org.json.JSONObject
import shark.HeapAnalysis
import shark.HeapAnalysisSuccess
import shark.LeakTrace
import java.lang.Exception
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class LeakUploader : OnHeapAnalyzedListener {

    private val defaultListener = DefaultOnHeapAnalyzedListener.create()

    override fun onHeapAnalyzed(heapAnalysis: HeapAnalysis) {
            makePostJiraIssueRequest(heapAnalysis)
        if (defaultListener != null) {
            defaultListener.onHeapAnalyzed(heapAnalysis)
        }
    }

    /**
     * This function is responsible for analyse heap data
     * and create JIRA ticket through Rest api
     */
    private fun makePostJiraIssueRequest(heapAnalysis: HeapAnalysis) {
        if (LeakCanaryUploaderInit.leakCanaryBaseModel==null)
            return
        if (!LeakCanaryUploaderInit.leakCanaryBaseModel?.jiraEnable!!)
            return
        var priorityValue = ""
        var crashType = ""
        var summary = ""
        var type = "doc"
        var version = 1
        var subContentType = "paragraph"
        var internalContentType = "text"



        if (heapAnalysis != null) {
            val heapAnalysisSuccess = heapAnalysis as HeapAnalysisSuccess
            var executor: Executor? = null
            if (heapAnalysisSuccess.applicationLeaks.isNotEmpty() || heapAnalysisSuccess.libraryLeaks.isNotEmpty()) {
                executor = Executors.newFixedThreadPool(heapAnalysisSuccess.applicationLeaks.size + heapAnalysisSuccess.libraryLeaks.size)
            }else{
                Log.e("LeakUploader:","No Leaks")
            }
            if (heapAnalysisSuccess.applicationLeaks.isNotEmpty()) {
                //application leak
                crashType = "app_crash"
                heapAnalysisSuccess.applicationLeaks.forEach {
                    summary=it.shortDescription
                    if (it.leakTraces.isNotEmpty() && it.leakTraces.size > 5) {
                        priorityValue = "High"
                    } else if (it.leakTraces.isNotEmpty() && (it.leakTraces.size in 3..5)) {
                        priorityValue = "Medium"
                    } else if (it.leakTraces.isNotEmpty() && it.leakTraces.size in 1..2) {
                        priorityValue = "Low"
                    }
                    if (makeJiraRequest(
                            crashType,
                            it.leakTraces,
                            priorityValue,
                            internalContentType,
                            subContentType,
                            type,
                            version,
                            summary,
                            executor
                        )
                    ) return
                }
            }

            if (heapAnalysisSuccess.libraryLeaks.isNotEmpty()) {
                crashType = "lib_crash"
                heapAnalysisSuccess.libraryLeaks.forEach {
                    summary=it.shortDescription
                    if (it.leakTraces.isNotEmpty() && it.leakTraces.size > 5) {
                        priorityValue = "High"
                    } else if (it.leakTraces.isNotEmpty() && (it.leakTraces.size in 3..5)) {
                        priorityValue = "Medium"
                    } else if (it.leakTraces.isNotEmpty() && it.leakTraces.size in 1..2) {
                        priorityValue = "Low"
                    }
                    if (makeJiraRequest(
                            crashType,
                            it.leakTraces,
                            priorityValue,
                            internalContentType,
                            subContentType,
                            type,
                            version,
                            summary,
                            executor
                        )
                    ) return
                }

            }
        }
    }

    private fun makeJiraRequest(
        crashType: String,
        leakTraces: List<LeakTrace>,
        priorityValue: String,
        internalContentType: String,
        subContentType: String,
        type: String,
        version: Int,
        summary: String,
        executor: Executor?
    ): Boolean {
        if (LeakCanaryUploaderInit.leakCanaryBaseModel==null)
            return false;
        var labels =ArrayList<String>()
        var text = ""
        val finalJson=JSONObject()

        labels = arrayListOf(LeakCanaryUploaderInit.leakCanaryBaseModel?.brandName.toString(), "android", crashType, "leak")
        leakTraces.forEachIndexed { index, leakTrace ->
            try {
                val  refJson= JSONArray(Gson().toJson(leakTrace.referencePath))
                val leakJson=JSONObject(Gson().toJson(leakTrace.leakingObject))
                finalJson.put("LeakReference-$index",refJson)
                finalJson.put("LeakObject-$index",leakJson)
            }catch (e:Exception){
                text+=Gson().toJson(leakTrace.referencePath)
                text+=Gson().toJson(leakTrace.leakingObject)
            }

        }
        if (finalJson!=null && text.isNullOrEmpty()){
            text=finalJson.toString()
        }

        val issueType = Issuetype(name = LeakCanaryUploaderInit.leakCanaryBaseModel?.issueType)
        val customfield10060 = Customfield10060(priorityValue)
        val internalContent = arrayListOf(InternalContent(internalContentType, text))
        val content = arrayListOf(Content(subContentType, internalContent))
        val description = Description(type, version = version, content)
        val project = Project(LeakCanaryUploaderInit.leakCanaryBaseModel?.jiraProjectKey)
        val parent= Parent(LeakCanaryUploaderInit.leakCanaryBaseModel?.parentKey)
        val field =
            Fields(project,parent, summary, description, customfield10060, issueType, labels = labels)
        val postJiraIssue = PostJiraIssue(field)
        if (executor == null)
            return true
        executor.execute {
            if (postJiraIssue != null) {
                NetworkManager.Instance.networkManagerInstance.postJiraIssue(postJiraIssue)?.enqueue(object : NetworkCallback<Void?>() {
                    override fun onResponse(result: com.adp.leakcanary.model.base.Result<Void?>?) {
                        if (result!=null && result.isSuccessful){
                            Log.e("JIRA ISSUE:","Log success ")
                        }
                    }
                })
            }
        }
        return false
    }
}