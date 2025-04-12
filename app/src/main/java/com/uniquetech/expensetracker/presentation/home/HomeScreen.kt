package com.uniquetech.expensetracker.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.uniquetech.expensetracker.domain.model.HomeState
import com.uniquetech.expensetracker.domain.model.TimeRange
import com.uniquetech.expensetracker.presentation.components.ExpenseItem
import com.uniquetech.expensetracker.presentation.components.SpendingSummary
import com.uniquetech.expensetracker.presentation.components.TimeRangeSelector
import java.time.LocalDateTime

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToAddExpense: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    
    Scaffold(
        containerColor = Color(0xFFF8F9FA),
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToAddExpense,
                containerColor = Color(0xFF1B1B4B),
                contentColor = Color.White,
                modifier = Modifier.clip(CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Expense"
                )
            }
        }
    ) { padding ->
        HomeContent(
            state = state,
            onTimeRangeSelected = { viewModel.onTimeRangeSelected(it) },
            contentPadding = padding
        )
    }
}

@Composable
private fun HomeContent(
    state: HomeState,
    onTimeRangeSelected: (TimeRange) -> Unit,
    contentPadding: PaddingValues
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .background(Color(0xFFF8F9FA))
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else if (state.error != null) {
            Text(
                text = state.error,
                color = Color.Red,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Üst kısım - Zaman aralığı ve toplam harcama
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TimeRangeSelector(
                        currentTimeRange = state.currentTimeRange,
                        onTimeRangeSelected = onTimeRangeSelected
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    SpendingSummary(
                        totalSpent = state.totalSpent,
                        timeRange = state.currentTimeRange,
                        currencyCode = state.currencyCode
                    )
                }
                
                // Harcamalar listesi
                if (state.expenses.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No expenses yet",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Gray
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(horizontal = 16.dp)
                    ) {
                        // Bugünün harcamaları
                        val todayExpenses = state.expenses.filter { 
                            it.date.dayOfYear == LocalDateTime.now().dayOfYear && 
                            it.date.year == LocalDateTime.now().year 
                        }
                        
                        if (todayExpenses.isNotEmpty()) {
                            item {
                                Text(
                                    text = "Today",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                                )
                                
                                Text(
                                    text = "${getCurrencySymbol(state.currencyCode)}${todayExpenses.sumOf { it.amount }.formatToString()}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray,
                                    fontSize = 14.sp,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                            }
                            
                            items(todayExpenses) { expense ->
                                ExpenseItem(
                                    expense = expense, 
                                    currencySymbol = getCurrencySymbol(state.currencyCode)
                                )
                            }
                        }
                        
                        // Dünün harcamaları
                        val yesterdayExpenses = state.expenses.filter { 
                            it.date.dayOfYear == LocalDateTime.now().minusDays(1).dayOfYear && 
                            it.date.year == LocalDateTime.now().year 
                        }
                        
                        if (yesterdayExpenses.isNotEmpty()) {
                            item {
                                Text(
                                    text = "Yesterday",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                                )
                                
                                Text(
                                    text = "${getCurrencySymbol(state.currencyCode)}${yesterdayExpenses.sumOf { it.amount }.formatToString()}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray,
                                    fontSize = 14.sp,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                            }
                            
                            items(yesterdayExpenses) { expense ->
                                ExpenseItem(
                                    expense = expense,
                                    currencySymbol = getCurrencySymbol(state.currencyCode)
                                )
                            }
                        }
                        
                        // Daha eski harcamalar
                        val olderExpenses = state.expenses.filter { 
                            it.date.dayOfYear < LocalDateTime.now().minusDays(1).dayOfYear || 
                            it.date.year < LocalDateTime.now().year 
                        }
                        
                        if (olderExpenses.isNotEmpty()) {
                            item {
                                Text(
                                    text = "Older",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                                )
                                
                                Text(
                                    text = "${getCurrencySymbol(state.currencyCode)}${olderExpenses.sumOf { it.amount }.formatToString()}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray,
                                    fontSize = 14.sp,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                            }
                            
                            items(olderExpenses) { expense ->
                                ExpenseItem(
                                    expense = expense,
                                    currencySymbol = getCurrencySymbol(state.currencyCode)
                                )
                            }
                        }
                        
                        // Son aşağı boşluk ekler
                        item {
                            Spacer(modifier = Modifier.height(80.dp))
                        }
                    }
                }
            }
        }
    }
}

// Double'ı formatlı stringe çevirme
private fun Double.formatToString(): String {
    return String.format("%.2f", this)
}

// Para birimi kodundan sembol elde etme
private fun getCurrencySymbol(currencyCode: String): String {
    return when (currencyCode) {
        "USD", "$" -> "$"
        "TRY", "₺" -> "₺"
        else -> "$"
    }
} 