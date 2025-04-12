package com.uniquetech.expensetracker.data.sample

import com.uniquetech.expensetracker.domain.model.Expense
import com.uniquetech.expensetracker.domain.model.ExpenseType
import com.uniquetech.expensetracker.domain.repository.ExpenseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SampleDataProvider @Inject constructor(
    private val expenseRepository: ExpenseRepository
) {
    fun populateSampleData() {
        CoroutineScope(Dispatchers.IO).launch {
            // Bugünün harcamaları
            addExpense(
                amount = 3.35,
                description = "Treats",
                type = ExpenseType.PETS,
                date = LocalDateTime.now().minusHours(2)
            )
            addExpense(
                amount = 1.70,
                description = "Snacks",
                type = ExpenseType.SNACKS,
                date = LocalDateTime.now().minusHours(3)
            )
            addExpense(
                amount = 2.19,
                description = "Coffee",
                type = ExpenseType.COFFEE,
                date = LocalDateTime.now().minusHours(4)
            )
            
            // Dünün harcamaları
            addExpense(
                amount = 39.75,
                description = "Jeff's birthday",
                type = ExpenseType.GIFTS,
                date = LocalDateTime.now().minusDays(1).minusHours(5)
            )
            
            // Daha eski harcamalar
            addExpense(
                amount = 2300.00,
                description = "Monthly salary",
                type = ExpenseType.SALARY,
                date = LocalDateTime.now().minusDays(3)
            )
            addExpense(
                amount = 125.40,
                description = "Grocery shopping",
                type = ExpenseType.FOOD,
                date = LocalDateTime.now().minusDays(4)
            )
            addExpense(
                amount = 54.99,
                description = "Internet bill",
                type = ExpenseType.BILLS,
                date = LocalDateTime.now().minusDays(5)
            )
            addExpense(
                amount = 18.50,
                description = "Movie tickets",
                type = ExpenseType.ENTERTAINMENT,
                date = LocalDateTime.now().minusDays(6)
            )
        }
    }
    
    private suspend fun addExpense(
        amount: Double,
        description: String,
        type: ExpenseType,
        date: LocalDateTime
    ) {
        val expense = Expense(
            amount = amount,
            description = description,
            type = type,
            date = date
        )
        expenseRepository.addExpense(expense)
    }
} 