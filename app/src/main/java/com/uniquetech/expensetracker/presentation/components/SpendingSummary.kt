package com.uniquetech.expensetracker.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uniquetech.expensetracker.domain.model.TimeRange
import java.text.DecimalFormat

@Composable
fun SpendingSummary(
    totalSpent: Double,
    timeRange: TimeRange,
    currencyCode: String = "USD",
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = getTimeRangeLabel(timeRange),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            
            Text(
                text = formatCurrency(totalSpent, currencyCode),
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1B1B4B),
                textAlign = TextAlign.Center
            )
        }
    }
}

private fun formatCurrency(amount: Double, currencyCode: String): String {
    val formatter = DecimalFormat("###,##0.00")
    val symbol = getCurrencySymbol(currencyCode)
    return "$symbol${formatter.format(amount)}"
}

private fun getCurrencySymbol(currencyCode: String): String {
    return when (currencyCode) {
        "USD", "$" -> "$"
        "TRY", "₺" -> "₺"
        else -> "$"
    }
}

private fun getTimeRangeLabel(timeRange: TimeRange): String {
    return when (timeRange) {
        is TimeRange.Daily -> "Spent today"
        is TimeRange.Weekly -> "Spent this week"
        is TimeRange.Monthly -> "Spent this month"
        is TimeRange.Custom -> "Spent in period"
    }
} 