package com.example.chat_engine.screens

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chat_engine.GetChatMembers.GetChatMembers
import com.example.chat_engine.IsTyping.IsTypingHelpingFunction
import com.example.chat_engine.R
import com.example.chat_engine.WebSocket.ChatWebSocket
import com.example.chat_engine.ViewModel.MainViewModel
import com.example.chat_engine.mychats.SendMessages.SendMessageDataClass
import com.example.chat_engine.mychats.SendMessages.SendMessagesHelpingFunction
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun GetMessagesFunction(
    vm: MainViewModel,
    ChatWebSocket:ChatWebSocket,
    onClickGotoChatScreen:()->Unit
) {
    val context= LocalContext.current
    GetChatMembers(context = context,vm)
    if(vm.open.value) {
        vm.startThread()
        DialogBoxLoading()
    }
    var temp:()->Unit

    Column(modifier = Modifier
    ) {

        MyUI(vm,onClickGotoChatScreen)

        val messageListStateList=vm.messageList.collectAsState()
        val messageList=messageListStateList.value



        val temp = "#03DAC5".toColor()
        val temp2="#f5f7e1".toColor()

        val lazyListState= rememberLazyListState()
        if(vm.ChatList.size>0){
            LazyColumn(
                state=lazyListState,
                modifier = Modifier
                    .fillMaxHeight(0.9f)
                    .background(temp2),
            ){
                itemsIndexed(vm.ChatList){items,it->
                    val time=it.created.substring(10,16)
                    if(it.sender_username!=vm.user_name){
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                            , horizontalAlignment = Alignment.Start
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(start=20.dp,top=8.dp,end=8.dp)

                            ){
                                Card(

//                                shape = RoundedCornerShape(20.dp),
                                    modifier = Modifier
                                        .padding(15.dp)
//                                    .background(Color.White)
                                        .clip(RoundedCornerShape(50.dp))
                                    ,
                                    backgroundColor = Color.White
                                ) {
                                    Column(
                                    )
                                    {
                                        Text(
                                            text = it.text,
                                            fontSize = 20.sp,
                                            modifier = Modifier
                                                .padding(start=5.dp,end = 8.dp)
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = time,
                                            modifier = Modifier
                                                .padding(start=5.dp,end = 8.dp),
                                            fontSize = 10.sp
                                        )
                                    }

                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    else{
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                            , horizontalAlignment = Alignment.End
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(start = 20.dp, top = 8.dp, end = 8.dp)

                            ) {
                                Card(
                                    modifier = Modifier
                                        .padding(15.dp)
                                        .clip(RoundedCornerShape(50.dp)),
                                    backgroundColor = temp
                                ) {
                                    Column(
                                    )
                                    {
                                        Text(
                                            text = it.text,
                                            fontSize = 20.sp,
                                            modifier = Modifier
                                                .padding(start = 10.dp, end = 8.dp)
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = time,
                                            modifier = Modifier
                                                .padding(start = 15.dp, end = 8.dp),
                                            fontSize = 10.sp
                                        )
                                    }

                                }
                            }
                        }
                    }
                }
            }
            LaunchedEffect(vm.ChatList.size){
                lazyListState.scrollToItem(vm.ChatList.size-1)
            }
        }
        else{
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight(0.9f)
                    .fillMaxWidth()
                    .background(temp2)
            ){
                items(1) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 100.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "No Messages Available",
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }


        Spacer(modifier = Modifier.height(0.dp))

        Card(
            modifier = Modifier
//                .clip(RoundedCornerShape(10.dp))
            ,
            backgroundColor = temp2
        ) {

            Box(
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp)
//                    .background(Color.Cyan)
                    .fillMaxWidth()
            ) {
                Row(
                    //            Modifier.height(15.dp)
                ) {
                    var text by remember { mutableStateOf("") }
                    //            vm.Send_Message=SimpleTextField()
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(0.7f),
                        value = text,
                        onValueChange = { newText ->
                            text = newText
                            IsTypingHelpingFunction(context,vm)
//                            SendMessagesHelpingFunction(context, vm)
                        },
                        placeholder = { Text(text = "Message") }
                        //        textStyle = TextStyle(brush = brush)
                    )
                    var flag by remember {
                        mutableStateOf(false)
                    }
                    flag = text != ""
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(
                        shape = CircleShape,
                        enabled = flag,
                        //                modifier = Modifier.clip(RoundedCornerShape(30.dp)),
                        onClick = {
//                            vm.open.value=true
                            vm.Send_Message = text
                            //                    listener.sendMessage(webSocket,text)
                            SendMessagesHelpingFunction(context, vm)
                            ChatWebSocket.sendMessage(vm.Send_Message)
                            //                vm.ChatList.add(MessagesDataClassItem("time",,vm.user_name,vm.Send_Message))
                            text = ""
                            //                    getMessages(context,vm,vm.chatId){
                            //                    }
                        }
                    ) {
                        Text(text = "Send")
                    }
                }
            }
        }
//        Text(text = "this is message response ${vm.result}")
    }
}



@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MyUI(vm:MainViewModel,onClickGotoChatScreen:()->Unit) {

    val contextForToast = LocalContext.current.applicationContext

    TopAppBar(
        title = {
            Row() {
                if(vm.istyping.value&&vm.user_name!=vm.istypinguser.value){
                    Text(text = "${vm.istypinguser.value} is typing")
                    vm.starttyping()
                }
                else {
                    LazyRow(
                        modifier = Modifier
//                        .height(20.dp)
                            .fillMaxWidth()
                    ) {
                        items(vm.ChatMembersList) {
                            if(it.person.username==vm.user_name){
                                Text(
                                    modifier = Modifier,
                                    text = "${ it.person.username} ",
                                    color=Color.Cyan
                                )
                            }
                            else
                                Text(text = "${ it.person.username } ")
                        }
                    }
                }
            }
//            Text(text = "Agent $istyping")

        },
        navigationIcon = {
            IconButton(onClick = {
                onClickGotoChatScreen()
                Toast.makeText(contextForToast, "Back Icon Clicked", Toast.LENGTH_SHORT)
                    .show()
            }) {
//                Icon(painter = painterResource(id = R.drawable.baseline_account_circle_24), contentDescription = "Go Back")
//                Spacer(modifier = Modifier.width(20.dp))
                Icon( imageVector = Icons.Filled.ArrowBack,contentDescription = "Go Back")
            }
        },


    )
}

fun String.toColor() = Color(android.graphics.Color.parseColor(this))