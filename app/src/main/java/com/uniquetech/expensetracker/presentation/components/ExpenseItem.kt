package com.uniquetech.expensetracker.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.uniquetech.expensetracker.domain.model.Expense
import com.uniquetech.expensetracker.domain.model.ExpenseType
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun ExpenseItem(
    expense: Expense,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // İkon kutusu
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(getColorForExpenseType(expense.type).copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = getEmojiForExpenseType(expense.type),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                
                Column(
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    Text(
                        text = getDisplayNameForExpenseType(expense.type),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = expense.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                    Text(
                        text = formatTime(expense),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                }
            }
            
            // Tutar
            Text(
                text = formatAmount(expense),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = if (expense.type == ExpenseType.SALARY) Color.Green else Color.Red
            )
        }
    }
}

private fun formatTime(expense: Expense): String {
    val formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH)
    return expense.date.format(formatter)
}

private fun formatAmount(expense: Expense): String {
    val prefix = if (expense.type == ExpenseType.SALARY) "+" else "-"
    return "$prefix$${String.format("%.2f", expense.amount)}"
}

private fun getColorForExpenseType(type: ExpenseType): Color {
    return when (type) {
        ExpenseType.FOOD -> Color(0xFFF44336)
        ExpenseType.TRANSPORT -> Color(0xFF3F51B5)
        ExpenseType.ENTERTAINMENT -> Color(0xFF9C27B0)
        ExpenseType.SHOPPING -> Color(0xFF2196F3)
        ExpenseType.HEALTH -> Color(0xFF4CAF50)
        ExpenseType.EDUCATION -> Color(0xFFFF9800)
        ExpenseType.BILLS -> Color(0xFF795548)
        ExpenseType.PETS -> Color(0xFF607D8B)
        ExpenseType.SALARY -> Color(0xFF4CAF50)
        ExpenseType.GIFTS -> Color(0xFFE91E63)
        ExpenseType.OTHER -> Color(0xFF9E9E9E)
    }
}

private fun getEmojiForExpenseType(type: ExpenseType): String {
    return when (type) {
        ExpenseType.FOOD -> "🍔"
        ExpenseType.TRANSPORT -> "🚗"
        ExpenseType.ENTERTAINMENT -> "🎮"
        ExpenseType.SHOPPING -> "🛍️"
        ExpenseType.HEALTH -> "💊"
        ExpenseType.EDUCATION -> "📚"
        ExpenseType.BILLS -> "📄"
        ExpenseType.PETS -> "🐶"
        ExpenseType.SALARY -> "💰"
        ExpenseType.GIFTS -> "🎁"
        ExpenseType.OTHER -> "📝"
    }
}

private fun getDisplayNameForExpenseType(type: ExpenseType): String {
    return when (type) {
        ExpenseType.FOOD -> "Food"
        ExpenseType.TRANSPORT -> "Transport"
        ExpenseType.ENTERTAINMENT -> "Entertainment"
        ExpenseType.SHOPPING -> "Shopping"
        ExpenseType.HEALTH -> "Health"
        ExpenseType.EDUCATION -> "Education"
        ExpenseType.BILLS -> "Bills"
        ExpenseType.PETS -> "Pets"
        ExpenseType.SALARY -> "Salary"
        ExpenseType.GIFTS -> "Gifts"
        ExpenseType.OTHER -> "Other"
    }
} 