package com.example.chat_engine.screens

import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chat_engine.ProgressBarIndicator.LoadingFile
import com.example.chat_engine.R
import com.example.chat_engine.ViewModel.MainViewModel
import com.example.chat_engine.login.LoginHelpingFunction
import com.example.chat_engine.ui.theme.Purple700




/*
Called when the app opens
 */
@Composable
fun LoginScreen(
    onClickGotoChatScreen:()->Unit,
    onClickGotoSignUpScreen:()->Unit,
    viewModel: MainViewModel,
    sharedPreferences: SharedPreferences
) {

    val username = sharedPreferences.getString("USERNAME", "").toString()
    val secrett = sharedPreferences.getString("SECRET", "").toString()

    /*
    if user is already logged in then navigate to chat screen
    else open the login screen
     */
    if (username.isNotBlank()){
        viewModel.user_name = username
        viewModel.password = secrett
        onClickGotoChatScreen()
    }
    else {
        FirstLogin(
            onClickGotoChatScreen,
            onClickGotoSignUpScreen,
            viewModel
        )

    }
}

/*
Composable to show login components = {text-fields,login buttons}
 */
@Composable
fun FirstLogin(
    onClickGotoChatScreen:()->Unit,
    onClickGotoSignUpScreen:()->Unit,
    viewModel: MainViewModel,
) {
    val context= LocalContext.current
    val cardBackGround ="#f5f5f2".toColor()

    /*
    The variable open stores the status for the
    dialog box
    If it is true then loading-function is called
    and a loader appears on the screen
     */
    if(viewModel.open.value) {
        viewModel.startThread()
        LoadingFile()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()

        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(700.dp)
                    .padding(top = 150.dp, start = 20.dp, end = 20.dp, bottom = 0.dp)
                    .clip(RoundedCornerShape(60.dp)),
                backgroundColor = cardBackGround
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
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
                    /*
                    On clicking here it navigates to the sign up screen
                     */
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

                    /*
                    Variables to store username and password which
                    are declared in the MainViewModel class
                     */
//                    Column(modifier=Modifier.testTag("username")) {
//                        viewModel.user_name = simpleTextField("Username", KeyboardType.Text)
//                    }
                    var text1 by remember { mutableStateOf("") }
                    OutlinedTextField(
                        modifier=Modifier.testTag("username"),
                        value = text1,
                        onValueChange = { newText ->
                            text1 = newText
                        },
                        placeholder= {Text(text = "")},
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                    viewModel.user_name=text1

                    Spacer(modifier = Modifier.height(20.dp))

                    var text2 by remember{ mutableStateOf("") }
                    OutlinedTextField(
                        modifier=Modifier.testTag("password"),
                        value = text2,
                        onValueChange = { newText ->
                            text2 = newText
                        },
                        placeholder= {Text(text = "")},
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                    viewModel.password=text2
                    Spacer(modifier = Modifier.height(20.dp))

                    Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                        /*
                        Variable to store status of text-fields so that
                        button enables only if the below conditions match
                         */
                        var temp by remember {
                            mutableStateOf(false)
                        }
                        var buttoncolor by remember {
                            mutableStateOf("#a302cc".toColor())
                        }
                        if (viewModel.user_name == "" || viewModel.password == "") {
                            temp = false
                            buttoncolor = "#a302cc".toColor()
                        } else {
                            temp = true
                            buttoncolor = "#f4defa".toColor()
                        }

                        /*
                        on clicking this button first login helping function is called
                        which calls the login authentication api then navigates to
                        the chat-screen if the user is authenticated else user stays on this
                        screen only
                         */
                        Button(
                            colors = ButtonDefaults.buttonColors(backgroundColor = buttoncolor),
                            enabled = temp,
                            onClick = {
                                viewModel.open.value = true
                                LoginHelpingFunction(
                                    context = context,
                                    viewModel.user_name,
                                    viewModel.password,
                                    onClickGotoChatScreen,
                                    viewModel
                                )
                            },
                            shape = RoundedCornerShape(50.dp),
                            modifier = Modifier.testTag("login")
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


/*
A composable to return the value stored in the textfield
such that this value can be stored in the variables declared
in the viewmodel

 */
@Composable
fun simpleTextField(
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



