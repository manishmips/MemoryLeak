package com.adp.leakcanary

import com.adp.leakcanary.model.LeakCanaryBaseModel
import leakcanary.LeakCanary

object LeakCanaryUploaderInit {
    var leakCanaryBaseModel: LeakCanaryBaseModel? =null

    fun initLeakCanary(leakCanaryBaseModel: LeakCanaryBaseModel){
        this.leakCanaryBaseModel=leakCanaryBaseModel
        LeakCanary.config = LeakCanary.config.copy(onHeapAnalyzedListener = LeakUploader(),dumpHeapWhenDebugging=true,dumpHeap = leakCanaryBaseModel.enableAutoDump)
    }
}