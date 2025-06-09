package com.iruka.tachibana.ui.components.calendar

// Compose基本UI
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// リソースから文字列取得
import androidx.compose.ui.res.stringResource

// R参照（リソースID用）
import com.iruka.tachibana.R


// CalendarDaysOfWeekHeader.kt
@Composable
fun CalendarDaysOfWeekHeader(yuseiFont: FontFamily) {
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
