package com.example.todolistapp.data


import kotlinx.coroutines.flow.Flow

// 4th class implemented
// serves as an abstraction of the database and the functions for testing and makes changing code easier.
interface TodoRepository {


    suspend fun insertTodo(todo: Todo)


    suspend fun deleteTodo(todo: Todo)


    suspend fun getTodoById(id:Int): Todo? // nullable because if the ID does not exist the app would crash otherwise


    fun getTodos(): Flow<List<Todo>>

}