package com.uniquetech.expensetracker.data.repository

import com.uniquetech.expensetracker.data.local.ExpenseDao
import com.uniquetech.expensetracker.data.local.ExpenseEntity
import com.uniquetech.expensetracker.domain.model.Expense
import com.uniquetech.expensetracker.domain.model.ExpenseFilter
import com.uniquetech.expensetracker.domain.model.TimeRange
import com.uniquetech.expensetracker.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

class ExpenseRepositoryImpl @Inject constructor(
    private val expenseDao: ExpenseDao
) : ExpenseRepository {

    override fun getExpenses(filter: ExpenseFilter): Flow<List<Expense>> {
        // Filtreleme işlemini room sorguları ile yapıyoruz
        val startDate = when (filter.timeRange) {
            is TimeRange.Daily -> startOfDay(LocalDate.now())
            is TimeRange.Weekly -> startOfDay(LocalDate.now().minusDays(6))
            is TimeRange.Monthly -> startOfDay(LocalDate.now().minusDays(29))
            is TimeRange.Custom -> startOfDay(filter.timeRange.startDate)
        }
        
        val endDate = when (filter.timeRange) {
            is TimeRange.Daily -> endOfDay(LocalDate.now())
            is TimeRange.Weekly -> endOfDay(LocalDate.now())
            is TimeRange.Monthly -> endOfDay(LocalDate.now())
            is TimeRange.Custom -> endOfDay(filter.timeRange.endDate)
        }
        
        return expenseDao.getExpensesBetweenDates(startDate, endDate)
            .map { entities -> entities.map { it.toDomain() } }
    }

    override fun getExpensesByTimeRange(timeRange: TimeRange): Flow<List<Expense>> {
        val startDate = when (timeRange) {
            is TimeRange.Daily -> startOfDay(LocalDate.now())
            is TimeRange.Weekly -> startOfDay(LocalDate.now().minusDays(6))
            is TimeRange.Monthly -> startOfDay(LocalDate.now().minusDays(29))
            is TimeRange.Custom -> startOfDay(timeRange.startDate)
        }
        
        val endDate = when (timeRange) {
            is TimeRange.Daily -> endOfDay(LocalDate.now())
            is TimeRange.Weekly -> endOfDay(LocalDate.now())
            is TimeRange.Monthly -> endOfDay(LocalDate.now())
            is TimeRange.Custom -> endOfDay(timeRange.endDate)
        }
        
        return expenseDao.getExpensesBetweenDates(startDate, endDate)
            .map { entities -> entities.map { it.toDomain() } }
    }

    override fun getTotalSpentByTimeRange(timeRange: TimeRange): Flow<Double> {
        val startDate = when (timeRange) {
            is TimeRange.Daily -> startOfDay(LocalDate.now())
            is TimeRange.Weekly -> startOfDay(LocalDate.now().minusDays(6))
            is TimeRange.Monthly -> startOfDay(LocalDate.now().minusDays(29))
            is TimeRange.Custom -> startOfDay(timeRange.startDate)
        }
        
        val endDate = when (timeRange) {
            is TimeRange.Daily -> endOfDay(LocalDate.now())
            is TimeRange.Weekly -> endOfDay(LocalDate.now())
            is TimeRange.Monthly -> endOfDay(LocalDate.now())
            is TimeRange.Custom -> endOfDay(timeRange.endDate)
        }
        
        return expenseDao.getTotalSpentBetweenDates(startDate, endDate)
            .map { it ?: 0.0 }
    }

    override suspend fun addExpense(expense: Expense) {
        expenseDao.insertExpense(ExpenseEntity.fromDomain(expense))
    }

    override suspend fun updateExpense(expense: Expense) {
        expenseDao.updateExpense(ExpenseEntity.fromDomain(expense))
    }

    override suspend fun deleteExpense(expenseId: String) {
        expenseDao.deleteExpenseById(expenseId)
    }

    override fun getExpenseById(expenseId: String): Flow<Expense?> {
        return expenseDao.getExpenseById(expenseId)
            .map { it?.toDomain() }
    }
    
    // Yardımcı fonksiyonlar
    private fun startOfDay(date: LocalDate): LocalDateTime {
        return LocalDateTime.of(date, LocalTime.MIN)
    }
    
    private fun endOfDay(date: LocalDate): LocalDateTime {
        return LocalDateTime.of(date, LocalTime.MAX)
    }
} 