package com.example.chat_engine.signup

import android.content.Context
import android.widget.Toast
import com.example.chat_engine.ViewModel.MainViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

const val BASE_URL="https://api.chatengine.io/"

const val header_name= "PRIVATE-KEY"
const val header_link="dabd1050-947a-4510-bdff-9af7266cb620"


interface ApiService {
    @POST("users/")
    fun addUser(
        @Body myadded: SignUpDataClass?,
    ): Call<SignUpDataClass?>?
}

fun SignUpHelper(
    context: Context,
    vm: MainViewModel,
    firstname:String,
    lastname:String
){
    val myadded= SignUpDataClass(vm.user_name,firstname,lastname,vm.password)

    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

    val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor { chain ->
            val originalRequest = chain.request().newBuilder()
                .addHeader(header_name, header_link)
                .build()
            chain.proceed(originalRequest)
        }
        .build()

    val retrofitAPI = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(ApiService::class.java)

    val call: Call<SignUpDataClass?>? = retrofitAPI.addUser(myadded)
    call!!.enqueue(object : Callback<SignUpDataClass?> {
        override fun onResponse(call: Call<SignUpDataClass?>?, response: Response<SignUpDataClass?>) {
            Toast.makeText(context, "Sign Up Successful", Toast.LENGTH_SHORT).show()
            val model: SignUpDataClass? = response.body()
            val resp =
                "Response Code : " + response.code() + "\n" + "Id: " + model?.last_name + "\n" + "Title : " + model?.first_name
            vm.result = resp
        }

        override fun onFailure(call: Call<SignUpDataClass?>?, t: Throwable) {
            vm.result = "Error found is : " + t.message
        }

    })
}