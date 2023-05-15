package com.example.todolistapp.ui.todo_list

import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolistapp.data.Todo


// Child composable that will be used to display a single item, hence TodoItem
@Composable
fun TodoItem(
    todo: Todo,
    // onEvent parameter allows us to make all of the events that can happen simple because it is referencing the TodoListEvent file
    onEvent: (TodoListEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = todo.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = { onEvent(TodoListEvent.OnDeleteTodoClick(todo)) }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                }
            }
            // More info about .let https://kotlinlang.org/docs/scope-functions.html#return-value
            todo.description?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = it)
            }
        }
        Checkbox(
            checked = todo.isDone,
            // changes the onDone state to reflect whether or not the box is checked
            onCheckedChange = { onEvent(TodoListEvent.OnDoneChange(todo, it)) })
    }
}