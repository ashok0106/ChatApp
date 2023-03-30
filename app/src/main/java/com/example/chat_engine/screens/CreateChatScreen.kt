package com.example.chat_engine.screens

import android.annotation.SuppressLint

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import com.example.chat_engine.ViewModel.MainViewModel
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import com.example.chat_engine.GetChats.GetChatDetails
import com.example.chat_engine.GetUsers.GetUsers
import com.example.chat_engine.MainActivity
import com.example.chat_engine.R
import com.example.chat_engine.mychats.AddChatMembers.AddMembersInChat
import com.example.chat_engine.mychats.ChatHelpingFunction
import com.example.chat_engine.mychats.GetMessages.getMessages

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ChatScreen(
    vm: MainViewModel,
    onClickGotoMessages:()->Unit,
    sharedPreferences:SharedPreferences,
    onClickGotoFAQScreen:()->Unit
) {
    val editor: SharedPreferences.Editor = sharedPreferences.edit()
    val context= LocalContext.current
    var showuser by remember {
        mutableStateOf(false)
    }

    GetChatDetails(context,vm)
    GetChatDetails(
        context,
        vm,
    )

    Column(
        modifier = Modifier.padding()
    ) {
        TopAppBar(
            title = { Text("Welcome ${vm.user_name}") },
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary,
            actions = {
                IconButton(
                    onClick = {
                        editor.putString("USERNAME", "")
                        editor.putString("SECRET", "")
                        editor.apply()
                        val intent = Intent(context, MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        context.startActivity(intent)
                    }) {
                    Icon(Icons.Filled.ExitToApp, contentDescription = "Search")
                }
            }
        )
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {

            itemsIndexed(vm.GetChatList) { index,item->

                var showDialog by remember { mutableStateOf(false) }
                var MyColor by remember {
                    mutableStateOf(Color.White)
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        ,
                    backgroundColor = Color.White,
                    elevation = 4.dp,
                    shape = MaterialTheme.shapes.medium,
                    content = {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .background(MyColor)
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = {
                                            MyColor = Color.Gray
                                        }
                                    )
                                }
                                .clickable(
                                    onClick = {
                                        vm.open.value = true
                                        vm.chatId = item.id
                                        vm.chatAccessKey = item.access_key
                                        onClickGotoMessages()
                                        getMessages(
                                            context = context,
                                            vm,
                                            vm.chatId,
                                        )
                                    }
                                ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            GradientIcon(
                                painter = painterResource(R.drawable.baseline_account_circle_24),
                                colors = listOf(
                                    MaterialTheme.colors.primary,
                                    MaterialTheme.colors.primaryVariant
                                ),
                                shape = MaterialTheme.shapes.small,
                                modifier = Modifier
                                    .size(60.dp)
                                    .padding(end = 16.dp)
                                    .clickable { showDialog = true }
                            )
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(0.8f)
                            ) {
                                Text(
                                    text = item.title,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = item.id.toString(),
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                            }
                            IconButton(onClick = {
                                vm.chatId=item.id
                                showuser=true
//                                AddMembersInChat(context,vm)
                            }) {
                                Icon(Icons.Filled.Add, contentDescription = "Add Member")
                            }
                        }
                    }
                )
                if (showDialog) {
                    Dialog(
                        onDismissRequest = { showDialog = false },
                        content = {
                            Column(
                                modifier = Modifier
                                    .background(MaterialTheme.colors.surface)
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                //        vm.chat_name=SimpleTextField("Enter Chat Room Name", KeyboardType.Text)

                                Text(
                                    text = item.title,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Check Check",
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    )
                }
                if (showuser) {
                    GetUsers(context,vm)
                    Dialog(
                        onDismissRequest = { showuser = false },
                        content = {
                            Card(
                                modifier= Modifier
                                    .fillMaxWidth(0.8f)
                                    .fillMaxHeight(0.6f)
                                    .clip(RoundedCornerShape(20.dp))
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxHeight(0.6f)
                                        .fillMaxWidth(0.8f)
                                        .background(MaterialTheme.colors.surface)
                                        .padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = "Click To Add",
                                        fontSize = 30.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(5.dp))
                                    LazyColumn(
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        itemsIndexed(vm.UsersList) { index, item ->
                                            Card(
                                                modifier = Modifier
                                                    .height(40.dp)
                                                    .fillMaxWidth()
                                                    .clickable {
                                                        vm.MemberName=item.username
                                                        AddMembersInChat(context,vm)
                                                        showuser=false
                                                    }
                                            ) {
                                                Column(
                                                    modifier = Modifier
                                                        .height(30.dp)
                                                        .fillMaxWidth(),
                                                    verticalArrangement = Arrangement.Center,
                                                    horizontalAlignment = Alignment.CenterHorizontally
                                                ) {
                                                    Text(
                                                        text = item.username.toString(),
                                                        fontSize = 20.sp,
                                                        fontWeight = FontWeight.SemiBold,

                                                    )
                                                }
                                            }
                                        }

                                    }
                                    Text(
                                        text = vm.UsersList.size.toString(),
                                        fontSize = 14.sp,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }

    }
    floating_Button(
        context,
        onClickGotoFAQScreen,
        vm
    )

//    val temp=AddMemberIcon(vm = vm, usershowDialog = showuser)
//    showuser=temp


}



@Composable
fun AddMemberIcon(
    vm: MainViewModel,
    usershowDialog:Boolean
):Boolean {
    var showDialog by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 30.dp, bottom = 30.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ) {

        showDialog=usershowDialog

        if (showDialog) {
            Dialog(
                onDismissRequest = { showDialog = false },
                content = {
                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(MaterialTheme.colors.surface)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Card(
                            modifier = Modifier
                        ) {
                            Column() {
                                var flag by remember {
                                    mutableStateOf(false)
                                }
                                vm.MemberName=SimpleTextField("Enter User Name", KeyboardType.Text)
                                flag = vm.MemberName != ""
                                Button(
                                    enabled = flag,
                                    onClick = {

                                    }) {
                                    Text(text = "Submit")
                                }
                            }
                        }
                    }
                }
            )
        }
    }
    return showDialog
}

@Composable
fun floating_Button(
    context:Context,
    onClickGotoFAQScreen:()->Unit,
    vm: MainViewModel
) {
    val context= LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 30.dp, bottom = 30.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ) {

        FloatingActionButton(
            onClick = {
//                showDialog=true
                onClickGotoFAQScreen()
            }
        )
        {
            Text(text = "Add")
        }
        if (showDialog) {
            Dialog(
                onDismissRequest = { showDialog = false },
                content = {
                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(MaterialTheme.colors.surface)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Card(
                            modifier = Modifier
                        ) {
                            Column() {
                                var flag by remember {
                                    mutableStateOf(false)
                                }

                                vm.chat_name=SimpleTextField("Enter Chat Room Name", KeyboardType.Text)
                                flag = vm.chat_name != ""
                                Button(
                                    enabled = flag,
                                    onClick = {
                                        ChatHelpingFunction(context,vm)
                                        GetChatDetails(
                                            context,
                                            vm,
                                        )

                                }) {
                                    Text(text = "Submit")
                                }
                            }
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun GradientIcon(
    painter: Painter,
    colors: List<Color>,
    shape: Shape,
    modifier: Modifier = Modifier,
    contentDescription: String? = null
) {
    Icon(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier
            .fillMaxHeight()
            .width(30.dp)
    )
}