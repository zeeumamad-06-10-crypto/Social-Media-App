package com.example.socialmidiaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Social Media App") }) },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { navController.navigate("upload") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                ) { Text("Upload File") }

                Button(
                    onClick = { navController.navigate("download") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                ) { Text("Download Files") }
            }
        }
    )
}
