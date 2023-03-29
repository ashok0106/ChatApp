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
import com.example.chat_engine.screens.SimpleTextField
import com.example.chat_engine.screens.toColor


@Composable
fun SignUpScreen(
    vm: MainViewModel
) {
    val context= LocalContext.current
    var result = remember { mutableStateOf("") }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val gradient = Brush.linearGradient(
            colors = listOf("#9e05fc".toColor(), "#0571e6".toColor()),
            start = Offset(0f, 0f),
            end = Offset(0f, constraints.maxHeight.toFloat())
        )
        val gradient2 = Brush.linearGradient(
            colors = listOf("#cd7aff".toColor(),"#9dc3ed".toColor()),
            start = Offset(0f, 0f),
            end = Offset(0f, constraints.maxHeight.toFloat())
        )
        Column(
            modifier = Modifier.background(gradient)
                .fillMaxSize()
        ) {
            Card(
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .padding(top = 150.dp, start = 20.dp, end = 20.dp, bottom = 0.dp)
                    .clip(RoundedCornerShape(60.dp))
            )
            {
                Box(
                    modifier = Modifier.fillMaxSize()
                        .background(brush = gradient2)
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

                        vm.user_name = SimpleTextField("Username", KeyboardType.Text)
                        Spacer(modifier = Modifier.height(20.dp))

                        vm.password = SimpleTextField("Password", KeyboardType.Password)
                        var first_name by remember {
                            mutableStateOf("")
                        }
                        Spacer(modifier = Modifier.height(20.dp))

                        first_name = SimpleTextField(
                            placeholder = "First Name",
                            KeyboardType.Text
                        )
                        var last_name by remember {
                            mutableStateOf("")
                        }
                        Spacer(modifier = Modifier.height(20.dp))

                        last_name = SimpleTextField(
                            placeholder = "Last Name",
                            KeyboardType.Text
                        )
                        Spacer(modifier = Modifier.height(20.dp))

                        Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                            Button(
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