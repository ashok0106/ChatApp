package com.example.chat_engine.IsTyping

import android.content.Context
import android.widget.Toast
import com.example.chat_engine.ConstantData.httpclient
import com.example.chat_engine.ViewModel.MainViewModel
import com.example.chat_engine.mychats.SendMessages.SendMessageDataClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface IstypingApi {
    @POST("typing/")
    fun Istyping(
    ): Call<IsTypingDataClass?>?
}
class IsTypingClass(val username: String, val password: String,val chatId:Int){

    val IsTypingBaseUrl="https://api.chatengine.io/chats/$chatId/"

    fun getInstance(): IstypingApi {
        val httpClient = httpclient(username,password)
        val retrofitAPI = Retrofit.Builder()
            .baseUrl(IsTypingBaseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(IstypingApi::class.java)
        return retrofitAPI
    }
}

fun IsTypingHelpingFunction(
    context: Context,
    viewModel: MainViewModel
)
{
    val retrofitAPI= viewModel.IsUserTyping()
    val call: Call<IsTypingDataClass?>? = retrofitAPI.Istyping()
    call!!.enqueue(object : Callback<IsTypingDataClass?> {
        override fun onResponse(call: Call<IsTypingDataClass?>?, response: Response<IsTypingDataClass?>) {
            val model: IsTypingDataClass? = response.body()
            val resp =
                "Response Code : " + response.code()
            viewModel.result=resp
        }
        override fun onFailure(call: Call<IsTypingDataClass?>?, t: Throwable) {
            var temp = "Error found is : " + t.message
            Toast.makeText(context,temp, Toast.LENGTH_SHORT).show()
        }
    })
}

