package com.example.chat_engine.mychats.GetMessages.GetMessagesDataClass

data class Sender(
    val avatar: String,
    val custom_json: CustomJsonX,
    val first_name: String,
    val is_online: Boolean,
    val last_name: String,
    val username: String
)