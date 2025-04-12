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
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uniquetech.expensetracker.domain.model.Expense
import com.uniquetech.expensetracker.domain.model.ExpenseType
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun ExpenseItem(
    expense: Expense,
    modifier: Modifier = Modifier,
    currencySymbol: String = "$"
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Sol taraf - Emoji ve İsim
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Emoji kutusu
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(getBackgroundColorForEmoji()),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = getEmojiForExpenseType(expense.type),
                        fontSize = 20.sp
                    )
                }
                
                Column(
                    modifier = Modifier.padding(start = 12.dp)
                ) {
                    Text(
                        text = getDisplayNameForExpenseType(expense.type),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                    if (expense.description.isNotEmpty()) {
                        Text(
                            text = expense.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }
                    Text(
                        text = formatTime(expense),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
            }
            
            // Sağ taraf - Fiyat
            Text(
                text = formatAmount(expense, currencySymbol),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = if (expense.type == ExpenseType.SALARY) Color(0xFF4CAF50) else Color.Black
            )
        }
        
        Divider(
            color = Color(0xFFEEEEEE),
            thickness = 1.dp
        )
    }
}

private fun formatTime(expense: Expense): String {
    val formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH)
    return expense.date.format(formatter)
}

private fun formatAmount(expense: Expense, currencySymbol: String): String {
    val prefix = if (expense.type == ExpenseType.SALARY) "+" else ""
    return "$prefix$currencySymbol${String.format("%.2f", expense.amount)}"
}

private fun getBackgroundColorForEmoji(): Color {
    // Emojilerin göründüğü açık gri arkaplan rengi
    return Color(0xFFF5F5F5)
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
        ExpenseType.SALARY -> "💼"
        ExpenseType.GIFTS -> "🎁"
        ExpenseType.COFFEE -> "☕"
        ExpenseType.SNACKS -> "🍪"
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
        ExpenseType.COFFEE -> "Coffee"
        ExpenseType.SNACKS -> "Snacks"
        ExpenseType.OTHER -> "Other"
    }
} 