package com.example.todolistapp.ui.todo_list

import com.example.todolistapp.data.Todo


// Defines specific events for the TodoListViewModel that can happen based upon what things are selectable by the user like
// deleting todos, adding todos, or editing todos
sealed class TodoListEvent {
    // if there are parameters then the thing is a data class and if no parameters its an object
    data class OnDeleteTodoClick(val todo: Todo): TodoListEvent()
    data class OnDoneChange(val todo: Todo, val isDone: Boolean): TodoListEvent()
    object OnUndoDeleteClick: TodoListEvent()
    data class OnTodoClick(val todo: Todo): TodoListEvent()
    object OnAddTodoClick: TodoListEvent()
}
