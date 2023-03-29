package com.example.chat_engine.mychats.GetMessages

import android.content.Context
import android.widget.Toast
import com.example.chat_engine.ConstantData.httpclient
import com.example.chat_engine.ViewModel.MainViewModel
import com.example.chat_engine.login.loginService
import com.example.chat_engine.mychats.GetMessages.GetMessagesDataClass.MessagesDataClass
import com.example.chat_engine.mychats.GetMessages.GetMessagesDataClass.MessagesDataClassItem
import com.example.chat_engine.signup.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ChatMessagesApi{
    @GET("messages/")
    fun getMessages():Call<MessagesDataClass?>?
}

class ChatMessagesClass(val username:String,val password:String,val chatId:Int){
    val messages_base_url="https://api.chatengine.io/chats/$chatId/"

    fun getInstance(): ChatMessagesApi {
        val httpClient = httpclient(username,password)
        val retrofitAPI = Retrofit.Builder()
            .baseUrl(messages_base_url)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ChatMessagesApi::class.java)
        return retrofitAPI
    }
}
fun getMessages(
    context: Context,
    vm: MainViewModel,
    chatId:Int,
//    onClickGotoMessages: () -> Unit,
){

    val retrofitApi=vm.GetMessages(chatId)
    val call: Call<MessagesDataClass?>? = retrofitApi.getMessages()
    call!!.enqueue(object : Callback<MessagesDataClass?> {
        override fun onResponse(call: Call<MessagesDataClass?>?, response: Response<MessagesDataClass?>) {
            vm.open.value=false
//            Toast.makeText(context, "Data posted to API", Toast.LENGTH_SHORT).show()
            val model: MessagesDataClass? = response.body()
            val resp =
                "Response Code : " + response.code() + "\n" + "id " + model?.size + "\n" + "Title : " + model?.size
            println("////////////////////////////////////////////// Response Code:${response.code()}")
            vm.result = resp
            if (model != null) {
                vm.ChatList=model
//                onClickGotoMessages()
                Toast.makeText(context,"Room Chat is ${vm.chatId}", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show()
            }
        }
        override fun onFailure(call: Call<MessagesDataClass?>?, t: Throwable) {
            var temp = "Error found is : " + t.message
            vm.result=temp
            Toast.makeText(context,temp, Toast.LENGTH_SHORT).show()

        }
    })
}