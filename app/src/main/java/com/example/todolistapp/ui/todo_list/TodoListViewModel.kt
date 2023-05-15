package com.example.todolistapp.ui.todo_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolistapp.data.Todo
import com.example.todolistapp.data.TodoRepository
import com.example.todolistapp.util.Routes
import com.example.todolistapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(private val repository: TodoRepository) : ViewModel() {

    // getTodos returns a flow that will be converted into a state using .collectAsState in the UI composables
    val todos = repository.getTodos()

    // flows should always have a private mutable and public immutable version of a variable.
    // The mutable version is changed by the data layer behind the scenes while the immutable version is what the UI interacts with.
    // This way the UI cannot change the data layer but the data layer can change the UI.
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    // Will be used to cache the most recently deleted item in case it is undone. Then the stored data will be reinserted into the list
    private var deletedTodo: Todo? = null

    // The events were defined in the TodoListEvent so that we can have a clean way to define the events in the viewmodel without using a function for each one
    fun onEvent(event: TodoListEvent) {
        when (event) {
            is TodoListEvent.OnAddTodoClick -> {
                sendUiEvent(event = UiEvent.Navigate(Routes.ADD_EDIT_TODO))
            }
            is TodoListEvent.OnDeleteTodoClick -> {
                viewModelScope.launch {
                    deletedTodo = event.todo
                    repository.deleteTodo(event.todo)
                    sendUiEvent(UiEvent.ShowSnackBar(message = "Todo item deleted", action = "Undo"))
                }
            }
            is TodoListEvent.OnDoneChange -> {
                viewModelScope.launch {
                    repository.insertTodo(event.todo.copy(isDone = event.isDone))
                }
            }
            is TodoListEvent.OnTodoClick -> {
                sendUiEvent(event = UiEvent.Navigate(Routes.ADD_EDIT_TODO + "?todoId=${event.todo.id}"))
            }
            TodoListEvent.OnUndoDeleteClick -> {
                deletedTodo?.let { viewModelScope.launch { repository.insertTodo(it) } }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            // send is asynchronous and thus needs to be executed within a coroutine
            _uiEvent.send(event)
        }
    }
}