package com.example.sarisari.round3

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sarisari.bigStore


private val StoreColors = lightColorScheme(
    primary = Color(0xFF2E7D32),
    onPrimary = Color.White,
    secondary = Color(0xFFF9A825),
    surface = Color(0xFFFFFDE7)
)

@Composable
fun StoreApp() {
    val navController = rememberNavController()
    MaterialTheme(colorScheme = StoreColors) {
        NavHost(navController, startDestination = "list") {
            composable("list") {
                ItemListScreen(onItemClick = { id ->
                    navController.navigate("detail/$id")
                })
            }
            composable(
                route = "detail/{itemId}",
                arguments = listOf(navArgument("itemId") { type = NavType.IntType })
            ) { entry ->
                val id = entry.arguments?.getInt("itemId") ?: 0
                DetailScreen(id, onBack = { navController.popBackStack() })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemListScreen(onItemClick: (Int) -> Unit) {
    var selectedId by remember { mutableStateOf<Int?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sari-Sari Store") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        LazyColumn(Modifier.padding(padding)) {
            items(bigStore, key = { it.id }) { item ->
                val color by animateColorAsState(
                    if (selectedId == item.id) Color(0xFFC8E6C9) else Color.White,
                    label = "cardColor"
                )
                Card(
                    colors = CardDefaults.cardColors(containerColor = color),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            selectedId = item.id
                            onItemClick(item.id)
                        }
                ) {
                    Text("${item.name}   ₱${item.price}", Modifier.padding(16.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(id: Int, onBack: () -> Unit) {
    val item = bigStore.first { it.id == id }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Item Details") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding).padding(16.dp)) {
            Text(item.name, style = MaterialTheme.typography.headlineMedium)
            Text("Price: ₱${item.price}")
            Button(onClick = onBack) { Text("Back") }
        }
    }
}