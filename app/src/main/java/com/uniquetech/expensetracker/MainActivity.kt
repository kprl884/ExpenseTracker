package com.uniquetech.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uniquetech.expensetracker.presentation.home.HomeScreen
import com.uniquetech.expensetracker.presentation.navigation.Screen
import com.uniquetech.expensetracker.ui.theme.ExpenseTrackerTheme
import androidx.compose.material3.Scaffold

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExpenseTrackerTheme {
                ExpenseTrackerApp()
            }
        }
    }
}

@Composable
fun ExpenseTrackerApp() {
    val navController = rememberNavController()
    
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToAddExpense = { navController.navigate(Screen.AddExpense.route) }
                )
            }
            // Diğer ekranlar için composable fonksiyonları burada tanımlanacak
        }
    }
}