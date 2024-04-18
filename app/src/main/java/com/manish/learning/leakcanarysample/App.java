package com.manish.learning.leakcanarysample;

import android.app.Application;

import com.adp.leakcanary.ApiConstantsForJIRA;
import com.adp.leakcanary.LeakCanaryUploaderInit;
import com.adp.leakcanary.model.LeakCanaryBaseModel;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    initLeakCanary();
    }
    private void initLeakCanary() {
//        if (BuildConfig.DEBUG) {
            String task="Task";
            String subTask= "Sub-task";
            String baseUrl="https://americana-food.atlassian.net";
            String userName = "";// your email id using on JIRA
            String pass = "Your Jira app password";//do not push it on server
            String jiraProject = "ADP";
            String parentKey = "ADP-1063";
            boolean isDebug=true;
            String brandName= ApiConstantsForJIRA.Brand.KFC.name();
            boolean enableJira=false;
            boolean enableAutoDump=true;
            LeakCanaryBaseModel leakCanaryBaseModel=new LeakCanaryBaseModel(baseUrl,
                    userName,
                    pass,
                    jiraProject,
                    parentKey,
                    isDebug,
                    brandName,
                    enableJira,
                    enableAutoDump,
                    subTask);
            LeakCanaryUploaderInit.INSTANCE.initLeakCanary(leakCanaryBaseModel);
        }
//    }

}
