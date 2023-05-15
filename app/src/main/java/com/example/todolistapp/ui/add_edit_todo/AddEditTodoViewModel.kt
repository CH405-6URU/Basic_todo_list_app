package com.example.todolistapp.ui.add_edit_todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolistapp.data.Todo
import com.example.todolistapp.data.TodoRepository
import com.example.todolistapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTodoViewModel @Inject constructor(
    private val repository: TodoRepository,
    // savedStateHandle involved with navigating to the Add/Edit screen
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    // is nullable value because we will be both editing old todos and creating new ones which will not have any data in them.
    var todo by mutableStateOf<Todo?>(null)
        // ensures that the values can only be changed by the viewmodel but can be read from anywhere.
        private set
    var title by mutableStateOf("")
        private set
    var description by mutableStateOf("")
        private set

    // Same as TodoListViewModel
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        // todoId comes from the OnTodoClick event under the onEvent function in TodoListViewModel
        val todoId = savedStateHandle.get<Int>("todoId")!!
        if (todoId != -1) {
            viewModelScope.launch {
                repository.getTodoById(todoId)?.let {
                    title = it.title
                    description = it.description ?: ""
                    this@AddEditTodoViewModel.todo = it
                }
            }
        }
    }

    fun onEvent(event: AddEditTodoEvent) {
        when (event) {
            is AddEditTodoEvent.OnDescriptionChange -> description = event.description
            is AddEditTodoEvent.OnTitleChange -> title = event.title
            is AddEditTodoEvent.OnSaveTodoClick -> {
                viewModelScope.launch {
                    if (title.isBlank()) {
                        sendUiEvent(
                            UiEvent.ShowSnackBar(
                                message = "The title cannot be empty"
                            )
                        )
                        return@launch
                    }
                    repository.insertTodo(
                        Todo(title = title, description = description, isDone = todo?.isDone ?: false, id = todo?.id)
                    )
                    sendUiEvent(UiEvent.PopBackStack)
                }
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