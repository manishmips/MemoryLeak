package com.manish.learning.leakcanarysample

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    var context: Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstActivityContent()
        }
        context = this
    }
    @Composable
    fun FirstActivityContent() {
        val context = LocalContext.current
        val startSecondActivity =
            rememberLauncherForActivityResult(StartSecondActivity()) { result ->
                // Handle the result if needed
            }

        Column {
            Text(text = stringResource(id = R.string.first_activity_text))
            Button(onClick = { startSecondActivity.launch(null) }) {
                Text(text = stringResource(id = R.string.launch_second_activity_button))
            }
        }
    }
    @Composable
    fun MyApp() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
                HomeScreen(navController)
            }
            composable("second") {
                SecondScreen()
            }
        }
    }

    @Composable
    fun HomeScreen(navController: NavController) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = "Home Screen")
            Button(onClick = { navController.navigate("second") }) {
                Text(text = "Go to Second Screen")
            }
        }
    }

    @Composable
    fun SecondScreen() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = "Second Screen")
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        MyApp()
    }
}
