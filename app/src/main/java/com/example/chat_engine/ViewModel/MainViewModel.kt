package com.example.chat_engine.ViewModel
import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chat_engine.GetChatMembers.GetChatMembersApi
import com.example.chat_engine.GetChatMembers.GetChatMembersClass
import com.example.chat_engine.GetChatMembers.dataclasses.ChatMembersDataClass
import com.example.chat_engine.GetChats.GetChatApi
import com.example.chat_engine.GetChats.GetChatClass
import com.example.chat_engine.GetChats.GetChatsDataClass
import com.example.chat_engine.GetUsers.GetUserApi
import com.example.chat_engine.GetUsers.GetUserClass
import com.example.chat_engine.GetUsers.GetUsersDataClass
import com.example.chat_engine.IsTyping.IsTypingClass
import com.example.chat_engine.IsTyping.IstypingApi
import com.example.chat_engine.login.LoginClass
import com.example.chat_engine.login.LoginDataClass
import com.example.chat_engine.login.loginService
import com.example.chat_engine.mychats.AddChatMembers.AddMemberClass
import com.example.chat_engine.mychats.AddChatMembers.AddMembersApi
import com.example.chat_engine.mychats.CreateChatClass
import com.example.chat_engine.mychats.CreateChat.CreateChatDataFile
import com.example.chat_engine.mychats.GetMessages.ChatMessagesApi
import com.example.chat_engine.mychats.GetMessages.ChatMessagesClass
import com.example.chat_engine.mychats.GetMessages.GetMessagesDataClass.MessagesDataClassItem
import com.example.chat_engine.mychats.MakeChatService
import com.example.chat_engine.mychats.SendMessages.SendMessageDataClass
import com.example.chat_engine.mychats.SendMessages.SendMessagesApi
import com.example.chat_engine.mychats.SendMessages.SendMessagesClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("MutableCollectionMutableState")
class MainViewModel():ViewModel() {

    private val _messageList= MutableStateFlow(emptyList<MessagesDataClassItem>())
    val messageList:StateFlow<List<MessagesDataClassItem>> = _messageList

    fun updateMessageList(newList: List<MessagesDataClassItem>){
        _messageList.value=newList
    }

    var result by mutableStateOf("")

//    to authenticate user
    var user_name by mutableStateOf("")
    var password by mutableStateOf("")

    var initial_Data= LoginDataClass("","",false,"","",true,"")
    var UserData: LoginDataClass? by mutableStateOf(initial_Data)
    var CreateChatName by mutableStateOf("")

    fun AuthenticateUser(): loginService {
            val apiService= LoginClass(user_name,password).getInstance()
            return apiService
    }

//    to create new chat
    var chat_name by mutableStateOf("Agent")

    var tempchat= CreateChatDataFile(false,"", usernames = listOf("Admin"))
    var NewChatDetails: CreateChatDataFile? by mutableStateOf(tempchat)


    var ChatList:MutableList<MessagesDataClassItem> by mutableStateOf(mutableListOf())

    fun CreateNewChat():MakeChatService{
        val apiService=CreateChatClass(user_name,password).getInstance()
        return apiService
    }


//    This function is called to get
//    all messages with their respective details

    var chatId by mutableStateOf(0)
    var chatAccessKey by mutableStateOf("")
    fun GetMessages(chatId:Int): ChatMessagesApi{
        val apiService=ChatMessagesClass(user_name,password,chatId).getInstance()
        return apiService
    }

//    this is to send and recieve data
    /*
    * */
    var Send_Message by mutableStateOf("")
    var MyMessagesList by mutableStateOf(listOf(SendMessageDataClass("")))
    fun sendMessages():SendMessagesApi{
        val apiService=SendMessagesClass(user_name,password,chatId).getInstance()
        return apiService
    }

//    this is for getting chat details
    var GetChatList:MutableList<GetChatsDataClass> by mutableStateOf(mutableListOf())
    fun GetChats():GetChatApi{
        val apiService=GetChatClass(user_name,password).getInstance()
        return apiService
    }



//    To get chat usernames
    var UsersList:MutableList<GetUsersDataClass> by mutableStateOf(mutableListOf())
    fun GetUsers(): GetUserApi {
        val apiService= GetUserClass(user_name,password).getInstance()
        return apiService
    }

//    To get chat members usernames
    var ChatMembersList:MutableList<ChatMembersDataClass> by mutableStateOf(mutableListOf())
    fun GetChatMembers(): GetChatMembersApi {
        val apiService= GetChatMembersClass(user_name,password,chatId).getInstance()
        return apiService
    }

    var MemberName by mutableStateOf("")
    fun AddMemberInChat(): AddMembersApi {
        val apiService= AddMemberClass(user_name,password,chatId).getInstance()
        return apiService
    }

//    this is to check if a user is typing or not


    val istyping = mutableStateOf(false)
    val istypinguser= mutableStateOf("")

    @SuppressLint("SuspiciousIndentation")
    fun IsUserTyping(): IstypingApi {
    val apiService= IsTypingClass(user_name,password,chatId).getInstance()
        return apiService
    }
    fun starttyping(){
        viewModelScope.launch {
            withContext(Dispatchers.Default){
                delay(2000L)
            }
            istyping.value=false
        }
    }

//    LOADING INDICATOR
   val open = mutableStateOf(false)

fun startThread() {
    viewModelScope.launch {
        withContext(Dispatchers.Default) {
            // Do the background work here
            // I'm adding delay
            delay(100000)
        }
        closeDialog()
    }
}
    private fun closeDialog() {
        open.value = false
    }
}