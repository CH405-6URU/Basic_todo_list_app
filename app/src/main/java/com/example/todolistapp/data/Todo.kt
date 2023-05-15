package com.example.todolistapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey


// 1st class/file implemented

@Entity //Defines the data class as a table in the database
data class Todo(
    val title: String,
    val description: String?,
    val isDone: Boolean,
    // flag as nullable and set to null so it doesn't error when room has to generate a key
    @PrimaryKey val id: Int? = null
)
