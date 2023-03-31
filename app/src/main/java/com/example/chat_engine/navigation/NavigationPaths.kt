package com.example.chat_engine.navigation

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chat_engine.WebSocket.ChatWebSocket
//import com.example.chat_engine.login.LoginScreen
import com.example.chat_engine.ViewModel.MainViewModel
import com.example.chat_engine.screens.FAQScreen
import com.example.chat_engine.screens.GetMessagesFunction
import com.example.chat_engine.screens.LoginScreen
import com.example.chat_engine.screens.ChatScreen
import com.example.chat_engine.signup.SignUpScreen

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun navigation(
    vm: MainViewModel,
    sharedPreferences:SharedPreferences,
    navController: NavHostController= rememberNavController()
) {
    var startDestination="Login_Screen"
    val ChatWebSocket=ChatWebSocket(vm)

    NavHost(navController = navController, startDestination = startDestination){

        composable(route="Login_Screen"){
            LoginScreen(
                onClickGotoChatScreen={
                    navController.navigate("Chat_screen")
                },
                onClickGotoSignUpScreen={
                    navController.navigate("SignUpScreen")
                },
                vm,
                sharedPreferences
            )
        }

        composable(route="SignUpScreen"){
            SignUpScreen(vm)
        }
        composable(route="Chat_screen"){
            ChatScreen(
                vm,
                onClickGotoMessages={
                  navController.navigate("StartMessaging")
                },
                sharedPreferences,
                onClickGotoFAQScreen={
                    navController.navigate("GoToFAQScreen")
                },
            )
        }
        composable(route="StartMessaging"){
            GetMessagesFunction(
                vm = vm,
                ChatWebSocket,
                onClickGotoChatScreen={
                    navController.navigate("Chat_screen"){
                        popUpTo("second_screen")
                    }

                }
            )
        }
        composable(route="GoToFAQScreen"){
            FAQScreen(
                onClickGoToChatScreen={
                    navController.navigate("Chat_screen")
                },
                vm
            )
        }
    }

}