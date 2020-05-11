package com.deuron.teacoffeebackend.api
//package com.authnex.android.sdk.authnexpure.utils;

import android.content.SharedPreferences
import com.deuron.teacoffeebackend.GlobalVariables
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RestAPIClient {
    private var sharedPref: SharedPreferences? = null
    var retrofit: Retrofit? = null
     var serverIp: String? = null
     var serverURL: String? = null
     var apiBase: String? = null
    var loggingInterceptor = HttpLoggingInterceptor()

    val client: Retrofit?
        get() {
            retrofit
//            serverIp = sharedPref!!.getString("ServerIP", "0.0.0.0")
//            serverURL = "http://$serverIp:/deuron_automation_api_cloud/"
//            apiBase = serverURL + "v1.0/api/"
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(
                        BasicAuthInterceptor(
                            "api_user",
                            "api_user123"
                        )
                    )
                    .build()
            if (retrofit == null) {
                val gson = GsonBuilder().setLenient()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                        .create()
                retrofit = Retrofit.Builder()
                        .baseUrl(GlobalVariables.ApiBaseUrl)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(okHttpClient)
                        .build()
            }
            return retrofit
        }

    val demoClient: Retrofit?
        get() {
//            val ip = sharedPref!!.getString("ServerIP", "0.0.0.0")
//            serverURL = "http://$ip:/deuron_automation_api_cloud/v1.0/api/"
//            //apiBase = serverURL + "v1.0/api/"
            retrofit
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .addInterceptor(
                        BasicAuthInterceptor(
                            "api_user",
                            "api_user123"
                        )
                    )
                    .build()
            if (retrofit == null) {
                val gson = GsonBuilder().setLenient()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                        .create()
                retrofit = Retrofit.Builder()
                        .baseUrl(GlobalVariables.ApiBaseUrl)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(okHttpClient)
                        .build()
            }
            return retrofit
        }
}
