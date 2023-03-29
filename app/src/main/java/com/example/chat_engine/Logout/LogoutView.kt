//package com.example.chat_engine.Logout
//
//import android.app.Application
//import android.content.Context
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.Composable
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.Button
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Text
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.BlendMode.Companion.Screen
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.unit.dp
//import androidx.core.content.edit
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavController
//
//private const val PREFS_NAME = "my_prefs"
//private const val KEY_IS_LOGGED_IN = "is_logged_in"
//
//@Composable
//fun LogoutScreen(navController: NavController) {
//    val context = LocalContext.current
//    val viewModel: LogoutViewModel = viewModel()
//    val isLoggedIn by remember { mutableStateOf(viewModel.isLoggedIn) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(horizontal = 16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text(
//            text = "Are you sure you want to log out?",
//            style = MaterialTheme.typography.h5
//        )
//        Button(
//            modifier = Modifier.padding(top = 32.dp),
//            onClick = {
//                context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit {
//                    putBoolean(KEY_IS_LOGGED_IN, false)
//                }
//                viewModel.onLogout()
//                navController.navigate(Screen.Login.route) {
//                    popUpTo(Screen.Home.route) { inclusive = true }
//                }
//            }
//        ) {
//            Text(text = "Log out")
//        }
//    }
//}
//
//class LogoutViewModel : ViewModel() {
//    private val sharedPreferences =
//        getApplication<Application>().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
//    val isLoggedIn: Boolean
//        get() = sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
//
//    fun onLogout() {
//        sharedPreferences.edit {
//            putBoolean(KEY_IS_LOGGED_IN, false)
//        }
//    }
//}