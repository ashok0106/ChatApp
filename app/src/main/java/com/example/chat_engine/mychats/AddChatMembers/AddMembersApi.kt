package com.example.chat_engine.mychats.AddChatMembers

import android.content.Context
import android.widget.Toast
import com.example.chat_engine.ConstantData.ConstVariables
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

interface AddMembersApi{
    @POST("people/")
    fun AddMember(
        @Body myadded: AddMembersDataClass?
    ): Call<AddMembersDataClass?>?
}

class AddMemberClass(val username:String,val password:String,val chatid:Int){

    val url="https://api.chatengine.io/chats/$chatid/"
    fun getInstance(): AddMembersApi {
        val httpClient = httpclient(username,password)
        val retrofitAPI = Retrofit.Builder()
            .baseUrl(url)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(AddMembersApi::class.java)
        return retrofitAPI
    }
}

fun AddMembersInChat(
    context: Context,
    viewModel: MainViewModel
)
{
    val myadded= AddMembersDataClass(viewModel.MemberName)
    val retrofitAPI= viewModel.AddMemberInChat()
    val call: Call<AddMembersDataClass?>? = retrofitAPI.AddMember(myadded)
    call!!.enqueue(object : Callback<AddMembersDataClass?> {
        override fun onResponse(call: Call<AddMembersDataClass?>?, response: Response<AddMembersDataClass?>) {
            Toast.makeText(context,"User Added", Toast.LENGTH_SHORT).show()
            val model: AddMembersDataClass? = response.body()
            val resp =
                "Response Code : " + response.code()
            viewModel.result=resp
        }
        override fun onFailure(call: Call<AddMembersDataClass?>?, t: Throwable) {
            var temp = "Error found is : " + t.message
            Toast.makeText(context,temp, Toast.LENGTH_SHORT).show()
        }
    })
}
