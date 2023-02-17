package com.example.simpletodo.todoitem.presentation.todoitem.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

import com.example.simpletodo.R
import com.example.simpletodo.todoitem.domain.model.TodoItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoItem(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    cornerRadius: Dp = 8.dp,
    onDelete:() -> Unit,
    onClick:() -> Unit
) {
    Card(
        shape = RoundedCornerShape(cornerRadius),
        onClick = onClick
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            val (contentColumn, iconButton) = createRefs()

            Column (
                modifier = Modifier.constrainAs(contentColumn) {
                width = Dimension.fillToConstraints
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(iconButton.start)
            })
            {
                Text(text = title, color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = description, color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.bodySmall)
            }
            IconButton(
                onClick = { onDelete() },
                modifier = Modifier
                    .constrainAs(iconButton) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(id = R.string.delete_item),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val todoItem = TodoItem(1, "Test Title", "Test description", 1L)

    Surface(modifier = Modifier.padding(32.dp)) {

    }

}