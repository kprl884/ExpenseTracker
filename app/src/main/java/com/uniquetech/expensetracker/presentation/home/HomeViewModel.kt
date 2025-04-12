package com.uniquetech.expensetracker.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uniquetech.expensetracker.domain.model.HomeState
import com.uniquetech.expensetracker.domain.model.TimeRange
import com.uniquetech.expensetracker.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val expenseRepository: ExpenseRepository
) : ViewModel() {

    private val _timeRange = MutableStateFlow<TimeRange>(TimeRange.Daily)
    
    val state: StateFlow<HomeState> = combine(
        _timeRange,
        expenseRepository.getExpensesByTimeRange(_timeRange.value),
        expenseRepository.getTotalSpentByTimeRange(_timeRange.value)
    ) { timeRange, expenses, totalSpent ->
        HomeState(
            currentTimeRange = timeRange,
            expenses = expenses,
            totalSpent = totalSpent
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeState(isLoading = true)
    )

    fun onTimeRangeSelected(timeRange: TimeRange) {
        viewModelScope.launch {
            _timeRange.value = timeRange
        }
    }
} 