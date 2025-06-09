package com.iruka.tachibana.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
@Composable
fun BouncingDotsIndicator() {
    val dotCount = 3
    val delays = listOf(0L, 150L, 300L) // 各ドットに開始遅延

    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        repeat(dotCount) { index ->
            val offsetY = remember { Animatable(0f) }

            LaunchedEffect(index) {
                delay(delays[index])
                while (true) {
                    offsetY.animateTo(
                        targetValue = -8f,
                        animationSpec = tween(200, easing = LinearOutSlowInEasing)
                    )
                    offsetY.animateTo(
                        targetValue = 0f,
                        animationSpec = tween(200, easing = FastOutLinearInEasing)
                    )
                    delay(450) // 周期を少し長めにしてズレを保つ
                }
            }

            Box(
                modifier = Modifier
                    .size(8.dp)
                    .offset(y = offsetY.value.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF00FF00))
            )
        }
    }
}
