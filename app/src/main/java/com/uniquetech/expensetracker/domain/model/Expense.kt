package com.uniquetech.expensetracker.domain.model

import java.time.LocalDateTime
import java.util.UUID

data class Expense(
    val id: String = UUID.randomUUID().toString(),
    val amount: Double,
    val description: String,
    val type: ExpenseType,
    val date: LocalDateTime = LocalDateTime.now(),
    val currencyCode: String = "USD"
)

enum class ExpenseType {
    FOOD,
    TRANSPORT,
    ENTERTAINMENT,
    SHOPPING,
    HEALTH,
    EDUCATION,
    BILLS,
    PETS,
    SALARY,
    GIFTS,
    COFFEE,
    SNACKS,
    OTHER
} 