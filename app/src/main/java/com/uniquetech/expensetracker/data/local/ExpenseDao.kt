package com.uniquetech.expensetracker.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface ExpenseDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: ExpenseEntity)
    
    @Update
    suspend fun updateExpense(expense: ExpenseEntity)
    
    @Delete
    suspend fun deleteExpense(expense: ExpenseEntity)
    
    @Query("DELETE FROM expenses WHERE id = :expenseId")
    suspend fun deleteExpenseById(expenseId: String)
    
    @Query("SELECT * FROM expenses WHERE id = :expenseId")
    fun getExpenseById(expenseId: String): Flow<ExpenseEntity?>
    
    @Query("SELECT * FROM expenses ORDER BY date DESC")
    fun getAllExpenses(): Flow<List<ExpenseEntity>>
    
    @Query("SELECT * FROM expenses WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getExpensesBetweenDates(startDate: LocalDateTime, endDate: LocalDateTime): Flow<List<ExpenseEntity>>
    
    @Query("SELECT * FROM expenses WHERE date >= :startDate ORDER BY date DESC")
    fun getExpensesFromDate(startDate: LocalDateTime): Flow<List<ExpenseEntity>>
    
    @Query("SELECT SUM(amount) FROM expenses WHERE type != 'SALARY' AND date BETWEEN :startDate AND :endDate")
    fun getTotalSpentBetweenDates(startDate: LocalDateTime, endDate: LocalDateTime): Flow<Double?>
    
    @Query("SELECT SUM(amount) FROM expenses WHERE type != 'SALARY' AND date >= :startDate")
    fun getTotalSpentFromDate(startDate: LocalDateTime): Flow<Double?>
} 