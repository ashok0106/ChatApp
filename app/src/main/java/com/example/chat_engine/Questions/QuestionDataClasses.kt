package com.example.chat_engine.Questions

import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


@Entity(tableName = "questions")
@TypeConverters(SubQuestionConverter::class)
data class Question(
    @PrimaryKey val id: Int,
    val title: String,
    val subQuestions: List<SubQuestion>
)

data class SubQuestion(
    val id: Int,
    val question: String,
    val subQuestions: List<SubQuestion>? = null,// Add a list of sub-sub-questions
    val nestedSubQuestions: List<SubQuestion>? = null
)

class SubQuestionConverter {
    @TypeConverter
    fun fromSubQuestionList(subQuestions: List<SubQuestion>): String {
        val gson = Gson()
        return gson.toJson(subQuestions)
    }

    @TypeConverter
    fun toSubQuestionList(subQuestionsString: String): List<SubQuestion> {
        val gson = Gson()
        val type = object : TypeToken<List<SubQuestion>>() {}.type
        return gson.fromJson(subQuestionsString, type)
    }
}