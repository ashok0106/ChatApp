package com.example.chat_engine.Questions

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Question::class], version = 21)
abstract class QuestionDataBase : RoomDatabase() {

    abstract fun questionDao(): QuestionDao

    companion object {
        @Volatile
        private var instance: QuestionDataBase? = null

        fun getInstance(context: Context): QuestionDataBase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    QuestionDataBase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration().build().also { instance = it }
            }
        }
    }
}