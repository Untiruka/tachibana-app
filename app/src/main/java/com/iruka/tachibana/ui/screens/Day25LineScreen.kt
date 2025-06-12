package com.iruka.tachibana.ui.screens

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iruka.tachibana.R
import kotlinx.coroutines.delay
import com.iruka.tachibana.ui.components.BouncingDotsIndicator
import android.os.Handler
import android.os.Looper
import androidx.navigation.NavController
@Composable
fun Day25LineScreen(onDismiss: () -> Unit) {
    val context = LocalContext.current
    val icon1: Painter = painterResource(id = R.drawable.line_1)
    val icon2: Painter = painterResource(id = R.drawable.line_2)
    val icon3: Painter = painterResource(id = R.drawable.line_3)

    val mediaPlayer = remember {
        MediaPlayer.create(context, R.raw.line25)
    }

    LaunchedEffect(Unit) {
        val prefs = context.getSharedPreferences("tachibana_prefs", Context.MODE_PRIVATE)

        // 🎯 一度表示したら二度と出さないフラグ
        val hasLaunched = prefs.getBoolean("day25_line_shown", false)
        if (hasLaunched) {
            onDismiss() // 既に表示済みなら即終了
            return@LaunchedEffect
        }

        // 🎯 フラグ保存（今後一切出ないように）
        prefs.edit()
            .putBoolean("day25_line_shown", true)
            .putBoolean("just_returned_from_day25", true) // → MainScreen用

            .apply()

        // 🔊 着信音（10秒）
        val ring = MediaPlayer.create(context, R.raw.line25)
        ring?.start()
        delay(10_000)
        ring?.stop()
        ring?.release()

        // 🔊 セリフ（4.5秒）
        val voice = MediaPlayer.create(context, R.raw.day25_1)
        voice?.start()
        delay(4_500)

        // 🔚 終了処理
        onDismiss()
    }

    DisposableEffect(Unit) {
        onDispose {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
            }
            mediaPlayer.release()
        }
    }

    // 🌙 UI（そのまま）
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(painter = icon1, contentDescription = "Caller Icon", modifier = Modifier.size(128.dp))
        Text(text = "Incoming call", fontSize = 24.sp, color = Color.White)
        BouncingDotsIndicator()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 48.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = icon2, contentDescription = "Accept", modifier = Modifier.size(72.dp))
            Image(painter = icon3, contentDescription = "Reject", modifier = Modifier.size(72.dp))
        }
    }
}
