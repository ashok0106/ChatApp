package com.example.chat_engine.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chat_engine.GetChatMembers.GetChatMembers
import com.example.chat_engine.IsTyping.IsTypingHelpingFunction
import com.example.chat_engine.ProgressBarIndicator.LoadingFile
import com.example.chat_engine.WebSocket.ChatWebSocket
import com.example.chat_engine.ViewModel.MainViewModel
import com.example.chat_engine.mychats.SendMessages.SendMessagesHelpingFunction
import kotlinx.coroutines.*


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun GetMessagesFunction(
    vm: MainViewModel,
    ChatWebSocket:ChatWebSocket,
    onClickGotoChatScreen:()->Unit
) {
    val context= LocalContext.current
    /*
    GetChatMembers() function gets the members in that particular chat
     */
    GetChatMembers(context = context,vm)
    /*
    to show the loader until
     */
    if(vm.open.value) {
        vm.startThread()
        LoadingFile()
    }

    Column(modifier = Modifier

    ) {

        /*
        A composable to show the topappbar
         */
        MyUI(vm,onClickGotoChatScreen)

        /*
        variables to store the messages of a
        particular chat
         */
        val messageListStateList=vm.messageList.collectAsState()
        val messageList=messageListStateList.value

        /*
        custom defined colors used to show in the background
         */
        val DifferentUserMessageColor = "#03DAC5".toColor()
        val LazyColumnBackgroundColor="#f5f7e1".toColor()


        val lazyListState= rememberLazyListState()
        /*
        We are showing the last values of the
        lazy column
        to avoid negative values it is called in the if statement
         */
        if(vm.ChatList.size>0){
            LazyColumn(
                state=lazyListState,
                modifier = Modifier
                    .fillMaxHeight(0.823f)
                    .background(LazyColumnBackgroundColor),
            ){
                itemsIndexed(vm.ChatList){items,it->
                    val time=it.created.substring(10,16)
                    if(it.sender_username!=vm.user_name){
                            ToShowDifferentUserMessages(it.text,time)

                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    else{
                        ToShowUserMessages(it.text,time)
                    }
                }
            }
            LaunchedEffect(vm.ChatList.size){
                lazyListState.scrollToItem(vm.ChatList.size-1)
            }
        }
        else{
            /*
            Called when there are no chats available
             */
            NoChatAvailable()

        }
        Card(
            modifier = Modifier
                .fillMaxHeight(1f)
                .fillMaxWidth()
                .navigationBarsPadding()
            ,
            backgroundColor = LazyColumnBackgroundColor
        ) {

            Box(
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp)
                    .fillMaxWidth()
            ) {

                /*
                Row to show textfield and a send button which sends
                the messages
                 */
                Row(modifier = Modifier
                ) {
                    var text by remember { mutableStateOf("") }
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(0.7f),
                        value = text,
                        onValueChange = { newText ->
                            text = newText
                            IsTypingHelpingFunction(context,vm)
                        },
                        placeholder = { Text(text = "Message") }
                    )
                    /*
                    Used to make the button visible when
                    there some text in it otherwise disable it
                     */
                    var flag by remember {
                        mutableStateOf(false)
                    }
                    flag = text != ""
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(
                        shape = CircleShape,
                        enabled = flag,
                        onClick = {
                            vm.Send_Message = text
                            SendMessagesHelpingFunction(context, vm)
                            ChatWebSocket.sendMessage(vm.Send_Message)
                            text = ""
                        }
                    ) {
                        Text(text = "Send")
                    }
                }
            }
        }
    }
}

/*
To show  user's messages
 */
@Composable
fun ToShowUserMessages(
    text:String,
    time:String
) {
    val temp = "#03DAC5".toColor()
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
                        text = text,
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
/*
To show different user's messages
 */
@Composable
fun ToShowDifferentUserMessages(
    text:String,
    time:String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
        , horizontalAlignment = Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .padding(start = 20.dp, top = 8.dp, end = 8.dp)

        ) {
            Card(
                modifier = Modifier
                    .padding(15.dp)
                    .clip(RoundedCornerShape(50.dp)),
                backgroundColor = Color.White
            ) {
                Column(
                )
                {
                    Text(
                        text = text,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(start = 5.dp, end = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = time,
                        modifier = Modifier
                            .padding(start = 5.dp, end = 8.dp),
                        fontSize = 10.sp
                    )
                }

            }
        }
    }
}
/*
To show when there are no chats available
 */
@Composable
fun NoChatAvailable() {
    val temp2="#f5f7e1".toColor()

    LazyColumn(
        modifier = Modifier
            .fillMaxHeight(0.8f)
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

/*
TopAppBar to store the usernames and show who is typing
 */
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

        },
        navigationIcon = {
            /*
            Icon to go back
             */
            IconButton(onClick = {
                onClickGotoChatScreen()
                Toast.makeText(contextForToast, "Back Icon Clicked", Toast.LENGTH_SHORT)
                    .show()
            }) {
                Icon( imageVector = Icons.Filled.ArrowBack,contentDescription = "Go Back")
            }
        }, modifier = Modifier.statusBarsPadding()


    )
}

/*
Function to change the hex code to the color
 */
fun String.toColor() = Color(android.graphics.Color.parseColor(this))