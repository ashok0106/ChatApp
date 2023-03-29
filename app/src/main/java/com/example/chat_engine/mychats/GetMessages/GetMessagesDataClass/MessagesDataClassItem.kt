package com.example.chat_engine.mychats.GetMessages.GetMessagesDataClass

data class MessagesDataClassItem(
//    val attachments: List<Any>,
    val created: String,
//    val custom_json: CustomJson,
//    val id: Int,
//    val sender: Sender,
    val sender_username: String,
    val text: String
)