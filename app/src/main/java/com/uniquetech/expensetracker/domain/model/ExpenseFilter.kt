package com.uniquetech.expensetracker.domain.model

import java.time.LocalDate

sealed class TimeRange {
    object Daily : TimeRange()
    object Weekly : TimeRange()
    object Monthly : TimeRange()
    data class Custom(val startDate: LocalDate, val endDate: LocalDate) : TimeRange()
}

data class ExpenseFilter(
    val timeRange: TimeRange = TimeRange.Daily,
    val expenseTypes: List<ExpenseType>? = null,
    val minAmount: Double? = null,
    val maxAmount: Double? = null
) 