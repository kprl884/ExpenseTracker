package com.uniquetech.expensetracker.domain.repository

import com.uniquetech.expensetracker.domain.model.Expense
import com.uniquetech.expensetracker.domain.model.ExpenseFilter
import com.uniquetech.expensetracker.domain.model.TimeRange
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    fun getExpenses(filter: ExpenseFilter): Flow<List<Expense>>
    fun getExpensesByTimeRange(timeRange: TimeRange): Flow<List<Expense>>
    fun getTotalSpentByTimeRange(timeRange: TimeRange): Flow<Double>
    suspend fun addExpense(expense: Expense)
    suspend fun updateExpense(expense: Expense)
    suspend fun deleteExpense(expenseId: String)
    fun getExpenseById(expenseId: String): Flow<Expense?>
} 