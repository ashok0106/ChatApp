package com.example.chat_engine

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chat_engine.ViewModel.MainViewModel
import com.example.chat_engine.navigation.navigation
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.lifecycleScope
import com.example.chat_engine.Questions.QuestionList
import com.example.chat_engine.Questions.QuestionListing
import com.example.chat_engine.Questions.QuestionViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : ComponentActivity() {

    lateinit var sharedPreferences: SharedPreferences

    val questionViewModel by viewModels<QuestionViewModel>()
    @SuppressLint("SuspiciousIndentation", "CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        lifecycleScope.launch {
//            withContext(Dispatchers.IO){
//
//                QuestionListing(questionViewModel)
//            }
//        }
        setContent {

//            QuestionList(questionViewModel)

            val vm: MainViewModel = viewModel()
            val context = LocalContext.current
            sharedPreferences = getSharedPreferences("UserDetails", Context.MODE_PRIVATE)
            val username = remember {
                mutableStateOf("")
            }
            val pwd = remember {
                mutableStateOf("")
            }
            username.value = sharedPreferences.getString("USERNAME", "").toString()
            pwd.value = sharedPreferences.getString("SECRET", "").toString()
                navigation(vm,sharedPreferences)
        }
    }
}


