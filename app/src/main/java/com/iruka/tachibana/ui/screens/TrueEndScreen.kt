package com.iruka.tachibana.ui.screens


import android.media.MediaPlayer
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOutCirc
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.iruka.tachibana.R
import kotlinx.coroutines.delay
import com.iruka.tachibana.ui.screens.YuseiMagic
import androidx.compose.animation.core.LinearEasing
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource


@Composable
fun TrueEndScreen(navController: NavController) {
    val context = LocalContext.current
    val mediaPlayer = remember { mutableStateOf<MediaPlayer?>(null) }

 //   LaunchedEffect(Unit) {
//        val player = MediaPlayer.create(context, R.raw.true_bgm) // 必要ならtrueエンド用BGM追加
   //     player.isLooping = true
  //      player.start()
 //       mediaPlayer.value = player
//    }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.value?.release()
        }
    }

    val sceneList = listOf(
        Triple(R.drawable.bad2_1, listOf(stringResource(R.string.TrueEnd_line_0)), true),

        Triple(R.drawable.bad2_2, listOf(
            stringResource(R.string.TrueEnd_line_1),
            stringResource(R.string.TrueEnd_line_2)
        ), true),

        Triple(R.drawable.bad2_3, listOf(
            stringResource(R.string.TrueEnd_line_3),
            stringResource(R.string.TrueEnd_line_4),
            stringResource(R.string.TrueEnd_line_5),
            stringResource(R.string.TrueEnd_line_6)
        ), true),

        Triple(R.drawable.bad2_4, listOf(
            stringResource(R.string.TrueEnd_line_7),
            stringResource(R.string.TrueEnd_line_8),
            stringResource(R.string.TrueEnd_line_9),
            stringResource(R.string.TrueEnd_line_10),
            stringResource(R.string.TrueEnd_line_11)
        ), true),

        Triple(R.drawable.true1, listOf(stringResource(R.string.TrueEnd_line_12)), true),

        Triple(R.drawable.true2, listOf(stringResource(R.string.TrueEnd_line_13)), true),

        Triple(R.drawable.true3, listOf(
            stringResource(R.string.TrueEnd_line_14),
            stringResource(R.string.TrueEnd_line_15),
            stringResource(R.string.TrueEnd_line_16),
            stringResource(R.string.TrueEnd_line_17),
            stringResource(R.string.TrueEnd_line_18),
            stringResource(R.string.TrueEnd_line_19)
        ), true),

        Triple(R.drawable.true4, listOf(
            stringResource(R.string.TrueEnd_line_20),
            stringResource(R.string.TrueEnd_line_21),
            stringResource(R.string.TrueEnd_line_22)
        ), true),

        Triple(R.drawable.true5, listOf(
            stringResource(R.string.TrueEnd_line_23),
            stringResource(R.string.TrueEnd_line_24)
        ), true),

        Triple(R.drawable.true7, listOf(
            stringResource(R.string.TrueEnd_line_25),
            stringResource(R.string.TrueEnd_line_26),
            stringResource(R.string.TrueEnd_line_27)
        ), true),

        Triple(R.drawable.true8, listOf(stringResource(R.string.TrueEnd_line_28)), true),

        Triple(R.drawable.true5, listOf(stringResource(R.string.TrueEnd_line_29)), true),

        Triple(R.drawable.true7, listOf(
            stringResource(R.string.TrueEnd_line_30),
            stringResource(R.string.TrueEnd_line_31),
            stringResource(R.string.TrueEnd_line_32),
            stringResource(R.string.TrueEnd_line_33)
        ), true),

        Triple(R.drawable.true9, listOf(stringResource(R.string.TrueEnd_line_34)), true),

        Triple(R.drawable.true10, listOf(stringResource(R.string.TrueEnd_line_35)), true) // タイトル表示
    )



    var currentImageIndex by remember { mutableStateOf(0) }
    var currentLineIndex by remember { mutableStateOf(0) }
    var isAnimating by remember { mutableStateOf(false) }
    val currentTriple = sceneList[currentImageIndex]
    val imageResId = currentTriple.first
    val lines = currentTriple.second
    val changeImage = currentTriple.third
    val true2Index = sceneList.indexOfFirst { it.first == R.drawable.true2 }

    var titleVisible by remember { mutableStateOf(false) }

    val animatedImageList = listOf(
        R.drawable.bad2_1,
        R.drawable.bad2_2,
        R.drawable.true1,
        R.drawable.true2,
        R.drawable.true3,
        R.drawable.true9,
        R.drawable.true10
    )

    LaunchedEffect(currentImageIndex) {
        if (currentImageIndex == sceneList.lastIndex) {
            delay(400)
            titleVisible = true

            // 🎬 true10 表示後にエンディングロールへ遷移
            delay(4000)  // 4秒待つ
            navController.navigate("ending_roll") {
                popUpTo("true") { inclusive = true } // 遷移後に戻れないようにする
            }
        } else {
            titleVisible = false
        }
    }
    LaunchedEffect(currentImageIndex) {
        if (sceneList.getOrNull(currentImageIndex)?.first == R.drawable.true2) {
            AudioManager.stopBgm()
            AudioManager.playBgm(context, R.raw.lost)
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                if (!isAnimating) {
                    if (currentLineIndex < lines.lastIndex) {
                        currentLineIndex++
                    } else if (currentImageIndex < sceneList.lastIndex) {
                        currentImageIndex++
                        currentLineIndex = 0
                    } else {
                        // 👇 true10のときは初期画面に戻らず、ending_rollのLaunchedEffectに任せる
                        if (imageResId != R.drawable.true10) {
                            navController.navigate("initial") {
                                popUpTo("true") { inclusive = true }
                            }
                        }
                    }
                }
            },

    contentAlignment = Alignment.Center
    ) {
        // 🎨 背景画像（true2のみ）
        if (currentImageIndex >= true2Index) {
            Image(
                painter = painterResource(id = R.drawable.true2),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
        } else {
            Box(modifier = Modifier.matchParentSize().background(Color.Black))
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (changeImage && imageResId != R.drawable.true2) {

                if (imageResId in animatedImageList)  {
                    AnimatedImageFadeIn(
                        imageResId = imageResId,
                        onAnimationStateChange = { isAnimating = it },
                        bottomText = null
                    )
                } else {// true2だけスキップ
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .padding(bottom = 16.dp)
                )
            }}

            AnimatedVisibility(
                visible = true,
                enter = fadeIn(animationSpec = tween(durationMillis = 800))
            ) {
                Text(
                    text = lines[currentLineIndex],
                    color = Color.White,
                    fontFamily = YuseiMagic,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        if (currentImageIndex == sceneList.lastIndex) {
            AnimatedVisibility(
                visible = titleVisible,
                enter = fadeIn(animationSpec = tween(durationMillis = 1200, easing = EaseInOutCirc))
            ) {
                Text(
                    text = "True End　断ち花",
                    fontSize = 20.sp,
                    color = Color.White,
                    fontFamily = YuseiMagic,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(24.dp)
                )
            }
        }
    }
}

@Composable
fun AnimatedImageFadeIn(
    imageResId: Int,
    onAnimationStateChange: (Boolean) -> Unit,
    bottomText: String? = null // オプションでTextも出せる
) {
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(imageResId) {
        onAnimationStateChange(true)
        alpha.snapTo(0f)
        delay(200)
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 2200, easing = EaseInOutCirc)
        )
        onAnimationStateChange(false)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer(alpha = alpha.value)
            .padding(bottom = 16.dp)
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        )

        bottomText?.let {
            Text(
                text = it,
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = YuseiMagic
            )
        }
    }
}


@Composable
fun EndingRollScreen(onFinished: () -> Unit) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val lines = listOf(
        "断ち花", "", "キャラクター構築参考", "", "ちびばな",
        "制作者の妹との会話、人格より", "", "脚本・演出：Luka", "企画・構成：Luka", "アプリ開発：Luka", "",
        "アーネスト・ヘミングウェイ『老人と海』（The Old Man and the Sea）", "",
        "文学引用：井伏鱒二『山椒魚』", "（新潮文庫）", "",
        "※この作品には、井伏鱒二『山椒魚』から着想を得た構造が含まれています。",
        "「閉じ込めた者が、いつの間にか閉じ込められていた」という構造。", "",
        "素材提供：", "ふきだしデザイン", "",
        "Special Thanks", "しゃろう「lost」", "DOVA-SYNDROME", "On-Jin ～音人～", "",
        "制作", "Luca Verce", "", "Powered by", "ChatGPT × Kotlin × Luka × あなた", "",
        "End of Chapter", "", "断ち花", "", "Special Thanks", "あなた自身", "依存と向き合うすべての人たちへ", "",
        "この30日間、よくがんばったね！", "「何かを手放すこと」は、「何かを選び直すこと」でもある。",
        "またいつでも、“たちばな”に会いにきてください。"
    )

    LaunchedEffect(Unit) {
        delay(300) // 最初のため
        scrollState.animateScrollTo(
            scrollState.maxValue + 1000,
            animationSpec = tween(durationMillis = 60000, easing = LinearEasing)
        )
        delay(3000)
        onFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .clickable { onFinished() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(vertical = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            lines.forEach {
                Text(
                    text = it,
                    fontSize = 20.sp,
                    fontFamily = YuseiMagic,
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 6.dp)
                )
            }
        }
    }
}
