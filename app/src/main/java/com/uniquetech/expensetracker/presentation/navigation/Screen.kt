package com.uniquetech.expensetracker.presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object AddExpense : Screen("add_expense")
    object Statistics : Screen("statistics")
    object Settings : Screen("settings")
} 