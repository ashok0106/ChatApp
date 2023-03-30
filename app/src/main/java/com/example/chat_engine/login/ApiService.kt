package com.example.chat_engine.login

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import com.example.chat_engine.ConstantData.ConstVariables
import com.example.chat_engine.ConstantData.httpclient
import com.example.chat_engine.GetChats.GetChatDetails
import com.example.chat_engine.ViewModel.MainViewModel
import com.example.chat_engine.signup.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface loginService{
    @GET("me/")
    fun getUser():Call<LoginDataClass?>?
}

class LoginClass(val username:String,val password:String){

    fun getInstance():loginService{
        val httpClient = httpclient(username,password)
        val retrofitAPI = Retrofit.Builder()
            .baseUrl(ConstVariables().Login_base_url)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(loginService::class.java)
        return retrofitAPI
    }
}

fun second1(
    context: Context,
    username: String,
    password: String,
    onClickGotoMainScreen: () -> Unit,
    viewModel: MainViewModel,
    sharedPreferences: SharedPreferences
){
    val editor: SharedPreferences.Editor = sharedPreferences.edit()
    val retrofitAPI=viewModel.AuthenticateUser()
    val call: Call<LoginDataClass?>? = retrofitAPI.getUser()
    call!!.enqueue(object : Callback<LoginDataClass?> {
        override fun onResponse(call: Call<LoginDataClass?>, response: Response<LoginDataClass?>) {
            viewModel.open.value = false
            val model: LoginDataClass? = response.body()
            val resp =
                "Response Code : " + response.code() + "\n" + "Id: " + model?.is_authenticated + "\n" + "Title : " + model?.first_name
            viewModel.result = resp
            viewModel.UserData = model
            if (model?.is_authenticated == true) {
                onClickGotoMainScreen()
                Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.open.value = false
                Toast.makeText(context, "Wrong Credentials", Toast.LENGTH_SHORT).show()
            }

            if(response.isSuccessful){
                GetChatDetails(context,viewModel)
                editor.putString("USERNAME", username)
                editor.putString("SECRET", password)
                editor.apply()
            }

        }
        override fun onFailure(call: Call<LoginDataClass?>, t: Throwable) {
            viewModel.result = "Error found is : " + t.message
        }
    })
}