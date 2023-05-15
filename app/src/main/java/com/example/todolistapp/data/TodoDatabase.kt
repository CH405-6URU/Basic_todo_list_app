package com.example.todolistapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

// 3rd class implemented

@Database(
    // pass in an array of the To-do entity that was created as a class
    entities = [Todo::class],
    // version will be updated automatically when things are migrated or something major changes. Not really important now...
    version = 1
)
// abstract class will be implemented by Room
abstract class TodoDatabase: RoomDatabase()  {
    // abstract val will be turned into a val by Room with dagger hilt (AppModule under provideTodoRepository class)
    abstract val dao: TodoDao
}