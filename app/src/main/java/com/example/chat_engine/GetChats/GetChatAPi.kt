package com.example.chat_engine.GetChats

import android.content.Context
import android.widget.Toast
import com.example.chat_engine.ConstantData.ConstVariables
import com.example.chat_engine.ConstantData.httpclient
import com.example.chat_engine.ViewModel.MainViewModel
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface GetChatApi{
    @GET("chats/")
    fun getChats(): Call<List<GetChatsDataClass>?>?
}

class GetChatClass(val username:String,val password:String){

    fun getInstance(): GetChatApi {

        val httpClient = httpclient(username,password)

        val retrofitAPI = Retrofit.Builder()
            .baseUrl(ConstVariables().GetChat_base_url)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(GetChatApi::class.java)
        return retrofitAPI
    }
}
fun GetChatDetails(
    context: Context,
    vm: MainViewModel,
){

    val retrofitApi=vm.GetChats()
    val call: Call<List<GetChatsDataClass>?>? = retrofitApi.getChats()
    call!!.enqueue(object : Callback<List<GetChatsDataClass>?> {
        override fun onResponse(call: Call<List<GetChatsDataClass>?>?, response: Response<List<GetChatsDataClass>?>) {
//            Toast.makeText(context, "Data posted to API", Toast.LENGTH_SHORT).show()
            val model: List<GetChatsDataClass>? = response.body()
            val resp =
                "Response Code : " + response.code() + "\n" + "id " + model?.size + "\n" + "Title : " + model?.size
            println("////////////////////////////////////////////// Response Code:${response.code()}")
            vm.result = resp
            if (model != null) {
                vm.GetChatList= model as MutableList<GetChatsDataClass>
//                onClickGotoMessages()
            }
        }
        override fun onFailure(call: Call<List<GetChatsDataClass>?>?, t: Throwable) {
            var temp = "Error found is : " + t.message
            vm.result=temp
            Toast.makeText(context,temp, Toast.LENGTH_SHORT).show()

        }
    })
}