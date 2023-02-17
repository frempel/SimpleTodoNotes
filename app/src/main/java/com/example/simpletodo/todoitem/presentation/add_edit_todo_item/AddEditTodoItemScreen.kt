package com.example.simpletodo.todoitem.presentation.add_edit_todo_item

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.core.os.bundleOf
import com.example.simpletodo.R
import com.example.simpletodo.todoitem.UiText
import com.example.simpletodo.todoitem.navigation.Screen
import com.example.simpletodo.ui.theme.Shapes
import dev.olshevski.navigation.reimagined.NavController
import dev.olshevski.navigation.reimagined.hilt.hiltViewModel
import dev.olshevski.navigation.reimagined.pop
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTodoItemScreen(
    navController: NavController<Screen>,
    todoItemId: Int?,
    modifier: Modifier = Modifier,
    viewModel: AddEditTodoItemViewModel = hiltViewModel(defaultArguments = bundleOf(AddEditTodoItemViewModel.NAV_ARG_TODO_ITEM_ID to todoItemId))
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current


    val titleFocusRequester = remember { FocusRequester() }
    val descriptionFocusRequester = remember { FocusRequester() }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is AddEditTodoItemViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message.asString(context),
                        duration = SnackbarDuration.Short
                    )
                }
                is AddEditTodoItemViewModel.UiEvent.SaveTodoItem -> {
                    navController.pop()
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddEditTodoItemEvent.SaveTodoItem)
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = UiText.StringResource(resId = R.string.item_saved).asString(context),
                        )
                    }
                },
                containerColor = MaterialTheme.colorScheme.primary,
                shape = Shapes.large
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_baseline_save_alt_24), contentDescription = stringResource(id = R.string.save_item))
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.widthIn(0.dp, 300.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .widthIn(0.dp, 300.dp)
                                .padding(8.dp))
                        {
                            Text(
                                stringResource(id = R.string.add_item),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            //Title field
                            OutlinedTextField(
                                value = viewModel.title.value.text,
                                onValueChange = { viewModel.onEvent(AddEditTodoItemEvent.EnteredTitle(it)) },
                                textStyle = MaterialTheme.typography.bodyMedium,
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    textColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                ),
                                singleLine = true,
                                label = { Text(stringResource(id = R.string.title)) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .widthIn(0.dp, 300.dp)
                                    .focusRequester(titleFocusRequester)
                                    .onFocusChanged {
                                        viewModel.onEvent(AddEditTodoItemEvent.TitleChangedFocus(it))
                                        //viewModel.title.value.isFocused = true
                                    }
                            )
                            /*
                            LaunchedEffect(key1 = viewModel.title.value.isFocused) {
                                if (viewModel.title.value.isFocused) {
                                    titleFocusRequester.requestFocus()
                                }
                            }

                             */

                            Spacer(modifier = Modifier.height(16.dp))
                            
                            //Description field
                            OutlinedTextField(
                                value = viewModel.description.value.text,
                                onValueChange = { viewModel.onEvent(AddEditTodoItemEvent.EnteredDescription(it)) },
                                textStyle = MaterialTheme.typography.bodyMedium,
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    textColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                ),
                                label = { Text(stringResource(id = R.string.description)) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .widthIn(0.dp, 300.dp)
                                    .focusRequester(descriptionFocusRequester)
                                    .onFocusChanged {
                                        viewModel.onEvent(
                                            AddEditTodoItemEvent.DescriptionChangedFocus(it)
                                        )
                                    }
                            )
                            /*
                            LaunchedEffect(key1 = viewModel.description.value.isFocused) {
                                if (viewModel.description.value.isFocused) {
                                    descriptionFocusRequester.requestFocus()
                                }
                            }*/

                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
@Preview
fun AddEditTodoItemScreenPreview() {
    //AddEditTodoItemScreen(rememberNavController(startDestination = Screen.TodoItemsScreen))
}