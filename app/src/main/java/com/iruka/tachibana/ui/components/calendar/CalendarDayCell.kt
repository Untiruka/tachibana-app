package com.iruka.tachibana.ui.components.calendar


// UIコンポーネント関連
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// 日付管理用
import java.time.LocalDate

// リソース参照用（R.drawableなどを使う場合に必要）
import com.iruka.tachibana.R


// CalendarDayCell.kt
@Composable
fun CalendarDayCell(
    date: LocalDate,
    isStamped: Boolean,
    isToday: Boolean,
    hasEvent: Boolean,
    hasMemo: Boolean,
    yuseiFont: FontFamily,
    onClick: (LocalDate) -> Unit
) {
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

            .clickable { onClick(date) },
        contentAlignment = Alignment.Center
    ) {
        // 扇マーク
        if (hasMemo) {
            Image(
                              painter = painterResource(id = R.drawable.memo),
                              contentDescription = "calendar icon",
                                modifier = Modifier
                                   .size(24.dp)
                                   .align(Alignment.BottomStart)
                                   .padding(start = 2.dp, bottom = 2.dp)
            )
        }

        // スタンプ
      //  if (isStamped) {
     //       Box(
     //           modifier = Modifier.fillMaxSize()
    //        ) {
    //            Image(
     //               painter = painterResource(id = R.drawable.calender),
     //               contentDescription = "calendar icon",
    //                modifier = Modifier
    //                    .size(24.dp)
   // //                    .align(Alignment.BottomStart)
   //                     .padding(start = 2.dp, bottom = 2.dp)
    //            )
      //      }
    //    }
        // 日付・イベント点
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = date.dayOfMonth.toString(),
                    fontSize = 14.sp,
                    fontFamily = yuseiFont,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(top = 2.dp, start = 4.dp)
                )
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
