package com.uniquetech.expensetracker.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uniquetech.expensetracker.domain.model.TimeRange

@Composable
fun TimeRangeSelector(
    currentTimeRange: TimeRange,
    onTimeRangeSelected: (TimeRange) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TimeRangeOption(
            text = "Day",
            selected = currentTimeRange is TimeRange.Daily,
            onClick = { onTimeRangeSelected(TimeRange.Daily) }
        )
        
        TimeRangeOption(
            text = "Week",
            selected = currentTimeRange is TimeRange.Weekly,
            onClick = { onTimeRangeSelected(TimeRange.Weekly) }
        )
        
        TimeRangeOption(
            text = "Month",
            selected = currentTimeRange is TimeRange.Monthly,
            onClick = { onTimeRangeSelected(TimeRange.Monthly) }
        )
    }
}

@Composable
private fun TimeRangeOption(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(
                if (selected) Color(0xFF1B1B4B)
                else Color(0xFFEBEEF3)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal,
            color = if (selected) Color.White else Color(0xFF1B1B4B)
        )
    }
} 