package com.example.simpletodo.todoitem.presentation.todoitem.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.simpletodo.R
import com.example.simpletodo.todoitem.UiText
import com.example.simpletodo.todoitem.domain.utils.TodoItemOrder
import com.example.simpletodo.todoitem.presentation.todoitem.TodoItemEvent

@Composable
fun DialogMenuButtonCheckable(
    modifier: Modifier = Modifier,
    onClick:() -> Unit,
    text: String,
    checked: Boolean = false,
) {
    Row {
        //Date sort button
        TextButton(
            modifier = Modifier.weight(1f),
            onClick = onClick
        ) {
            Text(
                text,
                textAlign = TextAlign.Start,
                modifier = Modifier.weight(1f)
            )
            if (checked) {
                Spacer(Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = stringResource(id = R.string.selected)
                )
            }
        }
    }
}