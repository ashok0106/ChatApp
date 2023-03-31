package com.example.chat_engine.GetChatMembers

import android.content.Context
import android.widget.Toast
import com.example.chat_engine.ConstantData.httpclient
import com.example.chat_engine.ConstantData.retrofitApi
import com.example.chat_engine.GetChatMembers.dataclasses.ChatMembersDataClass
import com.example.chat_engine.ViewModel.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET

interface GetChatMembersApi{
    @GET("people/")
    fun getChatMembers(): Call<List<ChatMembersDataClass>?>?
}

class GetChatMembersClass(val username:String,val password:String,val chatid:Int){

    val ChatMembersUrl="https://api.chatengine.io/chats/$chatid/"

    fun getInstance(): GetChatMembersApi {
        val httpClient = httpclient(username,password)
        val retrofitAPI = retrofitApi(httpClient, ChatMembersUrl).create(GetChatMembersApi::class.java)
        return retrofitAPI
    }
}
fun GetChatMembers(
    context: Context,
    vm: MainViewModel,
){

    val retrofitApi=vm.GetChatMembers()
    val call: Call<List<ChatMembersDataClass>?>? = retrofitApi.getChatMembers()
    call!!.enqueue(object : Callback<List<ChatMembersDataClass>?> {
        override fun onResponse(call: Call<List<ChatMembersDataClass>?>?, response: Response<List<ChatMembersDataClass>?>) {
//            Toast.makeText(context, "Data posted to API", Toast.LENGTH_SHORT).show()
            val model: List<ChatMembersDataClass>? = response.body()
            val resp =
                "Response Code : " + response.code() + "\n" + "id " + model?.size + "\n" + "Title : " + model?.size
            println("////////////////////////////////////////////// Response Code:${response.code()}")
            vm.result = resp
            if (model != null) {
                vm.ChatMembersList= model as MutableList<ChatMembersDataClass>
                println("////////////////////////////////////////////// Response Code:${model}")

//                onClickGotoMessages()
            }
        }
        override fun onFailure(call: Call<List<ChatMembersDataClass>?>?, t: Throwable) {
            var temp = "Error found is : " + t.message
            vm.result=temp
            Toast.makeText(context,temp, Toast.LENGTH_SHORT).show()

        }
    })
}