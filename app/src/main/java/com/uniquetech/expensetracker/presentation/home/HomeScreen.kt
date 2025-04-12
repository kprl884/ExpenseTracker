package com.uniquetech.expensetracker.presentation.home

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.uniquetech.expensetracker.domain.model.Expense
import com.uniquetech.expensetracker.domain.model.ExpenseType
import com.uniquetech.expensetracker.domain.model.HomeState
import com.uniquetech.expensetracker.domain.model.TimeRange
import com.uniquetech.expensetracker.presentation.components.ExpenseItem
import com.uniquetech.expensetracker.presentation.components.SpendingSummary
import com.uniquetech.expensetracker.presentation.components.TimeRangeSelector
import java.time.LocalDateTime

@Composable
fun HomeScreen(
    navigateToAddExpense: () -> Unit
) {
    // Gerçek verileri kullanmak yerine şimdilik örnek verilerle gösterelim
    val sampleState = HomeState(
        currentTimeRange = TimeRange.Daily,
        totalSpent = 292.50,
        expenses = sampleExpenses,
        isLoading = false,
        error = null
    )
    
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToAddExpense,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Expense",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) { padding ->
        HomeContent(
            state = sampleState,
            onTimeRangeSelected = { /* Geçici olarak boş bir lambda */ },
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
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else if (state.error != null) {
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error,
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
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        item {
                            Text(
                                text = "Today",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                            Divider()
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        
                        items(state.expenses) { expense ->
                            ExpenseItem(expense = expense)
                        }
                    }
                }
            }
        }
    }
}

// Örnek veriler
private val sampleExpenses = listOf(
    Expense(
        amount = 3.35,
        description = "Treats",
        type = ExpenseType.PETS,
        date = LocalDateTime.now().minusHours(2)
    ),
    Expense(
        amount = 1.70,
        description = "Snacks",
        type = ExpenseType.FOOD,
        date = LocalDateTime.now().minusHours(3)
    ),
    Expense(
        amount = 2.19,
        description = "Coffee",
        type = ExpenseType.FOOD,
        date = LocalDateTime.now().minusHours(4)
    ),
    Expense(
        amount = 2300.00,
        description = "",
        type = ExpenseType.SALARY,
        date = LocalDateTime.now().minusHours(5)
    ),
    Expense(
        amount = 39.75,
        description = "Jeff's birthday",
        type = ExpenseType.GIFTS,
        date = LocalDateTime.now().minusDays(1)
    )
) 