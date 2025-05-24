@file:OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)

package com.iruka.tachibana.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState

import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import java.time.DayOfWeek
import java.time.YearMonth

import androidx.compose.ui.text.style.TextAlign



@Composable
fun CalendarModal(
    onClose: () -> Unit,
    selectedDateTime: Long
) {
    val selectedDate = remember(selectedDateTime) {
        Instant.ofEpochMilli(selectedDateTime)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    }

    val today = remember {
        Instant.now().atZone(ZoneId.systemDefault()).toLocalDate()
    }

    val startMonth = remember { YearMonth.now().minusMonths(6) }
    val endMonth = remember { YearMonth.now().plusMonths(6) }
    val firstMonth = remember { YearMonth.now() }

    val stampedDates = remember(selectedDateTime) {
        val elapsedDays = ((System.currentTimeMillis() - selectedDateTime) / 86_400_000L).toInt()
        Log.d("STAMP_DEBUG", "elapsedDays=$elapsedDays")

        if (elapsedDays < 1) emptySet()
        else {
            val startDate = Instant.ofEpochMilli(selectedDateTime)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()

            val result = (0 until elapsedDays).map { i ->
                startDate.plusDays(i.toLong())
            }.toSet()

            Log.d("STAMP_DEBUG", "Stamped dates = $result")
            result
        }
    }

    val calendarState = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = firstMonth,
        firstDayOfWeek = DayOfWeek.SUNDAY
    )

    val yuseiFont = FontFamily.Default // ← 差し替えるならここ

    // 🆕 ここで現在表示中の月を取得
    val currentMonthText = remember(calendarState.firstVisibleMonth) {
        val month = calendarState.firstVisibleMonth.yearMonth
        "${month.year}年 ${month.monthValue}月"
    }

    @Composable
    fun DaysOfWeekHeader() {
        val daysOfWeek = listOf("日", "月", "火", "水", "木", "金", "土")
        Row(modifier = Modifier.fillMaxWidth()) {
            daysOfWeek.forEach {
                Text(
                    text = it,
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 6.dp),
                    textAlign = TextAlign.Center,
                    color = Color.LightGray,
                    fontFamily = yuseiFont,
                    fontSize = 12.sp
                )
            }
        }
    }

    ModalWrapper(onClose = onClose) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(15f)
                .background(Color.Black)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = currentMonthText,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .align(Alignment.CenterHorizontally)
                )

                HorizontalCalendar(
                    state = calendarState,
                    monthHeader = { DaysOfWeekHeader() },
                    dayContent = { day: CalendarDay ->
                        val date = day.date
                        val isStamped = date in stampedDates

                        Box(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .padding(3.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(
                                    when {
                                        isStamped -> Color(0xFF2E7D32) // 緑系
                                        else -> Color(0xFF1A1A1A)
                                    }
                                )
                                .border(1.dp, Color.White)
                                .clickable { Log.d("DATE_CLICK", "Clicked: $date") },
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = date.dayOfMonth.toString(),
                                    fontSize = 14.sp,
                                    fontFamily = yuseiFont,
                                    color = Color.White
                                )
                                if (isStamped) {
                                    Text(
                                        text = "💮",
                                        fontSize = 12.sp,
                                        color = Color.Yellow
                                    )
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}
