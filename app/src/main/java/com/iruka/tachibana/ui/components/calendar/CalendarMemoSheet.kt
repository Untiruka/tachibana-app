package com.iruka.tachibana.ui.components.calendar


// Compose 基本UI
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import com.iruka.tachibana.ui.screens.YuseiMagic
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.pointer.pointerInput


// 日付管理
import java.time.LocalDate

// R参照（リソースID用。プロジェクトパッケージにあわせて変更可）
import com.iruka.tachibana.R
import com.iruka.tachibana.ui.screens.CalendarEvent

// イベント型（独自定義）例：data class CalendarEvent(val title: String) など
// import com.iruka.tachibana.model.CalendarEvent


@Composable
fun CalendarMemoSheet(
    selected: LocalDate,
    memo: String,
    tempMemo: String,
    dayEvents: List<CalendarEvent>,
    onTempMemoChange: (String) -> Unit,
    onSave: () -> Unit,
    onDelete: () -> Unit,
    yuseiFont: FontFamily,
    onOpenModal: () -> Unit,           // ← ここ
    onTextFieldFocusChanged: (Boolean) -> Unit
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
                modifier = Modifier.size(24.dp).padding(end = 1.dp)
            )
            Text(
                text = "$selected の予定",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                fontFamily = YuseiMagic
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
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
                    fontSize = 10.sp,
                    fontFamily = YuseiMagic
                )
                Text(
                    text = memo,
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 4.dp),
                    fontFamily = YuseiMagic
                )
            }
            Button(
                onClick = onDelete,
                modifier = Modifier.align(Alignment.End).padding(top = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF444444))
            ) {
                Text(
                    text = stringResource(R.string.main_calendar_delete),
                    color = Color.White,
                    fontFamily = YuseiMagic
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color.Transparent)
                .border(
                    width = 1.dp,
                    color = Color.White, // 枠線色
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable { onOpenModal() }
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            val displayText = if (memo.isBlank()) "メモを入力…" else memo
            Text(
                text = displayText,
                color = Color.White,
                fontFamily = YuseiMagic
            )
            // ラベルやアイコンを追加したい場合はRowでText, Icon並べてもOK
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onSave,
            modifier = Modifier.align(Alignment.End),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF666666))
        ) {
            Text(
                text = stringResource(R.string.main_calendar_save),
                color = Color.White,
                fontFamily = YuseiMagic
            )
        }
    }
}