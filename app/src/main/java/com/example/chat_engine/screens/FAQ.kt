package com.example.chat_engine.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chat_engine.Questions.ShowQuestions
import com.example.chat_engine.Questions.QuestionListing
import com.example.chat_engine.Questions.QuestionViewModel
import com.example.chat_engine.ViewModel.MainViewModel
import com.example.chat_engine.mychats.ChatHelpingFunction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


/*
To show the faq to which can be clicked
and then a name can be given to the chat
which will be created when the support button is clicked
 */
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
        Column(modifier = Modifier
            .fillMaxHeight(0.9f)
        ) {
            /*
            Composable that shows questions
             */
            ShowQuestions(questionViewModel,viewModel)
        }
        Column(
            modifier= Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    onClickGoToChatScreen()
                    ChatHelpingFunction(context,viewModel)
                    Toast.makeText(context,viewModel.chat_name,Toast.LENGTH_SHORT).show()

                }) {
                Text(text = "Support")
            }
        }

    }
}