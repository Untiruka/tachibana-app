package com.iruka.tachibana.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

import androidx.compose.ui.draw.blur

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iruka.tachibana.R




@Composable
fun TipsModal(
    onClose: () -> Unit,
    setComment: (String) -> Unit = {} // 互換のため残すだけで使わない
) {


    // ─── 表示切替ステート ─────


    ModalWrapper(onClose = onClose) {
        // Tips吹き出しUI
        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 8.dp, bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // 1. 招待（反転）
            TipBox(
                imageRes = R.drawable.fukidasshihomeandcalendar,
                text = "招待するね！\n我が家に！",
                flip = true
            )

            // 2. 進捗
            TipBox(
                imageRes = R.drawable.fukidasshihomeandcalendar,
                text = "進捗を見て\n見よう"
            )

            // 3. メニュー
            TipBox(
                imageRes = R.drawable.fukidashimenu,
                text = "これが\nメニュー",
                offsetX = (-20).dp,
                offsetY = (-70).dp,
                size = Pair(82.dp, 119.dp)
            )
        }

        // 4. 設定
        Box(
            modifier = Modifier
                .size(95.dp, 79.dp)
                .offset(y = (-50).dp)
                .align(Alignment.BottomEnd)
        ) {
            Image(
                painter = painterResource(id = R.drawable.fukidashiconfig),
                contentDescription = "設定",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(2.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("設定", fontSize = 16.sp, color = Color.Black, fontFamily = YuseiMagic, textAlign = TextAlign.Center)
                Text("ふがふが", fontSize = 8.sp, color = Color.Black, fontFamily = YuseiMagic, textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
fun TipBox(
    imageRes: Int,
    text: String,
    flip: Boolean = false,
    offsetX: Dp = 0.dp,
    offsetY: Dp = (-15).dp,
    size: Pair<Dp, Dp> = Pair(120.dp, 75.dp)
) {
    Box(
        modifier = Modifier.size(size.first, size.second)
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(scaleX = if (flip) -1f else 1f)
                .offset(x = offsetX, y = offsetY)
        )
        Text(
            text = text,
            fontSize = 14.sp,
            color = Color.Black,
            fontFamily = YuseiMagic,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
                .offset(x = offsetX, y = offsetY)
        )
    }
}
