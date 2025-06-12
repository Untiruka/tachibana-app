package com.iruka.tachibana.ui.screens

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import com.iruka.tachibana.R
import com.iruka.tachibana.ui.screens.YuseiMagic
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iruka.tachibana.ui.components.BannerAdView

@Composable
fun EventDay7Screen(navController: NavController) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("tachibana_prefs", Context.MODE_PRIVATE)

    val index = remember { mutableIntStateOf(0) }

    val lines = listOf(
        stringResource(R.string.day7_line_0),
        stringResource(R.string.day7_line_1),
        stringResource(R.string.day7_line_2),
        stringResource(R.string.day7_line_3),
        stringResource(R.string.day7_line_4),
        stringResource(R.string.day7_line_5),
        stringResource(R.string.day7_line_6),
        stringResource(R.string.day7_line_7),
        stringResource(R.string.day7_line_8),
        stringResource(R.string.day7_line_9),
        stringResource(R.string.day7_line_10),
        stringResource(R.string.day7_line_11),
        stringResource(R.string.day7_line_12),
        stringResource(R.string.day7_line_13)
    )

    val currentLine = lines.getOrNull(index.value)

    // Composable内で評価されるようにする
    val line0 = stringResource(R.string.day7_line_0)
    val line3 = stringResource(R.string.day7_line_3)
    val line13 = stringResource(R.string.day7_line_13)

// その後、画像切り替えロジック
    val currentImage by remember(currentLine) {
        derivedStateOf {
            when (currentLine) {
                line0 -> R.drawable.day7_image_1
                line3 -> R.drawable.day7_image_2
                line13 -> R.drawable.day7_image_3
                else -> R.drawable.day7_image_2
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F3F3))
    ) {
        // 一番上のバナー表示
        BannerAdView(modifier = Modifier.fillMaxWidth())

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF3F3F3))
                .clickable {
                    AudioManager.playSE(context, R.raw.cursor_move_se)
                    if (index.value < lines.lastIndex) {
                        index.value++
                    } else {
                        val consumed =
                            prefs.getStringSet("consumedEvents", emptySet())?.toMutableSet()
                                ?: mutableSetOf()
                        consumed.add("7")
                        prefs.edit().putStringSet("consumedEvents", consumed).apply()
                        navController.navigate("main") {
                            popUpTo("main") { inclusive = true }
                        }
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = currentImage),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .alpha(1f)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "たちばな「${currentLine ?: ""}」",
                    color = Color(0xFF333333),
                    fontSize = 20.sp,
                    fontFamily = YuseiMagic,
                    modifier = Modifier.padding(16.dp)
                )
            }

            Skipp.SkipButton(day = "7", navController = navController, context = context)
        }

    }
}