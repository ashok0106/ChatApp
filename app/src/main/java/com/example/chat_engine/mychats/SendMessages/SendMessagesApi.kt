package com.example.chat_engine.mychats.SendMessages

import android.content.Context
import android.widget.Toast
import com.example.chat_engine.ConstantData.httpclient
import com.example.chat_engine.ViewModel.MainViewModel
import okhttp3.*
import okhttp3.Request.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface SendMessagesApi {
    @POST("messages/")
    fun sendMessage(
        @Body messagesApi:SendMessageDataClass?
    ):Call<SendMessageDataClass?>?
}
class SendMessagesClass(val username: String, val password: String,val chatId:Int){

    val SendMessagesBaseUrl="https://api.chatengine.io/chats/$chatId/"

    fun getInstance(): SendMessagesApi {
        val httpClient = httpclient(username,password)
        val retrofitAPI = Retrofit.Builder()
            .baseUrl(SendMessagesBaseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(SendMessagesApi::class.java)
        return retrofitAPI
    }
}
fun SendMessagesHelpingFunction(
    context: Context,
    viewModel: MainViewModel
)
{
    val myadded= SendMessageDataClass(viewModel.Send_Message)
    val retrofitAPI= viewModel.sendMessages()
    val call: Call<SendMessageDataClass?>? = retrofitAPI.sendMessage(myadded)
    call!!.enqueue(object : Callback<SendMessageDataClass?> {
        override fun onResponse(call: Call<SendMessageDataClass?>?, response: Response<SendMessageDataClass?>) {
            val model: SendMessageDataClass? = response.body()
            val resp =
                "Response Code : " + response.code()
            viewModel.result=resp
        }
        override fun onFailure(call: Call<SendMessageDataClass?>?, t: Throwable) {
            var temp = "Error found is : " + t.message
            Toast.makeText(context,temp, Toast.LENGTH_SHORT).show()
        }
    })
}

