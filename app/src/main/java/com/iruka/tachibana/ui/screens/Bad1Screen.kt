package com.iruka.tachibana.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOutCirc
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

import com.iruka.tachibana.R

enum class ChibiExpression {
    Angry, Pointing, Thinking, Smile, Smile2, Smile3, Happy, Question, Relax, ArmsCrossed, YareYare, Cry,
    Pointing1,Cry2
}
fun getImageForExpression(expression: ChibiExpression): Int {
    return when (expression) {
        ChibiExpression.Angry -> R.drawable.chibibana_angry
        ChibiExpression.Pointing -> R.drawable.chibibana_point_pixian_ai
        ChibiExpression.Pointing1 -> R.drawable.chibinbana_pointing// 通常のやつ
        ChibiExpression.Thinking -> R.drawable.chibibana_arms_crossed
        ChibiExpression.Smile -> R.drawable.chibibana_smile
        ChibiExpression.Smile2 -> R.drawable.chibibana_smile2
        ChibiExpression.Smile3 -> R.drawable.chibibana_smile3
        ChibiExpression.Happy -> R.drawable.chibibana_happy
        ChibiExpression.Question -> R.drawable.chibibana_question
        ChibiExpression.Relax -> R.drawable.chibibana_relax
        ChibiExpression.ArmsCrossed -> R.drawable.chibibana_arms_crossed
        ChibiExpression.YareYare -> R.drawable.chibibana_yareyare
        ChibiExpression.Cry -> R.drawable.chibibana_cry1
        ChibiExpression.Cry2 -> R.drawable.chibibana_cry2
    }
}


// --- セリフリストだけ（画像なし） ---



@Composable
fun Bad1Screen(navController: NavController) {

    val lines = listOf(
        stringResource(R.string.line_0),
        stringResource(R.string.line_1),
        stringResource(R.string.line_2),
        stringResource(R.string.line_3),
        stringResource(R.string.line_4),
        stringResource(R.string.line_5),
        stringResource(R.string.line_6),
        stringResource(R.string.line_7),
        stringResource(R.string.line_8),
        stringResource(R.string.line_9),
        stringResource(R.string.line_10),
        stringResource(R.string.line_11),
        stringResource(R.string.line_12),
        stringResource(R.string.line_13),
        stringResource(R.string.line_14),
        stringResource(R.string.line_15),
        stringResource(R.string.line_16),
        stringResource(R.string.line_17),
        stringResource(R.string.line_18),
        stringResource(R.string.line_19),
        stringResource(R.string.line_20),
        stringResource(R.string.line_21),
        stringResource(R.string.line_22),
        stringResource(R.string.line_23),
        stringResource(R.string.line_24),
        stringResource(R.string.line_25),
        stringResource(R.string.line_26),
        stringResource(R.string.line_27),
        stringResource(R.string.line_28),
        stringResource(R.string.line_29),
        stringResource(R.string.line_30),
        stringResource(R.string.line_31),
        stringResource(R.string.line_32),
        stringResource(R.string.line_33),
        stringResource(R.string.line_34),
        stringResource(R.string.line_35),
        stringResource(R.string.line_36),
        stringResource(R.string.line_37),
        stringResource(R.string.line_38),
        stringResource(R.string.line_39),
        stringResource(R.string.line_40),
        stringResource(R.string.line_41),
        stringResource(R.string.line_42),
        stringResource(R.string.line_43),
        stringResource(R.string.line_44),
        stringResource(R.string.line_45),
        stringResource(R.string.line_46),
        stringResource(R.string.line_47),
        stringResource(R.string.line_48),
        stringResource(R.string.line_49)
    )


    DoubleBackToExitHandler()
    val index = remember { mutableIntStateOf(0) }
    val currentLine = lines.getOrNull(index.value)
    val isAnimating = remember { mutableStateOf(false) }
    val context = LocalContext.current

    // 🔥 表情切替リスト（全体）
    val fadeTriggers = listOf(
        stringResource(R.string.line_2) to ChibiExpression.Angry,
        stringResource(R.string.line_4) to ChibiExpression.Pointing1,
        stringResource(R.string.line_6) to ChibiExpression.ArmsCrossed,
        stringResource(R.string.line_8) to ChibiExpression.Pointing,
        stringResource(R.string.line_10) to ChibiExpression.Pointing,
        stringResource(R.string.line_13) to ChibiExpression.YareYare,
        stringResource(R.string.line_15) to ChibiExpression.ArmsCrossed,
        stringResource(R.string.line_16) to ChibiExpression.Angry,
        stringResource(R.string.line_25) to ChibiExpression.Relax,
        stringResource(R.string.line_34) to ChibiExpression.Cry2,
        stringResource(R.string.line_35) to ChibiExpression.Cry,
        stringResource(R.string.line_39) to ChibiExpression.ArmsCrossed,
        stringResource(R.string.line_41) to ChibiExpression.Happy,
        stringResource(R.string.line_43) to ChibiExpression.Question,
        stringResource(R.string.line_44) to ChibiExpression.Smile,
        stringResource(R.string.line_45) to ChibiExpression.Happy,
        stringResource(R.string.line_48) to ChibiExpression.Smile2
    )


    val fadeInTriggers = listOf(
        stringResource(R.string.line_2),
        stringResource(R.string.line_34),
        stringResource(R.string.line_48)
    )

    var lastExpression by remember { mutableStateOf<ChibiExpression?>(null) }
    val currentExpression = fadeTriggers.find { currentLine?.startsWith(it.first) == true }?.second
    val imageResId = (currentExpression ?: lastExpression)?.let { getImageForExpression(it) }

    LaunchedEffect(currentLine) {
        if (currentExpression != null) {
            lastExpression = currentExpression
        }
    }

    var badEndVisible by remember { mutableStateOf(false) }

    LaunchedEffect(currentLine) {
        if (currentLine == "[BAD_END]") {
            delay(500)
            badEndVisible = true
            // ⛔️ これ以降削除して！
            // delay(2500)
            // navController.navigate("initial") {
            //     popUpTo("bad1") { inclusive = true }
            // }
        }
    }


    LaunchedEffect(Unit) {
        AudioManager.stopBgm() // ← 念のため前のBGMを止める
        AudioManager.playBgm(context, R.raw.bad1bgm) // ← あなたのbad1用BGM
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(enabled = !isAnimating.value) {
                AudioManager.playSE(context, R.raw.cursor_move_se)
                if (index.value < lines.lastIndex) {
                    index.value++
                }
            }
    ) {
        // 背景
        Image(
            painter = painterResource(id = R.drawable.chibibana_background_compressed),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // 星アニメレイヤー
        AnimatedStarLayer()

        // 🎨 表情画像表示（FadeInか通常表示か分岐）
        if (currentLine == "[BAD_END]") {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        navController.navigate("initial") {
                            popUpTo("bad1") { inclusive = true }
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                // 🎨 表情：ちび花の笑顔
                Image(
                    painter = painterResource(id = R.drawable.chibibana_smile3),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(bottom = 140.dp)
                        .height(300.dp)
                )

                // 🎨 Bad Endタイトル（右下アニメ表示）
                AnimatedVisibility(
                    visible = badEndVisible,
                    enter = fadeIn(
                        animationSpec = tween(durationMillis = 1200, easing = EaseInOutCirc)
                    )) {
                    Text(
                        text = "Bad End 1　協力者？",
                        fontSize = 20.sp,
                        color = Color.White,
                        fontFamily = YuseiMagic,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(24.dp)
                    )
                }
            }
        } else if (imageResId != null) {
            val modifier = Modifier
                .align(Alignment.BottomCenter)
                .height(300.dp)

            if (fadeInTriggers.any { currentLine?.startsWith(it) == true }) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter // ✅ 位置合わせ！
                ) {
                    ChibiHanaFadeIn(
                        imageResId = imageResId,
                        appearAtLine = currentLine ?: "",
                        currentLine = currentLine ?: "",
                        onAnimationStateChange = { isAnimating.value = it }
                    )
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = imageResId),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .padding(bottom = 140.dp)
                            .height(300.dp)
                    )
                }
            }
        }


        // スキップボタン
        Text(
            text = "スキップ",
            color = Color.Gray,
            fontSize = 12.sp,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .clickable {
                    navController.navigate("preinitial") {
                        popUpTo("bad1") { inclusive = true }
                    }
                }
        )


        // セリフ表示（最後は非表示）
        if (currentLine != "[BAD_END]") {
            currentLine?.let {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(24.dp)
                        .background(Color(0xFF2B2B2B))
                        .padding(16.dp)
                ) {
                    Text(
                        text = it,
                        color = Color.White,
                        fontSize = 24.sp,
                        fontFamily = YuseiMagic
                    )
                }
            }
        }
    }
}

        @Composable
        fun AnimatedStarLayer() {
            val infiniteTransition = rememberInfiniteTransition(label = "starAlpha")
            val alpha by infiniteTransition.animateFloat(
                initialValue = 0.2f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 3500, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                ), label = "alpha"
            )

            Image(
                painter = painterResource(id = R.drawable.background_stars),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer(alpha = alpha)
            )
        }
@Composable
fun ChibiHanaFadeIn(
    imageResId: Int,
    appearAtLine: String,
    currentLine: String,
    onAnimationStateChange: (Boolean) -> Unit,
    bottomContent: @Composable () -> Unit = {}
) {
    val alpha = remember { Animatable(0f) }
    var animationStarted by remember { mutableStateOf(false) }

    LaunchedEffect(currentLine) {
        if (currentLine.startsWith(appearAtLine) && !animationStarted) {
            animationStarted = true
            onAnimationStateChange(true)
            alpha.snapTo(0f)
            delay(300)
            alpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(2000, easing = EaseInOutCirc)
            )
            onAnimationStateChange(false)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center // ✅ これで中央表示に
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(300.dp)
                    .graphicsLayer(alpha = alpha.value)
            )

            bottomContent()
        }
    }
}
