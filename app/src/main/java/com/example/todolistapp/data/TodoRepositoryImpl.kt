package com.example.todolistapp.data

import kotlinx.coroutines.flow.Flow
//5th class implemented and last one for boilerplate dao stuff.

// I do not understand why all the commands from the Dao need to be overridden...
// Supposed to be important for when things get more complicated.
// Maybe so its easier to read instead of sifting through the code?

class TodoRepositoryImpl(private val dao: TodoDao): TodoRepository {
    override suspend fun insertTodo(todo: Todo) {
        dao.insertTodo(todo)
    }

    override suspend fun deleteTodo(todo: Todo) {
        dao.deleteTodo(todo)
    }

    override suspend fun getTodoById(id: Int): Todo? {
        return dao.getTodoById(id)
    }

    override fun getTodos(): Flow<List<Todo>> {
        return dao.getTodos()
    }
}