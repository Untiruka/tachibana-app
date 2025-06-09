package com.iruka.tachibana.ui.screens



import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.iruka.tachibana.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.zIndex


@Composable
fun BouncingIcon(
    modifier: Modifier = Modifier,
    iconResId: Int
) {
    val offsetY = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        repeat(10) {
            offsetY.animateTo(
                targetValue = -10f,
                animationSpec = tween(durationMillis = 150, easing = LinearEasing)
            )
            offsetY.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 150, easing = LinearEasing)
            )
        }
    }

    Image(
        painter = painterResource(id = iconResId),
        contentDescription = null,
        modifier = modifier
            .offset(y = offsetY.value.dp)
            .size(60.dp)
    )
}
@Composable
fun Main_IconBar(
    modifier: Modifier = Modifier,
    onIconClick: (Int) -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        for (i in 1..5) {
            val iconRes = when (i) {
                1 -> R.drawable.home_icons1
                2 -> R.drawable.home_icons2 // ← たちばな（ぴょこぴょこ）
                3 -> R.drawable.home_icons3
                4 -> R.drawable.home_icons4
                5 -> R.drawable.home_icons6
                else -> R.drawable.home_icons1
            }

            if (i == 2) {
                BouncingIcon(
                    iconResId = iconRes,
                    modifier = Modifier
                        .size(60.dp)
                        .clickable { onIconClick(i) }
                )
            } else {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = "icon $i",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(60.dp)
                        .clickable { onIconClick(i) }
                )
            }
        }
    }
}
