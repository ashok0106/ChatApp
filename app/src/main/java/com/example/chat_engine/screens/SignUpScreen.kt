package com.example.chat_engine.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chat_engine.ViewModel.MainViewModel
import com.example.chat_engine.screens.simpleTextField
import com.example.chat_engine.screens.toColor


@Composable
fun SignUpScreen(
    vm: MainViewModel
) {
    val context= LocalContext.current
    var result = remember { mutableStateOf("") }
    val cardBackGround ="#f5f5f2".toColor()
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Card(
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .padding(top = 150.dp, start = 20.dp, end = 20.dp, bottom = 0.dp)
                    .clip(RoundedCornerShape(60.dp)),
                backgroundColor = cardBackGround
            )
            {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                        Text(
                            text = "SignUp",
                            style = TextStyle(fontSize = 40.sp, fontFamily = FontFamily.Serif)
                        )

                        Spacer(modifier = Modifier.height(20.dp))
                    /*
                    Variable to store the username
                     */
                        vm.user_name = simpleTextField("Username", KeyboardType.Text)
                        Spacer(modifier = Modifier.height(20.dp))
                    /*
                    Variable to store the password
                     */
                        vm.password = simpleTextField("Password", KeyboardType.Password)
                        Spacer(modifier = Modifier.height(20.dp))

                    /*
                   Variable to store the first name
                    */
                        var first_name by remember { mutableStateOf("") }
                        first_name = simpleTextField(
                            placeholder = "First Name",
                            KeyboardType.Text
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    /*
                   Variable to store the lastname
                    */
                        var last_name by remember {
                            mutableStateOf("")
                        }

                        last_name = simpleTextField(
                            placeholder = "Last Name",
                            KeyboardType.Text
                        )
                        var flag by remember {
                            mutableStateOf(false)
                        }
                        flag = !(vm.user_name==""||vm.password==""||first_name==""||last_name=="")

                        Spacer(modifier = Modifier.height(20.dp))

                        Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                            Button(
                                enabled=flag,
                                onClick = {
                                    SignUpHelper(
                                        context = context,
                                        vm,
                                        first_name,
                                        last_name
                                    )
                                },
                                shape = RoundedCornerShape(50.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                            ) {
                                Text(text = "SignUP")
                            }
                        }
                    }
                }
            }
        }
    }
    Text(
        text = result.value,
        color = Color.Black,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold, modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}