package com.example.chat_engine.WebSocket

data class ReceiveDataClass(
    val text:String,
    val created:String,
    val sender_username:String,
)
