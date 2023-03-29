//package com.example.chat_engine.Logout
//
//import android.annotation.SuppressLint
//import android.app.Activity
//import android.content.Context
//import android.content.Intent
//import android.content.SharedPreferences
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.*
//import androidx.compose.material.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.example.chat_engine.MainActivity
//
//
//class MainActivity2 : ComponentActivity() {
//    lateinit var sharedPreferences: SharedPreferences
//    var PREFS_KEY = "prefs"
//    var EMAIL_KEY = "email"
//    var email = ""
//
//    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        sharedPreferences = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
//        email = sharedPreferences.getString(EMAIL_KEY, "").toString()
//
//        setContent {
//            Scaffold(
//                topBar = {
//                    TopAppBar(backgroundColor = Color.Green,
//                        title = {
//                            Text(
//                                text = "Session Management in Android",
//                                modifier = Modifier.fillMaxWidth(),
//                                textAlign = TextAlign.Center,
//                                color = Color.White
//                            )
//                        }
//                    )
//                }
//            ) {
//                getData(email, sharedPreferences)
//            }
//        }
//    }
//}
//@Composable
//fun getData(email: String, sharedPreferences: SharedPreferences) {
//    val ctx = LocalContext.current
//    val activity = (LocalContext.current as? Activity)
//
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .fillMaxHeight()
//            .fillMaxSize(),
//
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = "Welcome \n" + email,
//
//            fontWeight = FontWeight.Bold,
//            color = Color.Green,
//            fontSize = 20.sp,
//            textAlign = TextAlign.Center
//        )
//        Spacer(modifier = Modifier.height(50.dp))
//        Button(
//
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//
//            onClick = {
//                val editor: SharedPreferences.Editor = sharedPreferences.edit()
//
//                editor.putString("email", "")
//                editor.putString("pwd", "")
//                editor.apply()
//                val i = Intent(ctx, MainActivity::class.java)
//                ctx.startActivity(i)
//                activity?.finish()
//            },
//            colors = ButtonDefaults.buttonColors(
//                backgroundColor = Color.Green,
//            )
//        ) {
//            Text(text = "Log Out")
//        }
//
//    }
//}