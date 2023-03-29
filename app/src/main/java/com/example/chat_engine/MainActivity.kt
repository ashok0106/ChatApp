package com.example.chat_engine

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chat_engine.ViewModel.MainViewModel
import com.example.chat_engine.navigation.navigation
import androidx.compose.ui.platform.LocalContext

//import com.example.chat_engine.Logout.MainActivity2


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val vm: MainViewModel = viewModel()
            val context = LocalContext.current
                navigation(vm)
        }
    }
}

