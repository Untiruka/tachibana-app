@file:OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)

package com.iruka.tachibana.ui.screens

import android.content.Context
import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex

import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.zIndex

import kotlinx.coroutines.delay
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
import com.iruka.tachibana.R
import kotlinx.coroutines.delay
import androidx.compose.animation.core.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.IntOffset

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.ui.res.painterResource


import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.Canvas
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font

// 仮のイベントクラス（今は使ってなくてもOK）
data class CalendarEvent(
    val title: String,
    val description: String? = null
)

@Composable
fun CalendarModal(
    onClose: () -> Unit,
    selectedDateTime: Long,
    eventsByDate: Map<LocalDate, List<CalendarEvent>> = emptyMap()  // ← ★ここ
     ) {


    val selectedDate = remember(selectedDateTime) {
        Instant.ofEpochMilli(selectedDateTime).atZone(ZoneId.systemDefault()).toLocalDate()
    }
    val today = remember { LocalDate.now() }

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



    @Composable
    fun DaysOfWeekHeader() {
        val days = listOf(
            stringResource(R.string.main_calendar_sunday),
            stringResource(R.string.main_calendar_monday),
            stringResource(R.string.main_calendar_tuesday),
            stringResource(R.string.main_calendar_wednesday),
            stringResource(R.string.main_calendar_thursday),
            stringResource(R.string.main_calendar_friday),
            stringResource(R.string.main_calendar_saturday)
        )
        Row(modifier = Modifier.fillMaxWidth()) {
            days.forEach {
                Text(
                    text = it,
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 1.dp),
                    textAlign = TextAlign.Center,
                    color = Color.LightGray,
                    fontFamily = yuseiFont,
                    fontSize = 14.sp
                )
            }
        }
    }

    ModalWrapper(onClose = onClose) {
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
                        monthHeader = { DaysOfWeekHeader() },
                        dayContent = { day: CalendarDay ->
                            val date = day.date
                            val isStamped = date in stampedDates
                            val isToday = date == LocalDate.now()
                            val hasEvent = eventsByDate.containsKey(date)
                            val hasMemo = date in memoDates


                            Box(
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .padding(3.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(
                                        when {
                                            isToday -> Color(0xFF1976D2)
                                            isStamped -> Color(0xFF2E7D32)
                                            else -> Color(0xFF1A1A1A)
                                        }
                                    )
                                    .border(1.dp, Color.White)
                                    .clickable { selectedDay.value = date },
                                contentAlignment = Alignment.Center
                            ) {
                                // ✅ 扇マークを先に描画（背景層）
                                if (hasMemo) {
                                    Image(
                                        painter = painterResource(id = R.drawable.memo),
                                        contentDescription = "memo icon",
                                        modifier = Modifier
                                            .size(16.dp) // ← ここは必要に応じて調整
                                            .align(Alignment.TopEnd)
                                            .padding(top = 2.dp, end = 2.dp)
                                    )
                                }

                                if (isStamped) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.calender),
                                            contentDescription = "calendar icon",
                                            modifier = Modifier
                                                .size(24.dp) // ← デカくした
                                                .align(Alignment.BottomStart)
                                                .padding(start = 2.dp, bottom = 2.dp)
                                        )
                                    }
                                }
                                // ✅ 中身（文字・アイコン類）
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Box(modifier = Modifier.fillMaxSize()) {
                                        // 日付
                                        Text(
                                            text = date.dayOfMonth.toString(),
                                            fontSize = 14.sp,
                                            fontFamily = yuseiFont,
                                            color = Color.White,
                                            modifier = Modifier
                                                .align(Alignment.TopStart)
                                                .padding(top = 2.dp, start = 4.dp)
                                        )

                                        // スタンプ

                                        // イベント
                                        if (hasEvent) {
                                            Box(
                                                modifier = Modifier
                                                    .padding(end = 4.dp)
                                                    .size(6.dp)
                                                    .background(Color.Yellow, shape = CircleShape)
                                                    .align(Alignment.CenterEnd)

                                            )
                                        }
                                    }
                                }
                            }

                        }
                    )

                    val imeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
                    val offsetY by animateDpAsState(
                        targetValue = if (imeVisible) (-200).dp else 0.dp,
                        animationSpec = tween(300)
                    )

                    val prefs = remember {
                        context.getSharedPreferences(
                            "tachibana_prefs",
                            Context.MODE_PRIVATE
                        )
                    }
                    selectedDay.value?.let { selected ->

                        val bringIntoViewRequester = remember { BringIntoViewRequester() }
                        val coroutineScope = rememberCoroutineScope()
                        val prefs = context.getSharedPreferences("tachibana_prefs", Context.MODE_PRIVATE)

                        var memo by remember { mutableStateOf("") }
                        var tempMemo by remember { mutableStateOf("") }

                        // メモの保存・復元
                        LaunchedEffect(selected) {
                            val saved = prefs.getString("memo_$selected", "") ?: ""
                            memo = saved
                            tempMemo = saved
                        }

                        // キーボード表示の状態を検出 & オフセット値をアニメーションで制御
                        val imeBottomPx = WindowInsets.ime.getBottom(LocalDensity.current)
                        val offsetY by animateDpAsState(
                            targetValue = if (imeBottomPx > 0) (-200).dp else 0.dp,
                            animationSpec = tween(300)
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxSize() // ← 画面全体を覆う
                        ) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .offset {
                                        val baseY = (-100).dp  // ← 初期位置を上げる
                                        IntOffset(0, (baseY + offsetY).roundToPx())
                                    }

                                    .zIndex(20f)
                                    .padding(horizontal = 12.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(Color(0xFF333333))
                                        .padding(10.dp)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .verticalScroll(rememberScrollState())
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.memo),
                                                contentDescription = "memo icon",
                                                modifier = Modifier
                                                    .size(24.dp)
                                                    .padding(end = 1.dp)
                                            )
                                            Text(
                                                text = "$selected の予定",
                                                color = Color.White,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 14.sp
                                            )
                                        }

                                        Spacer(modifier = Modifier.height(8.dp))

                                        val dayEvents = eventsByDate[selected] ?: emptyList()
                                        dayEvents.forEach {
                                            Text("- $it", color = Color.White, fontSize = 14.sp)
                                        }

                                        Spacer(modifier = Modifier.height(6.dp))

                                        if (memo.isNotBlank()) {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .background(Color(0xFF444444), RoundedCornerShape(8.dp))
                                                    .padding(6.dp)
                                            ) {
                                                Text(
                                                    text = stringResource(R.string.main_calendar_saved_memo),
                                                    color = Color(0xFFDDDDDD),
                                                    fontSize = 10.sp
                                                )

                                                Text(
                                                    text = memo,
                                                    color = Color.White,
                                                    fontSize = 12.sp,
                                                    modifier = Modifier.padding(top = 4.dp)
                                                )
                                            }

                                            Button(
                                                onClick = {
                                                    prefs.edit().remove("memo_$selected").apply()
                                                    memo = ""
                                                    tempMemo = ""
                                                },
                                                modifier = Modifier
                                                    .align(Alignment.End)
                                                    .padding(top = 8.dp),
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = Color(0xFF444444)
                                                )
                                            ) {
                                                Text(
                                                    text = stringResource(R.string.main_calendar_delete),
                                                    color = Color.White
                                                )

                                            }

                                            Spacer(modifier = Modifier.height(8.dp))
                                        }

                                        OutlinedTextField(
                                            value = tempMemo,
                                            onValueChange = { tempMemo = it },
                                            label = {
                                                Text(stringResource(R.string.main_calendar_memo_label), color = Color.White)
                                            },

                                            colors = OutlinedTextFieldDefaults.colors(
                                                focusedBorderColor = Color.White,
                                                unfocusedBorderColor = Color.Gray,
                                                cursorColor = Color.White,
                                                focusedTextColor = Color.White,
                                                unfocusedTextColor = Color.White
                                            ),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .bringIntoViewRequester(bringIntoViewRequester)
                                        )

                                        Spacer(modifier = Modifier.height(8.dp))

                                        Button(
                                            onClick = {
                                                prefs.edit().putString("memo_$selected", tempMemo).apply()
                                                memo = tempMemo
                                            },
                                            modifier = Modifier.align(Alignment.End),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color(0xFF666666)
                                            )
                                        ) {
                                            Text(
                                                text = stringResource(R.string.main_calendar_save),
                                                color = Color.White
                                            )
                                        }
                                    }
                                }





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
                        }}
                    }
                }
            }
        }
}}

