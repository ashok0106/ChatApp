package com.example.chat_engine.login

data class LoginDataClass(
    val avatar: Any,
    val first_name: String,
    val is_online: Boolean,
    val last_name: String,
    val username: String,
    val is_authenticated:Boolean,
    val secret:String
)