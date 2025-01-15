package com.mohamed.tahiri.android.view.HomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mohamed.tahiri.android.Screen
import com.mohamed.tahiri.android.model.User
import com.mohamed.tahiri.android.ui.theme.AndroidTheme
import com.mohamed.tahiri.android.viewmodel.ApiState
import com.mohamed.tahiri.android.viewmodel.UserViewModel

@Composable
fun HomeScreen(navController: NavHostController, userViewModel: UserViewModel) {
    val usersState = userViewModel.users.value

    LaunchedEffect(Unit) {
        userViewModel.fetchUsers()
    }

    val switch = remember {
        mutableStateOf("Descussions")
    }
    val searchInContact = remember {
        mutableStateOf("")
    }
    val searchInMessage = remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
            if (switch.value == "Descussions") {
                Column(modifier = Modifier.padding(8.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Descussions",
                            fontSize = MaterialTheme.typography.titleLarge.fontSize.times(1.5),
                            fontWeight = FontWeight.Bold
                        )
                        IconButton(
                            onClick = { navController.navigate(Screen.ProfileScreen.name) },
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "",
                                modifier = Modifier.size(48.dp)
                            )
                        }
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = searchInMessage.value,
                            onValueChange = { searchInMessage.value = it },
                            leadingIcon = {
                                Icon(imageVector = Icons.Default.Search, contentDescription = "")
                            }, placeholder = {
                                Text(text = "Search for messages")
                            },
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
                    }
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(10) {
                            Card(modifier = Modifier
                                .padding(8.dp)
                                .clickable {
                                    navController.navigate(Screen.MessagingScreen.name)
                                }) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AccountCircle,
                                        contentDescription = "",
                                        modifier = Modifier.size(65.dp)
                                    )
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalAlignment = Alignment.Start,
                                        verticalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(text = "Mohamed Tahiri")
                                        Text(text = "hi bro \uD83D\uDC4B")
                                    }
                                }
                            }

                        }
                    }
                }

            } else {

                Column(modifier = Modifier.padding(8.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Contacts",
                            fontSize = MaterialTheme.typography.titleLarge.fontSize.times(1.5),
                            fontWeight = FontWeight.Bold
                        )
                        IconButton(
                            onClick = { navController.navigate(Screen.ProfileScreen.name) },
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "",
                                modifier = Modifier.size(48.dp)
                            )
                        }
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = searchInContact.value,
                            onValueChange = { searchInContact.value = it },
                            leadingIcon = {
                                Icon(imageVector = Icons.Default.Search, contentDescription = "")
                            }, placeholder = {
                                Text(text = "Search for contacts")
                            },
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
                    }
                    Box {
                        when (usersState) {
                            is ApiState.Loading -> {
                                // Show loading indicator
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                )
                            }

                            is ApiState.Success<*> -> {
                                val users = (usersState as ApiState.Success<List<User>>).data
                                // Display the fetched data using Jetpack Compose components
                                Column {
                                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                                        items(items = users, itemContent = { user ->
                                            Card(modifier = Modifier
                                                .padding(8.dp)
                                                .clickable {
                                                    navController.navigate(Screen.MessagingScreen.name)
                                                }) {
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(8.dp),
                                                    horizontalArrangement = Arrangement.Start,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.AccountCircle,
                                                        contentDescription = "",
                                                        modifier = Modifier.size(65.dp)
                                                    )
                                                    Column(
                                                        modifier = Modifier,
                                                        horizontalAlignment = Alignment.Start,
                                                        verticalArrangement = Arrangement.Center
                                                    ) {
                                                        Text(text = user.fullName)
                                                    }
                                                }
                                            }
                                        })
                                    }
                                }
                            }

                            is ApiState.Error -> {
                                val error = (usersState as ApiState.Error).message
                                // Show error message
                                Text(
                                    text = "Failed to fetch data: $error",
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth()
                                        .align(Alignment.Center)
                                )
                            }
                        }
                    }

                }


            }

            BottomAppBar {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .clickable { switch.value = "Descussions" }
                            .fillMaxSize()
                            .weight(1f, false)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle, contentDescription = "",
                            tint = if (switch.value == "Descussions") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "Descussions",
                            color = if (switch.value == "Descussions") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .clickable { switch.value = "Contacts" }
                            .fillMaxSize()
                            .weight(1f, false)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "",
                            tint = if (switch.value != "Descussions") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                        )

                        Text(
                            text = "Contacts",
                            color = if (switch.value != "Descussions") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun Preview() {
    AndroidTheme {

    }
}