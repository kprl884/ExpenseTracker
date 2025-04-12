package com.uniquetech.expensetracker.domain.model

data class HomeState(
    val currentTimeRange: TimeRange = TimeRange.Daily,
    val totalSpent: Double = 0.0,
    val expenses: List<Expense> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val currencyCode: String = "USD"
) 