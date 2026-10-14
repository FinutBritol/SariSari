package com.example.sarisari.round2

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CartCounter() {
    var count by remember { mutableStateOf(0) }
    Column(Modifier.padding(16.dp)) {
        Text("Items in cart: $count")
        Button(onClick = { count++ }) { Text("Add") }
    }
}

@Composable
fun StoreNameField(name: String, onNameChange: (String) -> Unit) {
    TextField(
        value = name,
        onValueChange = onNameChange,
        label = { Text("Store name") }
    )
}

@Composable
fun StoreHeader() {
    var name by remember { mutableStateOf("") }
    Column(Modifier.padding(16.dp)) {
        Text("Welcome to $name's store!")
        StoreNameField(name = name, onNameChange = { name = it })
    }
}