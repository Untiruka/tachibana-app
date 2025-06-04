package com.iruka.tachibana.ui.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.iruka.tachibana.R
import kotlinx.coroutines.delay

@Composable
fun EventDay3Screen(navController: NavController) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("tachibana_prefs", Context.MODE_PRIVATE)
    val editor = prefs.edit()

    val sceneList = listOf(
        R.drawable.white_background to listOf(
            "あなた「え？！なんで？！リセットしたっけ？！」",
            "たちばな「にっしっしっし…」"
        ),
        R.drawable.day3_2 to listOf(
            "あなた「？！」",
            "たちばな「私がやりました！」",
            "あなた「えー！たちばなじゃん！なんでそんなことすんの？！ひどくない？！」"
        ),
        R.drawable.day3_1 to listOf(
            "たちばな「酷くないよ！」",
            "あなた「酷いかどうかは俺が決めるんだよ！」",
            "たちばな「いいや私が決める！酷いのはあなただ！」",
            "あなた「やめろジャイアニズム！寄り添え！俺達に！」"
        ),
        R.drawable.day3_0 to listOf(
            "たちばな「そんなことより！なんでこんなことしたかわかる？」",
            "あなた「え…なんだろう…人が積み上げてきた努力の積み重ねを崩した時の、その人の悲しそうな顔を思い出しながら食う飯がこの世で一番うまいからとか？」",
            "たちばな「どうすんのよそれでわたしが、そうだと言ったら…」",
            "あなた「皆目検討つかん！なんでこんなひどいことするんだ！」",
            "たちばな「少しおしゃべりしてほしかったからなの！だからあんなことしたの！ごめんね…？」",
            "あなた「ああ…なるほど…」",

        ),
        R.drawable.day3_3 to listOf(

            "たちばな「だってあなたここに来てから私達と一言も会話してないじゃない。」",
            "あなた「…確かに」",
            "たちばな「人見知りなの？でもどっちかって言うとそんなふうには見えないよね」",
            "あなた「まあそうだな…産まれてこのかた人見知りなんてしたこともない。そのせいかうちの弟は人見知りだ極度の」",
            "たちばな「そうなんだ…極端な遺伝子設計だね…」",
        ),

        R.drawable.day3_4 to listOf(
            "たちばな「まあでも元気そうで良かった！またね！今度は4日後とかかな？」",
            "あなた「そうなのか？」",
            "たちばな「そうだよ。あとリセットは一回押した？」",
            "あなた「知らない。なんで？」"
        )
    )

    val finalScene = listOf(
        R.drawable.day3_4 to listOf(
            "たちばな「…まあ、一回くらい押して見たほうが良いよ！じゃ、またね！」"
        )
    )

    var currentImageIndex by remember { mutableStateOf(0) }
    var currentLineIndex by remember { mutableStateOf(0) }
    var showFinalScene by remember { mutableStateOf(false) }
    val currentScenes = if (showFinalScene) finalScene else sceneList
    val (currentImage, currentLines) = currentScenes[currentImageIndex]

    val isFinalSceneEnd = showFinalScene &&
            currentImageIndex == finalScene.lastIndex &&
            currentLineIndex == finalScene.last().second.lastIndex

    val isAtLastScene = isFinalSceneEnd

    Box(
        modifier = Modifier
            .background(Color(0xFFF3F3F3))

            .fillMaxSize()
            .clickable {
                if (currentLineIndex < currentLines.size - 1) {
                    currentLineIndex++
                } else if (currentImageIndex < currentScenes.size - 1) {
                    currentImageIndex++
                    currentLineIndex = 0
                } else if (!showFinalScene) {
                    showFinalScene = true
                    currentImageIndex = 0
                    currentLineIndex = 0
                }
            },
    contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = currentImage),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().height(300.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = currentLines[currentLineIndex],
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }

    if (isAtLastScene) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    AudioManager.playSE(context, R.raw.cursor_move_se)
                    val consumed = prefs.getStringSet("consumedEvents", emptySet())?.toMutableSet() ?: mutableSetOf()
                    consumed.add("3")
                    editor.putStringSet("consumedEvents", consumed).apply()
                    navController.navigate("main")
                },
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                text = "（タップして戻る）",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(32.dp)
            )
        }
    }
}
