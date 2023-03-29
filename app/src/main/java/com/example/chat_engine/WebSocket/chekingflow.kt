package com.example.chat_engine.WebSocket

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.chat_engine.mychats.GetMessages.GetMessagesDataClass.MessagesDataClassItem
import kotlinx.coroutines.flow.MutableSharedFlow

@Composable
fun senddata() {
    val dataFlow = remember { MutableSharedFlow<MessagesDataClassItem>() }
}