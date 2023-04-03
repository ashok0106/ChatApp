package com.example.chat_engine

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chat_engine.ViewModel.MainViewModel
import com.example.chat_engine.navigation.navigation
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding


class MainActivity : ComponentActivity() {

    lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("SuspiciousIndentation", "CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
    WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContent {

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

                navigation(
                    vm,
                    sharedPreferences
                )
//            ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)){ view, insets->
//                val bottom = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
//                view.updatePadding(bottom =bottom)
//                insets
//        }
        }
    }
}


