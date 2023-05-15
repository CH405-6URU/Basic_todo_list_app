package com.example.todolistapp.util

// defines broader events for the TodoListViewModel that can happen within the UI
sealed class UiEvent {
    object PopBackStack : UiEvent()
    data class Navigate(val route: String) : UiEvent()
    data class ShowSnackBar(val message: String, val action: String? = null) : UiEvent()
}
