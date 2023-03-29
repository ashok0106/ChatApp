package com.example.chat_engine.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chat_engine.WebSocket.ChatWebSocket
//import com.example.chat_engine.login.LoginScreen
import com.example.chat_engine.ViewModel.MainViewModel
import com.example.chat_engine.screens.GetMessagesFunction
import com.example.chat_engine.screens.LoginScreen
import com.example.chat_engine.screens.second_screen
import com.example.chat_engine.signup.SignUpScreen

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun navigation(
    vm: MainViewModel,
    navController: NavHostController= rememberNavController()
) {
    var startDestination:String="first_screen"
    val ChatWebSocket=ChatWebSocket(vm)

    NavHost(navController = navController, startDestination = startDestination){
        composable(route="first_screen"){
            LoginScreen(
                onClickGotoMainScreen={
                    navController.navigate("second_screen")
                },
                onClickGotoSignUpScreen={
                    navController.navigate("SignUpScreen")
                },
                vm
            )
        }
        composable(route="SignUpScreen"){
            SignUpScreen(vm)
        }
        composable(route="second_screen"){
            second_screen(
                vm,
                onClickGotoMessages={
                  navController.navigate("StartMessaging")
                },
            )
        }
        composable(route="StartMessaging"){
            GetMessagesFunction(
                vm = vm,
                ChatWebSocket,
                onClickGotoChatScreen={
                    navController.navigate("second_screen"){
                        popUpTo("second_screen")
                    }

                }
            )
        }
    }

}