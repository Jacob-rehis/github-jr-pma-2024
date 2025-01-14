package com.example.mycarschool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.mycarschool.ui.theme.MyCarSchoolTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyCarSchoolTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("order_drive") { OrderDriveScreen() }
        composable("theory_session") { TheorySessionScreen() }
        composable("submit_application") { SubmitApplicationScreen() }
    }
}

@Composable
fun HomeScreen(navController: androidx.navigation.NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { navController.navigate("order_drive") }) {
            Text(text = "Objednat jízdu")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("theory_session") }) {
            Text(text = "Přihlásit se na teoretickou hodinu")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("submit_application") }) {
            Text(text = "Podat přihlášku")
        }
    }
}

@Composable
fun OrderDriveScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Stránka: Objednat jízdu")
    }
}

@Composable
fun TheorySessionScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Stránka: Přihlášení na teoretickou hodinu")
    }
}

@Composable
fun SubmitApplicationScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Stránka: Podat přihlášku")
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MyCarSchoolTheme {
        HomeScreen(rememberNavController())
    }
}