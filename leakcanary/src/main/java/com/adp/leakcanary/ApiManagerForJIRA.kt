package com.adp.leakcanary

import android.text.TextUtils
import com.google.gson.GsonBuilder
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * This class is used for setup HttpClient and retrofit
 */
class ApiManagerForJIRA private constructor() {

    private lateinit var credentials: String
    private var apiClient: ApiClientForJira? = null
    private var httpClient: OkHttpClient.Builder
    private lateinit var retrofitBuilder: Retrofit.Builder


    init {
        httpClient = getHttpClient()
        apiClient = getRetrofitService()
        if (LeakCanaryUploaderInit.leakCanaryBaseModel!=null && LeakCanaryUploaderInit.leakCanaryBaseModel?.userName!=null && LeakCanaryUploaderInit.leakCanaryBaseModel?.pass!=null) {
            BasicAuthInterceptor(
                LeakCanaryUploaderInit.leakCanaryBaseModel?.userName!!,
                LeakCanaryUploaderInit.leakCanaryBaseModel?.pass!!
            )
        }
    }

    /**
     * This function is used for getting ApiManager instance
     */
    object Instance {
        private lateinit var instance: ApiManagerForJIRA
        @Synchronized fun getInstance(): ApiManagerForJIRA {
            if (this::instance.isInitialized.not()) {
                instance = ApiManagerForJIRA()
            }
            return instance
        }
    }

    /**
     * This function is used for getting ApiClient instance
     */
    fun getApiClientInstance(): ApiClientForJira? {
        if (apiClient == null) {
            apiClient = getRetrofitService()
        }
        return apiClient;
    }

    /**
     * This function is used for configuring retrofit and return ApiClient
     */
    private fun getRetrofitService(): ApiClientForJira? {
        if (LeakCanaryUploaderInit.leakCanaryBaseModel==null || LeakCanaryUploaderInit.leakCanaryBaseModel?.baseUrl.isNullOrEmpty())
            return null
        retrofitBuilder = Retrofit.Builder().baseUrl(LeakCanaryUploaderInit.leakCanaryBaseModel!!.baseUrl).addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        val retrofit: Retrofit = retrofitBuilder.client(httpClient.build()).build()
        return retrofit.create<ApiClientForJira>(ApiClientForJira::class.java)
    }

    /**
     * This function is used for configuring the HTTPClient
     */
    private fun getHttpClient(): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(getHeadersInterceptor())
        builder.addInterceptor(getLoggingInterceptor())
        builder.readTimeout(30000, TimeUnit.MILLISECONDS)
        builder.writeTimeout(30000, TimeUnit.MILLISECONDS)
        return builder
    }

    /**
     * This function is used for setup Headers for client
     */
    private fun getHeadersInterceptor(): Interceptor {
        return Interceptor { chain1: Interceptor.Chain ->
            val original = chain1.request()
            val requestBuilder: Request.Builder = getRequestBuilderWithRetryCount(original)
            requestBuilder.header("Authorization", credentials).build();
            requestBuilder.header("Content-Type", "application/json")
            val request = requestBuilder.build()
            chain1.proceed(request)
        }
    }

    private fun BasicAuthInterceptor(user: String, password: String) {
        this.credentials = Credentials.basic(user, password)
    }

    private fun getRequestBuilderWithRetryCount(original: Request): Request.Builder {
        var retrycount = 1
        if (!TextUtils.isEmpty(original.header("retrycount"))) {
            retrycount = original.header("retrycount")!!.toInt()
            retrycount++
        }
        return original.newBuilder().method(original.method(), original.body()).header("retrycount", retrycount.toString())
    }

    /**
     * This function is used for configuring Http Interceptor for Logs
     */
    private fun getLoggingInterceptor(): HttpLoggingInterceptor {
        return if (LeakCanaryUploaderInit.leakCanaryBaseModel?.isDebug!!) HttpLoggingInterceptor(CustomHttpLogger()).setLevel(HttpLoggingInterceptor.Level.BODY) else HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
    }
}