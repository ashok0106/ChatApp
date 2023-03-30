package com.example.chat_engine.screens

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType

import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.chat_engine.GetChats.GetChatsDataClass
import com.example.chat_engine.R
import com.example.chat_engine.login.LoginDataClass
import com.example.chat_engine.ViewModel.MainViewModel
import com.example.chat_engine.login.second1
import com.example.chat_engine.ui.theme.Purple700
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun LoginScreen(
    onClickGotoMainScreen:()->Unit,
    onClickGotoSignUpScreen:()->Unit,
    vm: MainViewModel,
    sharedPreferences: SharedPreferences
) {
    val ColumnBackGround ="#d6d6d6".toColor()
    val CardBackGround ="#f5f5f2".toColor()
    val context= LocalContext.current

    if(vm.open.value) {
        vm.startThread()
        DialogBoxLoading()
    }
    val email = sharedPreferences.getString("USERNAME", "").toString()
    val secrett = sharedPreferences.getString("SECRET", "").toString()

    println("*** $email")

    if (email.isNotBlank()){
        vm.user_name = email
        vm.password = secrett

        second1(context,email,secrett,onClickGotoMainScreen,vm,sharedPreferences)
    }
    else {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val gradient = Brush.linearGradient(
                colors = listOf("#9e05fc".toColor(), "#0571e6".toColor()),
                start = Offset(0f, 0f),
                end = Offset(0f, constraints.maxHeight.toFloat())
            )
            val gradient2 = Brush.linearGradient(
                colors = listOf("#cd7aff".toColor(), "#9dc3ed".toColor()),
                start = Offset(0f, 0f),
                end = Offset(0f, constraints.maxHeight.toFloat())
            )

            Column(
                modifier = Modifier
                    .background(gradient)
                    .fillMaxSize()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxHeight(0.8f)
                        .padding(top = 150.dp, start = 20.dp, end = 20.dp, bottom = 0.dp)
                        .clip(RoundedCornerShape(60.dp)),
                    backgroundColor = CardBackGround
//            .background(CardBackGround)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(brush = gradient2)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.user),
                            contentDescription = "Login Icon",
                            modifier = Modifier
                                .padding(
                                    start = 140.dp,
                                    top = 20.dp
                                )
                                .height(70.dp)
                                .width(70.dp),
                            contentScale = ContentScale.Crop
                        )
                        ClickableText(
                            text = AnnotatedString("Sign up here"),
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(20.dp),
                            onClick = {
                                onClickGotoSignUpScreen()
                            },
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontFamily = androidx.compose.ui.text.font.FontFamily.Default,
                                textDecoration = TextDecoration.Underline,
                                color = Purple700
                            )
                        )
                    }
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "Login",
                            style = TextStyle(
                                fontSize = 40.sp,
                                fontFamily = androidx.compose.ui.text.font.FontFamily.Serif
                            )
                        )

                        Spacer(modifier = Modifier.height(20.dp))
                        vm.user_name = SimpleTextField("Username", KeyboardType.Text)
                        Spacer(modifier = Modifier.height(20.dp))
                        vm.password = SimpleTextField("Password", KeyboardType.Password)
                        Spacer(modifier = Modifier.height(20.dp))
                        Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {

                            var temp by remember {
                                mutableStateOf(false)
                            }
                            var buttoncolor by remember {
                                mutableStateOf("#a302cc".toColor())
                            }
                            if (vm.user_name == "" || vm.password == "") {
                                temp = false
                                buttoncolor = "#a302cc".toColor()
                            } else {
                                temp = true
                                buttoncolor = "#f4defa".toColor()
                            }


                            Button(
                                colors = ButtonDefaults.buttonColors(backgroundColor = buttoncolor),
                                enabled = temp,
                                onClick = {
                                    vm.open.value = true
                                    second1(
                                        context = context,
                                        vm.user_name,
                                        vm.password,
                                        onClickGotoMainScreen,
                                        vm,
                                        sharedPreferences
                                    )
                                },
                                shape = RoundedCornerShape(50.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                            ) {
                                Text(text = "Login")
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
    }
}
@Composable
fun SimpleTextField(
    placeholder: String,
    myType: KeyboardType
):String {
    var text by remember { mutableStateOf("") }
    OutlinedTextField(
        value = text,
        onValueChange = { newText ->
            text = newText
        },
        placeholder= {Text(text = placeholder)},
        keyboardOptions = KeyboardOptions(keyboardType = myType)
    )
    return text
}



@Composable
fun DialogBoxLoading(
    cornerRadius: Dp = 16.dp,
    paddingStart: Dp = 56.dp,
    paddingEnd: Dp = 56.dp,
    paddingTop: Dp = 32.dp,
    paddingBottom: Dp = 32.dp,
    progressIndicatorColor: Color = Color(0xFF35898f),
    progressIndicatorSize: Dp = 80.dp
) {

    Dialog(
        onDismissRequest = {
        }
    ) {
        Surface(
            elevation = 4.dp,
            shape = RoundedCornerShape(cornerRadius)
        ) {
            Column(
                modifier = Modifier
                    .padding(start = paddingStart, end = paddingEnd, top = paddingTop),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                ProgressIndicatorLoading(
                    progressIndicatorSize = progressIndicatorSize,
                    progressIndicatorColor = progressIndicatorColor
                )

                // Gap between progress indicator and text
                Spacer(modifier = Modifier.height(32.dp))

                // Please wait text
                Text(
                    modifier = Modifier
                        .padding(bottom = paddingBottom),
                    text = "Please wait...",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
//                        fontFamily = FontFamily(
//                            Font(R.font, FontWeight.Normal)
//                        )
                    )
                )
            }
        }
    }
}
@Composable
fun ProgressIndicatorLoading(progressIndicatorSize: Dp, progressIndicatorColor: Color) {

    val infiniteTransition = rememberInfiniteTransition()

    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 600
            }
        )
    )

    CircularProgressIndicator(
        progress = 1f,
        modifier = Modifier
            .size(progressIndicatorSize)
            .rotate(angle)
            .border(
                12.dp,
                brush = Brush.sweepGradient(
                    listOf(
                        Color.White, // add background color first
                        progressIndicatorColor.copy(alpha = 0.1f),
                        progressIndicatorColor
                    )
                ),
                shape = CircleShape
            ),
        strokeWidth = 1.dp,
        color = Color.White // Set background color
    )
}