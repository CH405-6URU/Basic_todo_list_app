package com.example.todolistapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


// 2nd class/file defined
// Data access object (DAO)
// Defines how we can access the data
// First we defined what kind of data we have to access now we define how to access it
@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Query("SELECT * from Todo WHERE id = :id") // :id is the parameter that will be passed into the function
    suspend fun getTodoById(id:Int): Todo? // nullable because if the ID does not exist the app would crash otherwise

    @Query("Select * FROM todo")
    fun getTodos(): Flow<List<Todo>>
}