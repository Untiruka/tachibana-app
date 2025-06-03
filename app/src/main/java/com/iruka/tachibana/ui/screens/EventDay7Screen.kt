package com.iruka.tachibana.ui.screens

import android.content.Context
import androidx.benchmark.perfetto.PerfettoConfig
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EventDay7Screen(navController: NavController) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("tachibana_prefs", Context.MODE_PRIVATE)

    val lines = listOf(
        "おーいこっちだよー見えるか？",
        "おーい、生きてる～？",
        "ちゃんと水飲んでる？",
        "空気吸ってる？",
        "なんかもう、仙人みたいな顔してない？",
        "そんなにやめるの大変？私の妹もやめるの大変そうだったなー…アイスクリーム…異常によく食べるの…",
        "この前とか知らないおじさんに買ってもらってたんだから…しかもオーストラリアで…",
        "あっちだと物価が高くて…それで妹がアイスボックスの前でなやんでたら…",
        "おじさん「お嬢ちゃん…アイスクリームも高いよな…」",
        "おじさん「おじさんが買ってやるよ！」って買ってもらったらしい。もちろん英語で。",
        "かわいいから仕方ないよね…買ってあげたくなる気持ちもわかるけど",
        "ちなみに今は虫歯の時に歯医者さんに歯を削り取られる恐怖が勝って完全にアイスをやめれた。痛みを知るってだいじなのかも…",
        "君も痛みを知るべきなんじゃない？何なら私が痛みを与えてあげようか…？人間には明確な弱点があるよね…",
        "……あはは、冗談だってば。7日目、おつかれさま！"
    )

    val images = listOf(
        R.drawable.day7_image_1,
        R.drawable.day7_image_2,
        R.drawable.day7_image_3
    )

    var currentIndex by remember { mutableStateOf(0) }
    val imageRes = when (currentIndex) {
        in 0..2 -> images[0]
        in 3..9 -> images[1]
        else -> images[2]
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F3F3))
            .clickable {
                AudioManager.playSE(context, R.raw.cursor_move_se)
                if (currentIndex < lines.lastIndex) {
                    currentIndex++
                } else {
                    val consumed = prefs.getStringSet("consumedEvents", emptySet())?.toMutableSet() ?: mutableSetOf()
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
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .alpha(1f)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "たちばな「${lines[currentIndex]}」",
                color = Color(0xFF333333),
                fontSize = 20.sp,
                fontFamily = YuseiMagic,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
