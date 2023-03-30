package com.example.chat_engine.WebSocket

import android.os.Looper
import android.util.Log
import com.example.chat_engine.ViewModel.MainViewModel
import com.example.chat_engine.mychats.GetMessages.GetMessagesDataClass.MessagesDataClassItem
import com.google.gson.Gson
import okhttp3.*
import org.json.JSONObject
import java.net.SocketException
import java.util.logging.Handler

//
open class ChatWebSocket(private val mainViewModel: MainViewModel): WebSocketListener(){
    var webSocket: WebSocket

    init {
        val request = Request.Builder().url("wss://api.chatengine.io/chat/?projectID=05218f40-be15-4be5-9db2-13dc139a5f0a&chatID=${mainViewModel.chatId}&accessKey=${mainViewModel.chatAccessKey}").build()
        val client = OkHttpClient()
        webSocket = client.newWebSocket(request, this)
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        Log.d("MYTAG", "WebSocket connection established.")
//        webSocket.send("Hello, server!")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        Log.d("MYTAG", "onMessage: $text ")


        val gson = Gson()
        val json = JSONObject(text)
        val action = json.getString("action")

        if(action=="is_typing"){
            val data=json.getJSONObject("data")
            val name=data.getString("person")
            mainViewModel.istyping.value=true
            mainViewModel.istypinguser.value= name.toString()
//            println("MYTAG /////////////////// $name")
        }

        if (action == "new_message") {
            val message = json.getJSONObject("data").getJSONObject("message")
            val receivedMessage = MessagesDataClassItem(
                text = message.getString("text"),
                created = message.getString("created"),
                sender_username = message.getString("sender_username")
            )
            mainViewModel.updateMessageList((mainViewModel.messageList.value+message) as List<MessagesDataClassItem>)
            mainViewModel.ChatList.add(receivedMessage)
            Log.d("MYTAG", "onMessage: $receivedMessage ")
        }
    }

    fun onClose(webSocket: WebSocket, code: Int, reason: String) {
        Log.d("MYTAG", "WebSocket connection closed.")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        if (t is SocketException && t.message?.contains("Broken pipe") == true) {
            Log.d("MYTAG", "WebSocket failure: Broken pipe")
            // Reconnect the WebSocket here
            val request = Request.Builder()
                .url("wss://api.chatengine.io/chat/?projectID=52690bdb-3b85-4b96-9081-27fa9b4dc10e&chatID=153464&accessKey=ca-529db72b-f253-4bdb-9be1-8719383ecc2a")
                .build()
            val client = OkHttpClient()
            this.webSocket = client.newWebSocket(request, this)
        }
        else{Log.d("MYTAG", "WebSocket failure.", t)}
        Log.d("MYTAG", "WebSocket failure.", t)

    }

    fun sendMessage(message: String) {
        webSocket.send(message)
    }

    fun closeWebSocket() {
        webSocket.close(1000, "Closing WebSocket.")
    }

    private var typingHandler= android.os.Handler(Looper.getMainLooper())
    private val TYPING_TIMER_DELAY=1500L

     fun onTextChanged(text: String) {
            // Send 'istyping' to server
            webSocket.send("{\"action\": \"istyping\"}")

            // Cancel any previous typing indicator scheduled to be hidden
            typingHandler.removeCallbacks(hideTypingIndicator)

            // Schedule hiding the typing indicator after a delay
            typingHandler.postDelayed(hideTypingIndicator, TYPING_TIMER_DELAY.toLong())
    }

    private val hideTypingIndicator = Runnable {
        // Hide typing indicator after delay
        webSocket.send("{\"action\": \"stoptyping\"}")
    }

}