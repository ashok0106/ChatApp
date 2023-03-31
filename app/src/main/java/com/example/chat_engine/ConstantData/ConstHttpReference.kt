package com.example.chat_engine.ConstantData

import com.example.chat_engine.mychats.MakeChatService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/*
This is one time code to call the http client with the
required headers
 */
fun httpclient(username:String,password:String):OkHttpClient{

    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor { chain ->
            val originalRequest = chain.request().newBuilder()
                .addHeader("Project-ID",ConstVariables().projectid)
                .addHeader("User-Name",username)
                .addHeader("User-Secret",password)
                .build()
            chain.proceed(originalRequest)
        }
        .build()
    return httpClient
}

/*
Retrofit instance to get the response from the respective API
 */
fun retrofitApi(httpClient: OkHttpClient,base_url:String): Retrofit{
    val retrofitAPI = Retrofit.Builder()
        .baseUrl(base_url)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofitAPI
}