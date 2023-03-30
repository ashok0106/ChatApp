package com.example.chat_engine.Questions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun QuestionList(questionViewModel: QuestionViewModel) {
    val expandedQuestion = remember { mutableStateOf<Question?>(null) }

    val questions by questionViewModel.questions.observeAsState(emptyList())

//    Text(text = "hiiii")

    LazyColumn(
        modifier = Modifier.padding(16.dp)
    ) {
        items(questions) { question ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { expandedQuestion.value = question },
                elevation = 8.dp
            ) {
                Text(
                    text = question.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                )
            }
//            }

            if (expandedQuestion.value == question) {
                question.subQuestions.forEach { subQuestion ->
                    SubQuestion(
                        subQuestion = subQuestion,
                        onSubQuestionClick = { expandedQuestion.value = question }
                    )
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        questionViewModel.loadQuestions()
    }
}

@Composable
fun SubQuestion(
    subQuestion: SubQuestion,
    onSubQuestionClick: () -> Unit
) {
    var expandedSubQuestion by remember { mutableStateOf(false) }

//Text(text = subQuestion.subQuestions.toString())
    if(subQuestion.subQuestions?.isEmpty() == true){
        Row() {
            Icon(Icons.Default.ArrowForward, contentDescription = "Solution")
            Text(
                text = subQuestion.question,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
        }
    }
    else {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp)
                .clickable {
                    expandedSubQuestion = !expandedSubQuestion
                    onSubQuestionClick()
                },
            elevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = subQuestion.question,
                    fontStyle = FontStyle.Italic,
                    fontSize = 16.sp,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                if (expandedSubQuestion) {
                    subQuestion.subQuestions?.forEach { nestedSubQuestion ->
                        SubQuestion(
                            subQuestion = nestedSubQuestion,
                            onSubQuestionClick = onSubQuestionClick
                        )
                    }
                }
            }
        }
    }
}