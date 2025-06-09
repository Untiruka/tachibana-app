@file:OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)

package com.iruka.tachibana.ui.screens

import android.content.Context
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.delay
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.DayOfWeek
import java.time.YearMonth
import androidx.compose.ui.unit.IntOffset

import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay

import com.iruka.tachibana.R

// ★ここから部品import
import com.iruka.tachibana.ui.components.calendar.CalendarDayCell
import com.iruka.tachibana.ui.components.calendar.CalendarDaysOfWeekHeader
import com.iruka.tachibana.ui.components.calendar.CalendarMemoSheet

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import com.iruka.tachibana.ui.components.calendar.MemoEditModal

// 仮のイベントクラス（ファイル共通で必要ならここで定義）
data class CalendarEvent(
    val title: String,
    val description: String? = null
)

@Composable
fun CalendarModal(
    onClose: () -> Unit,
    selectedDateTime: Long,
    eventsByDate: Map<LocalDate, List<CalendarEvent>> = emptyMap()
) {
    val selectedDate = remember(selectedDateTime) {
        Instant.ofEpochMilli(selectedDateTime).atZone(ZoneId.systemDefault()).toLocalDate()
    }
    val today = remember { LocalDate.now() }

    var isMemoModalOpen by remember { mutableStateOf(false) }


    val startMonth = remember { YearMonth.now().minusMonths(12) }
    val endMonth = remember { YearMonth.now().plusMonths(12) }
    val firstMonth = remember { YearMonth.now() }

    val stampedDates = remember(selectedDateTime) {
        val elapsedDays = ((System.currentTimeMillis() - selectedDateTime) / 86_400_000L).toInt()
        if (elapsedDays < 1) emptySet()
        else {
            val startDate = Instant.ofEpochMilli(selectedDateTime)
                .atZone(ZoneId.systemDefault()).toLocalDate()
            (0 until elapsedDays).map { startDate.plusDays(it.toLong()) }.toSet()
        }
    }
    var isMemoFocused by remember { mutableStateOf(false) }

    val calendarState = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = firstMonth,
        firstDayOfWeek = DayOfWeek.SUNDAY
    )

    val currentMonthText = remember(calendarState.firstVisibleMonth) {
        val m = calendarState.firstVisibleMonth.yearMonth
        "${m.year}年 ${m.monthValue}月"
    }

    val currentMonthColor = when (calendarState.firstVisibleMonth.yearMonth.monthValue) {
        1 -> Color(0xFFBBDEFB)
        2 -> Color(0xFFE1BEE7)
        3 -> Color(0xFFFFCDD2)
        4 -> Color(0xFFFFF9C4)
        5 -> Color(0xFFC8E6C9)
        6 -> Color(0xFFB2EBF2)
        7 -> Color(0xFFFFF59D)
        8 -> Color(0xFFFFCCBC)
        9 -> Color(0xFFD1C4E9)
        10 -> Color(0xFFFFAB91)
        11 -> Color(0xFFBCAAA4)
        12 -> Color(0xFFB3E5FC)
        else -> Color.White
    }

    val yuseiFont = FontFamily.Default

    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("tachibana_prefs", Context.MODE_PRIVATE) }
    val isFirstHint =
        remember { mutableStateOf(!prefs.getBoolean("calendar_swipe_hint_shown", false)) }

    LaunchedEffect(isFirstHint.value) {
        if (isFirstHint.value) {
            delay(5000)
            prefs.edit().putBoolean("calendar_swipe_hint_shown", true).apply()
            isFirstHint.value = false
        }
    }

    val memoDates = remember {
        prefs.all.keys
            .filter { it.startsWith("memo_") }
            .mapNotNull {
                runCatching {
                    LocalDate.parse(it.removePrefix("memo_"))
                }.getOrNull()
            }
            .toSet()
    }
    LaunchedEffect(Unit) {
        if (isFirstHint.value) {
            delay(1000)
            calendarState.animateScrollToMonth(firstMonth.minusMonths(1))
            delay(1000)
            calendarState.animateScrollToMonth(firstMonth)
            delay(1000)
            calendarState.animateScrollToMonth(firstMonth.plusMonths(1))
            delay(1000)
            calendarState.animateScrollToMonth(firstMonth)
        }
    }
    val transition = rememberInfiniteTransition()
    val offsetX by transition.animateFloat(
        initialValue = -36f,
        targetValue = 36f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val selectedDay = remember { mutableStateOf<LocalDate?>(null) }

    // メモ用状態（シートで使用）
    var memo by remember { mutableStateOf("") }
    var tempMemo by remember { mutableStateOf("") }

    // メモ初期化（毎回選択日が変わった時のみ更新）
    LaunchedEffect(selectedDay.value) {
        selectedDay.value?.let { selected ->
            val saved = prefs.getString("memo_$selected", "") ?: ""
            memo = saved
            tempMemo = saved
        }
    }
    CalendarWrapper {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = currentMonthText,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = currentMonthColor,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Box(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.fillMaxSize()) {

                    HorizontalCalendar(
                        state = calendarState,
                        monthHeader = { CalendarDaysOfWeekHeader(yuseiFont) },
                        dayContent = { day: CalendarDay ->
                            CalendarDayCell(
                                date = day.date,
                                isStamped = day.date in stampedDates,
                                isToday = day.date == LocalDate.now(),
                                hasEvent = eventsByDate.containsKey(day.date),
                                hasMemo = day.date in memoDates,
                                yuseiFont = yuseiFont,
                                onClick = { selectedDay.value = it }
                            )
                        }
                    )


                    // メモ編集シート（必要時のみ表示）
                    selectedDay.value?.let { selected ->
                        CalendarMemoSheet(
                            selected = selected,
                            memo = memo,
                            tempMemo = tempMemo,
                            dayEvents = eventsByDate[selected] ?: emptyList(),
                            onTempMemoChange = { tempMemo = it },
                            onSave = {
                                prefs.edit().putString("memo_$selected", tempMemo).apply()
                                memo = tempMemo
                            },
                            onDelete = {
                                prefs.edit().remove("memo_$selected").apply()
                                memo = ""
                                tempMemo = ""
                            },
                            yuseiFont = yuseiFont,
                            onOpenModal = { isMemoModalOpen = true },            // ← ここがonTextFieldFocusChangedより前！
                            onTextFieldFocusChanged = { isMemoFocused = it }
                        )
                    }

// Modal本体
                    if (isMemoModalOpen && selectedDay.value != null) {
                        MemoEditModal(
                            value = tempMemo,
                            onValueChange = { tempMemo = it },
                            onSave = {
                                val selected = selectedDay.value!!
                                prefs.edit().putString("memo_$selected", tempMemo).apply()
                                memo = tempMemo
                                isMemoModalOpen = false
                            },
                            onDismiss = { isMemoModalOpen = false },
                            fontFamily = yuseiFont,
                            title = "${selectedDay.value} のメモ"
                        )
                    }
                    // ▼ ここにSpacerを入れて下に押し下げる
                    Spacer(modifier = Modifier.weight(1f))

                    // ▼ 閉じるボタン（中央下部）
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 80.dp),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        IconButton(
                            onClick = {
                                AudioManager.playSE(context, R.raw.close_door_se)
                                onClose()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Close",
                                tint = Color.White,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }
                }

                // スワイプヒント演出（任意）
                if (isFirstHint.value) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 325.dp)
                            .offset { IntOffset(offsetX.dp.roundToPx(), 0) }
                            .zIndex(30f)
                    ) {
                        Text(
                            text = "↔",
                            fontSize = 60.sp,
                            color = Color.White,
                            modifier = Modifier.alpha(0.9f)
                        )
                    }
                }
            }
        }
    }

}







@Composable
fun CalendarWrapper(
    //onClose: () -> Unit,
    content: @Composable () -> Unit // 子Composableを渡す
) {
    val context = LocalContext.current

    // 背景（外タップ検知＆SE＋close）
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.8f))
            .blur(1.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    AudioManager.playSE(context, R.raw.close_door_se)
              //      onClose()
                })
            }
            .zIndex(9f)
    )

    // メイン内容
    Box(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(10f)
    ) {
        content()
    }
}