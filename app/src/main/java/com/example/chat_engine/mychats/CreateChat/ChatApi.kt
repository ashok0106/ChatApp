package com.example.chat_engine.mychats

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import com.example.chat_engine.ConstantData.ConstVariables
import com.example.chat_engine.ConstantData.httpclient
import com.example.chat_engine.ConstantData.retrofitApi
import com.example.chat_engine.ViewModel.MainViewModel
import com.example.chat_engine.mychats.CreateChat.CreateChatDataFile
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT


interface MakeChatService{
    @PUT("chats/")
    fun addChat(
        @Body myadded: CreateChatDataFile?
    ): Call<responsedataclass?>?
}

class CreateChatClass(val username:String,val password:String){

    fun getInstance(): MakeChatService {
        val httpClient = httpclient(username,password)
        val retrofitAPI = retrofitApi(httpClient,ConstVariables().chat_base_url)
            .create(MakeChatService::class.java)
        return retrofitAPI
    }
}

fun ChatHelpingFunction(
    context: Context,
    viewModel: MainViewModel,
)
{

//    val editor: SharedPreferences.Editor = sharedPreferences.edit()
//    editor.putString("USERNAME", username)
//    editor.putString("SECRET", password)
//    editor.apply()

    val myadded= CreateChatDataFile(false,viewModel.chat_name, listOf("Admin"))
    println(viewModel.chat_name)
    val retrofitAPI= viewModel.CreateNewChat()

    val call: Call<responsedataclass?>? = retrofitAPI.addChat(myadded)

    call!!.enqueue(object : Callback<responsedataclass?> {
        override fun onResponse(call: Call<responsedataclass?>?, response: Response<responsedataclass?>) {
            Toast.makeText(context, "Room Created ${viewModel.user_name } ${viewModel.password}", Toast.LENGTH_SHORT).show()
            val model: responsedataclass? = response.body()
            val resp =
                "Response Code : " + response.code() + "\n" + "id " + model?.id + "\n" + "Title : " + model?.id
            viewModel.NewChatDetails?.title=resp

//            onClickGotoMainScreen()
        }

        override fun onFailure(call: Call<responsedataclass?>?, t: Throwable) {
            var temp = "Error found is : " + t.message
            Toast.makeText(context,temp, Toast.LENGTH_SHORT).show()
        }
    })
}