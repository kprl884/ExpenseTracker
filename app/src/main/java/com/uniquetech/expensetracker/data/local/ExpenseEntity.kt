package com.uniquetech.expensetracker.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.uniquetech.expensetracker.domain.model.Expense
import com.uniquetech.expensetracker.domain.model.ExpenseType
import java.time.LocalDateTime

@Entity(tableName = "expenses")
data class ExpenseEntity(
    @PrimaryKey
    val id: String,
    val amount: Double,
    val description: String,
    val type: String, // ExpenseType enum adı olarak saklanacak
    val date: LocalDateTime,
    val currencyCode: String
) {
    companion object {
        fun fromDomain(expense: Expense): ExpenseEntity {
            return ExpenseEntity(
                id = expense.id,
                amount = expense.amount,
                description = expense.description,
                type = expense.type.name,
                date = expense.date,
                currencyCode = expense.currencyCode
            )
        }
    }
    
    fun toDomain(): Expense {
        return Expense(
            id = id,
            amount = amount,
            description = description,
            type = ExpenseType.valueOf(type),
            date = date,
            currencyCode = currencyCode
        )
    }
} 