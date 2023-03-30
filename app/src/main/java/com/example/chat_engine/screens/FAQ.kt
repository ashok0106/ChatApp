package com.example.chat_engine.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chat_engine.Questions.ShowQuestions
import com.example.chat_engine.Questions.QuestionListing
import com.example.chat_engine.Questions.QuestionViewModel
import com.example.chat_engine.ViewModel.MainViewModel
import com.example.chat_engine.mychats.ChatHelpingFunction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun FAQScreen(onClickGoToChatScreen:()->Unit,viewModel: MainViewModel) {
    val context= LocalContext.current
    val questionViewModel:QuestionViewModel= viewModel()
    LaunchedEffect(true){
        withContext(Dispatchers.IO){
            QuestionListing(questionViewModel)
        }
    }
    Column() {
        Column(modifier = androidx.compose.ui.Modifier
            .fillMaxHeight(0.9f)
        ) {
            ShowQuestions(questionViewModel,viewModel)
        }
        Column(
            modifier= androidx.compose.ui.Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    onClickGoToChatScreen()
//                    viewModel.GenerateChatName()
                    ChatHelpingFunction(context,viewModel)
                    Toast.makeText(context,viewModel.chat_name,Toast.LENGTH_SHORT).show()
                }) {
                Text(text = "Support")
            }
        }

    }


//    CoroutineScope().launch{
//    QuestionListing(questionViewModel)
//    }

}