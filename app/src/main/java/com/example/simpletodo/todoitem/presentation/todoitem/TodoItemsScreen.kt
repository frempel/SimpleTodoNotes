package com.example.simpletodo.todoitem.presentation.todoitem

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.BottomAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.simpletodo.R
import com.example.simpletodo.todoitem.UiText
import com.example.simpletodo.todoitem.domain.utils.OrderDirection
import com.example.simpletodo.todoitem.domain.utils.TodoItemOrder
import com.example.simpletodo.todoitem.navigation.Screen
import com.example.simpletodo.todoitem.presentation.todoitem.components.DialogMenuButtonCheckable
import com.example.simpletodo.todoitem.presentation.todoitem.components.TodoItem
import com.example.simpletodo.ui.theme.Shapes
import dev.olshevski.navigation.reimagined.NavController
import dev.olshevski.navigation.reimagined.hilt.hiltViewModel
import dev.olshevski.navigation.reimagined.navigate
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoItemsScreen(
    navController: NavController<Screen>,
    viewModel: TodoItemViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val isSearchBarVisible = viewModel.isSearchBarVisible.value
    val snackbarHostState = remember { SnackbarHostState() }
    val openDialogState = rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val searchQuery = viewModel.searchQuery.value

    BackHandler(enabled = isSearchBarVisible) {
        viewModel.onEvent(TodoItemEvent.HideSearchBar)
        viewModel.onEvent(TodoItemEvent.SearchTodoItems(""))
    }

    Scaffold(
        modifier = Modifier,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            BottomAppBar(
                actions = {
                    if (!isSearchBarVisible) {
                        //Search button
                        IconButton(onClick = {
                            viewModel.onEvent(TodoItemEvent.ShowSearchBar)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = stringResource(id = R.string.search)
                            )
                        }

                        //Sort button
                        IconButton(onClick = { openDialogState.value = true }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_sort_24),
                                contentDescription = stringResource(id = R.string.sort)
                            )
                        }

                        //Sort order toggle button
                        IconButton(onClick = {
                            viewModel.onEvent(TodoItemEvent.Order(state.todoItemOrder.oppositeDirection()))
                        }) {
                            Icon(
                                painter =
                                if (state.todoItemOrder.orderDirection is OrderDirection.Descending) {
                                    painterResource(id = R.drawable.ic_baseline_arrow_drop_down_24)
                                } else {
                                    painterResource(id = R.drawable.ic_baseline_arrow_drop_up_24)
                                },
                                contentDescription = stringResource(id = R.string.sort_direction)
                            )
                        }
                    }

                    if (isSearchBarVisible) {
                        //Search back button
                        IconButton(onClick = {
                            viewModel.onEvent(TodoItemEvent.HideSearchBar)
                            viewModel.onEvent(TodoItemEvent.SearchTodoItems(""))
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = stringResource(id = R.string.back)
                            )
                        }

                            OutlinedTextField(
                                value = searchQuery,
                                onValueChange = { viewModel.onEvent(TodoItemEvent.SearchTodoItems(it)) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, end = 16.dp),
                                singleLine = true,
                            )
                        }
                },
                floatingActionButton = {
                    if (!isSearchBarVisible) {
                        FloatingActionButton(
                            onClick = {
                                navController.navigate(Screen.AddEditTodoItemScreen(null))
                            },
                            containerColor = MaterialTheme.colorScheme.primary,
                            shape = Shapes.large,
                            elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = stringResource(id = R.string.add_item)
                            )
                        }
                    }
                })
        },
    ) {
        paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            LazyColumn(
                contentPadding = PaddingValues(top = 16.dp, start = 16.dp, end = 16.dp)
            ) {
                items(state.todoItems) {
                        todoItem ->
                    TodoItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { },
                        title = todoItem.title,
                        description = todoItem.description,
                        onDelete = {
                            viewModel.onEvent(TodoItemEvent.DeleteTodoItem(todoItem))
                            scope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = UiText.StringResource(resId = R.string.item_deleted).asString(context),
                                    actionLabel = context.getString(R.string.undo),
                                    duration = SnackbarDuration.Long
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(TodoItemEvent.RestoreTodoItem)
                                }
                            }
                        },
                        onClick = {
                            navController.navigate(Screen.AddEditTodoItemScreen(todoItem.id))
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            Spacer(modifier = Modifier.height(64.dp))
        }

        if (openDialogState.value) {
            Dialog(
                onDismissRequest = { openDialogState.value = false },
            ) {
                Card (
                    shape = Shapes.large
                ) {
                    Box(modifier = Modifier.padding(16.dp)) {
                        Column {
                            //Title
                            Text(text = UiText.StringResource(resId = R.string.sort).asString())
                            Spacer(modifier = Modifier.height(8.dp))

                            //Title sort button
                            DialogMenuButtonCheckable(
                                onClick = {
                                    viewModel.onEvent(
                                        TodoItemEvent.Order(
                                            TodoItemOrder.Title(
                                                state.todoItemOrder.orderDirection
                                            )
                                        ))
                                    openDialogState.value = false
                                },
                                text = UiText.StringResource(resId = R.string.title).asString(),
                                checked = state.todoItemOrder is TodoItemOrder.Title
                            )

                            //Description sort button
                            DialogMenuButtonCheckable(
                                onClick = {
                                    viewModel.onEvent(
                                        TodoItemEvent.Order(
                                            TodoItemOrder.Description(
                                                state.todoItemOrder.orderDirection
                                            )
                                        ))
                                    openDialogState.value = false
                                },
                                text = UiText.StringResource(resId = R.string.description).asString(),
                                checked = state.todoItemOrder is TodoItemOrder.Description
                            )

                            //Date sort button
                            DialogMenuButtonCheckable(
                                onClick = {
                                    viewModel.onEvent(
                                        TodoItemEvent.Order(
                                            TodoItemOrder.Timestamp(
                                                state.todoItemOrder.orderDirection
                                            )
                                        ))
                                    openDialogState.value = false
                                },
                                text = UiText.StringResource(resId = R.string.date).asString(),
                                checked = state.todoItemOrder is TodoItemOrder.Timestamp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun TodoItemsScreenPreview() {
    Surface {
        //TodoItemsPreviewScreen()
    }
}