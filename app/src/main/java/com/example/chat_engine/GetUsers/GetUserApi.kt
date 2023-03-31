package com.example.chat_engine.GetUsers

import android.content.Context
import android.widget.Toast
import com.example.chat_engine.ConstantData.ConstVariables
import com.example.chat_engine.ConstantData.httpclient
import com.example.chat_engine.ConstantData.retrofitApi
import com.example.chat_engine.GetChats.GetChatsDataClass
import com.example.chat_engine.ViewModel.MainViewModel
import com.example.chat_engine.signup.header_link
import com.example.chat_engine.signup.header_name
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val GetUsersUrl="https://api.chatengine.io/"

/*
API TO GET THE LIST OF ALL USERS PRESENT IN THE PROJECT
 */
interface GetUserApi{
    @GET("users/")
    fun getUsers(): Call<List<GetUsersDataClass>?>?
}

class GetUserClass(val username:String,val password:String){

    fun getInstance(): GetUserApi {
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
        val retrofitAPI = retrofitApi(httpClient, GetUsersUrl).create(GetUserApi::class.java)
        return retrofitAPI
    }
}
fun GetUsers(
    context: Context,
    vm: MainViewModel,
){

    val retrofitApi=vm.GetUsers()
    val call: Call<List<GetUsersDataClass>?>? = retrofitApi.getUsers()
    call!!.enqueue(object : Callback<List<GetUsersDataClass>?> {
        override fun onResponse(call: Call<List<GetUsersDataClass>?>?, response: Response<List<GetUsersDataClass>?>) {
//            Toast.makeText(context, "Data posted to API", Toast.LENGTH_SHORT).show()
            val model: List<GetUsersDataClass>? = response.body()
            val resp =
                "Response Code : " + response.code() + "\n" + "id " + model?.size + "\n" + "Title : " + model?.size
            println("////////////////////////////////////////////// Response Code:${response.code()}")
            vm.result = resp
            if (model != null) {
                vm.UsersList= model as MutableList<GetUsersDataClass>
//                onClickGotoMessages()
            }
        }
        override fun onFailure(call: Call<List<GetUsersDataClass>?>?, t: Throwable) {
            var temp = "Error found is : " + t.message
            vm.result=temp
            Toast.makeText(context,temp, Toast.LENGTH_SHORT).show()

        }
    })
}