package com.example.todolistapp.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todolistapp.data.TodoDatabase
import com.example.todolistapp.data.TodoRepository
import com.example.todolistapp.data.TodoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


// 7th thing implemented
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // defines how dagger can create our room database
    @Provides
    @Singleton
    fun provideTodoDatabase(app: Application): TodoDatabase {
        return Room.databaseBuilder(app, TodoDatabase::class.java, "todo_db").build()
    }

    @Provides
    @Singleton
    fun provideTodoRepository(db: TodoDatabase): TodoRepository {
        return TodoRepositoryImpl(db.dao)
    }

}