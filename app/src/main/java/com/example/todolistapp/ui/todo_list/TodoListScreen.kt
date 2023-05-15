package com.example.todolistapp.ui.todo_list

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todolistapp.util.UiEvent
import kotlinx.coroutines.flow.collect

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TodoListScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    // Uses the injector defined in the TodoListViewmodel to inject the viewmodel into this UI layer
    viewModel: TodoListViewModel = hiltViewModel()
) {
    // todos fed in as a flow from the viewmodel which gets them from the repository and converts
    // the flow to a state to be displayed in UI
    val todos = viewModel.todos.collectAsState(initial = emptyList())
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {
            when (it) {
                is UiEvent.Navigate -> onNavigate(it)
                is UiEvent.ShowSnackBar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = it.message,
                        actionLabel = it.action
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(TodoListEvent.OnUndoDeleteClick)
                    }
                }
                // The PopBackStack event cannot occur on this screen but if its not included in
                // the when statement you must include an else block. By returning unit we are
                // essentially telling android to do nothing.
                else -> Unit
            }
        }
    }
    Scaffold(scaffoldState = scaffoldState, floatingActionButton = {
        FloatingActionButton(onClick = { viewModel.onEvent(TodoListEvent.OnAddTodoClick) }
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
        }
    }
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(todos.value) { todo ->
                // onEvent propogates up because the events are being handled by the viewmodel
                TodoItem(
                    todo = todo,
                    onEvent = viewModel::onEvent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.onEvent(TodoListEvent.OnTodoClick(todo)) }
                        .padding(16.dp))
            }
        }
    }
}
