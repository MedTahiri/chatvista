package com.mohamed.tahiri.android.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun MyTextField(name: MutableState<String>, s: String, s1: String, default: KeyboardOptions) {
    var isNotShow = remember {
        mutableStateOf(true)
    }
    val visual =
        if (default == KeyboardOptions(keyboardType = KeyboardType.Password) && isNotShow.value) PasswordVisualTransformation() else VisualTransformation.None
    TextField(
        value = name.value,
        onValueChange = { name.value = it },
        label = { Text(text = s) },
        placeholder = { Text(s1) },
        singleLine = true,
        keyboardOptions = default,
        visualTransformation = visual,
        trailingIcon = {
            if (default == KeyboardOptions(keyboardType = KeyboardType.Password)) {
                IconButton(onClick = {
                    isNotShow.value = !isNotShow.value
                }) {
                    Icon(
                        imageVector = if (isNotShow.value) Icons.Default.Close else Icons.Default.Check,
                        contentDescription = ""
                    )
                }
            }
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            disabledContainerColor = MaterialTheme.colorScheme.background,
        ),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(1f)
    )
}