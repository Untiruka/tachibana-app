package com.iruka.tachibana.ui.screens

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.iruka.tachibana.R
import kotlinx.coroutines.delay
import com.iruka.tachibana.ui.screens.ChibiHanaFadeIn
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOutCirc

@Composable
fun Bad2Screen(navController: NavController) {

    val context = LocalContext.current
    val index = remember { mutableIntStateOf(0) }
    val showFinalChoice = remember { mutableStateOf(false) }
    val isAnimating = remember { mutableStateOf(false) }
    val lines = listOf(
        stringResource(R.string.Bad2_line_0),
        stringResource(R.string.Bad2_line_1),
        stringResource(R.string.Bad2_line_2),
        stringResource(R.string.Bad2_line_3),
        stringResource(R.string.Bad2_line_4),
        stringResource(R.string.Bad2_line_5),
        stringResource(R.string.Bad2_line_6),
        stringResource(R.string.Bad2_line_7),
        stringResource(R.string.Bad2_line_8),
        stringResource(R.string.Bad2_line_9),
        stringResource(R.string.Bad2_line_10),
        stringResource(R.string.Bad2_line_11),
        stringResource(R.string.Bad2_line_12),
        stringResource(R.string.Bad2_line_13),
        stringResource(R.string.Bad2_line_14),
        stringResource(R.string.Bad2_line_15),
        stringResource(R.string.Bad2_line_16),
        stringResource(R.string.Bad2_line_17),
        stringResource(R.string.Bad2_line_18)
    )

// key: セリフ文字列, value: Pair(元stringId, 画像)
    val fadeTriggers = listOf(
        R.string.Bad2_line_0 to R.drawable.bad2_1,
        R.string.Bad2_line_1 to R.drawable.bad2_2,
        R.string.Bad2_line_2 to R.drawable.bad2_2,
        R.string.Bad2_line_3 to R.drawable.bad2_3,
        R.string.Bad2_line_4 to R.drawable.bad2_3,
        R.string.Bad2_line_5 to R.drawable.bad2_3,
        R.string.Bad2_line_6 to R.drawable.bad2_4,
        R.string.Bad2_line_7 to R.drawable.bad2_4,
        R.string.Bad2_line_8 to R.drawable.bad2_4,
        R.string.Bad2_line_9 to R.drawable.bad2_5,
        R.string.Bad2_line_10 to R.drawable.bad2_5,
        R.string.Bad2_line_11 to R.drawable.bad2_5,
        R.string.Bad2_line_12 to R.drawable.bad2_6,
        R.string.Bad2_line_13 to R.drawable.bad2_6,
        R.string.Bad2_line_14 to R.drawable.bad2_6,
        R.string.Bad2_line_15 to R.drawable.bad2_7,
        R.string.Bad2_line_16 to R.drawable.bad2_8,
        R.string.Bad2_line_17 to R.drawable.bad2_8,
        R.string.Bad2_line_18 to R.drawable.bad2_end
    ).associate { stringResource(it.first) to (it.first to it.second) }

    val currentLine = lines.getOrNull(index.value)
    val currentImage = fadeTriggers[currentLine]?.second
    val appearAtLine = fadeTriggers[currentLine]?.first?.let { stringResource(it) }
    val targetLine = stringResource(R.string.Bad2_line_9) // ← ここで確保！

    LaunchedEffect(currentLine) {
        if (currentLine == targetLine) {
            AudioManager.stopBgm()
            AudioManager.playBgm(context, R.raw.bad2_1)
        }
    }

    LaunchedEffect(Unit) {
        AudioManager.stopBgm()
        AudioManager.playBgm(context, R.raw.bad1bgm)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEEE8DD))
            .clickable(enabled = !isAnimating.value && !showFinalChoice.value) {
                AudioManager.playSE(context, R.raw.cursor_move_se)
                if (index.value < lines.lastIndex) {
                    index.value++
                } else {
                    showFinalChoice.value = true
                }
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (currentImage != null) {
                ChibiHanaFadeIn(
                    imageResId = currentImage,
                    appearAtLine = appearAtLine ?: "",
                    currentLine = currentLine ?: "",
                    onAnimationStateChange = { isAnimating.value = it },
                    bottomContent = {
                        currentLine?.let {
                            Row(
                                modifier = Modifier.padding(horizontal = 24.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = it,
                                    fontSize = 20.sp,
                                    color = Color.Black,
                                    fontFamily = YuseiMagic
                                )
                            }
                        }
                    }
                )
            }
        }

        if (showFinalChoice.value) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        navController.navigate("main") {
                            popUpTo("main") { inclusive = true }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("タイトルへ戻る", fontFamily = YuseiMagic)
                }
            }
        }
    }
}
