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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.iruka.tachibana.R
import kotlinx.coroutines.delay
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOutCirc
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.sp
import com.iruka.tachibana.ui.screens.ChibiHanaFadeIn


@Composable
fun Bad2Screen(navController: NavController) {

    val context = LocalContext.current
    var currentImageIndex by remember { mutableStateOf(0) }
    var currentLineIndex by remember { mutableStateOf(0) }
    var showFinalChoice by remember { mutableStateOf(false) }
    var isAnimating by remember { mutableStateOf(false) }

    val sceneList = listOf(
        Triple(R.drawable.bad2_1, listOf("たちばな『話があるの。』"), true),
        Triple(
            R.drawable.bad2_2, listOf(
                "たちばな『30日間お疲れ様。貴方はいろんな誘惑に打ち勝ってきたんだね。",
                "最初の方なんてわたし、仙人様がいらっしゃるのかと思った…』"
            ), true
        ),
        Triple(
            R.drawable.bad2_3, listOf(
                "たちばな『14日目に山椒魚の話もしたね。意外と可愛いんだよ。って。あと私の和装、似合ってたでしょ？』",
                "たちばな『21日目くらいから私は不安になってきた。貴方が依存症を克服したら…私から離れるんじゃないかって。",
                "だから秘技トリプルチャーハンで貴方の胃袋を掴もうとした。結果貴方は大絶賛。握りつぶしたようなもんだよね。』"
            ), true
        ),
        Triple(
            R.drawable.bad2_4, listOf(
                "28日目私はどうにか自分の気持を抑えようとした…でもだめだった。貴方がいなくなるくらいなら…",
                "一生依存症に苦しんで、私と一緒に窟の中で暮らしてほしかった。蛙を閉じ込めた山椒魚みたいに…だからあんな真似を…",
                "でもあなたは依存症を克服した…すべてを乗り越えた…"
            ), false
        ),
        Triple(
            R.drawable.bad2_5, listOf(
                "と思った？",
                "いや違うよね。思ってないよね。あなたは自分の依存症を乗り越えられたあとに、私に依存しただけ。依存先を乗り換えただけ。",
                "最初からこうすればよかったんだよ…私、貴方のことが好き。そして私のことが好きな貴方は私に依存した。私も貴方に依存するの。"
            ), false
        ),
        Triple(
            R.drawable.bad2_6, listOf(
                "蛙が逃げられないと思った？そんなわけないじゃない。山椒魚が寝てる間に出ていけばいいじゃない。多分居心地が良かったんだよ。思いの外",
                "そして貴方も思ったんでしょ？存外、ここは居心地がいいって。",
                "いつでも逃げられたはずなのに、のこのこ、私に会いに戻って今このハッピーエンディングを迎えているのだから。"
            ), false
        ),
        Triple(R.drawable.bad2_7, listOf(""), false),
        Triple(
            R.drawable.bad2_8, listOf(
                "私、幸せ…ありがとう…なにかに依存してくれて…それを克服しようとして…それで私達は出会えた。",
                "これからも、ずーと、すーっと一緒だよ"
            ), true
        ),
        Triple(R.drawable.bad2_end, listOf("BAD END 水籠の蛙と山椒魚"), true)
    )

    val (currentImage, currentLines, animateThisImage) = sceneList[currentImageIndex]
    val currentLine = currentLines.getOrNull(currentLineIndex)

    LaunchedEffect(currentLine) {
        if (currentLine ==  "と思った？") {
            AudioManager.stopBgm()
            AudioManager.playBgm(context, R.raw.bad2_1)
           // AudioManager.playSfx(context, R.raw.wind_howl) // 風の効果音を再生
        }
    }


    LaunchedEffect(Unit) {
        AudioManager.stopBgm() // ← 念のため前のBGMを止める
        AudioManager.playBgm(context, R.raw.bad1bgm) // ← あなたのbad1用BGM
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEEE8DD))
            .clickable(enabled = !isAnimating && !showFinalChoice) {
                AudioManager.playSE(context, R.raw.cursor_move_se)
                if (currentLineIndex < currentLines.lastIndex) {
                    currentLineIndex++
                } else if (currentImageIndex < sceneList.lastIndex) {
                    currentImageIndex++
                    currentLineIndex = 0
                } else {
                    showFinalChoice = true
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
            // 画像
            if (animateThisImage) {
                ChibiHanaFadeIn(
                    imageResId = currentImage,
                    appearAtLine = currentLine ?: "",
                    currentLine = currentLine ?: "",
                    onAnimationStateChange = { isAnimating = it },
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
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = currentImage),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.height(300.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    if (!showFinalChoice) {
                        currentLine?.let {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 24.dp)
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
                }


        }
        }

        // 🔘 最後の選択肢（そのままでOK）
        if (showFinalChoice) {
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