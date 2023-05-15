package com.example.todolistapp.ui.add_edit_todo

// Events that can occur from within the AddEditTodo screen
sealed class AddEditTodoEvent {
    data class OnTitleChange(val title: String): AddEditTodoEvent()
    data class OnDescriptionChange(val description: String): AddEditTodoEvent()
    object OnSaveTodoClick: AddEditTodoEvent()
}
