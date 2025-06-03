package com.iruka.tachibana.ui.screens


import android.content.Context
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
import com.iruka.tachibana.ui.screens.yuseiFont
import kotlinx.coroutines.delay

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun EventDay14Screen(navController: NavController) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("tachibana_prefs", Context.MODE_PRIVATE)
    val editor = prefs.edit()

    val sceneList = listOf(
        R.drawable.day_14_image to listOf(
            "たちばなが本を読んでいる…しかも和装で…珍しい…",
            "あなた「何にしてんの？」",
            "たちばな「小説を読んでる、『井伏鱒二』さんの著作」",
            "あなた「なんで和装なの？」",
            "たちばな「なんていうか…私なりの敬意とでもいうべきか…。",
            "魂がこもった作品の前では正装をすべきなのではと…パーティー会場にサンダルでいかないのと一緒かな」"
        ),
        R.drawable.day_14_image_1 to listOf(
            "あなた「どういう話なの？」",
            "たちばな「絵本みたいに楽しめるのに…なんか今の社会情勢と被ってて、童話風預言書って感じの本なんだよね…まあ当時ともかぶっていたのかもしれないけど。山椒魚ていうんだ」",
            "たちばな「ちなみに名前の由来は確か実際に山椒の匂いがするからラシイネ」",
            "「あと西洋では火を吐くというデマもあった。まあ見た目もリザードマンだしねほぼ。」",
            "たちばな「本来のサンショウウオは多分岩屋に頭つっかえるほどでかくなるとは考えにくいけど、",
            "「オオサンショウウオだったらあり得るかもね。最大150cmなるんだって。すっごいかわいいんだよ。なんでも食べる肉食爬虫類なんだけど。」"
        ),
        R.drawable.day_14_image2 to listOf(
            "たちばな「ねー？",
            "その小説では山椒魚がカエルを岩屋に閉じ込めるんだ…八つ当たりみたいな感じで。最後にカエルは死んじゃうんだけど…」",
            "「今でも別に、おまえのことを怒ってはいないんだ。ってカエルは言う",
            "私があなたを閉じ込めても怒らない？？"
        )
    )

    val finalScene = listOf(
        R.drawable.day_14_image3 to listOf(
            "たちばな「そう…」",
            "たちばな「この山椒魚ってさ、閉じ込めた方も、閉じ込められた方も、どっちも出られなくなるんだよ」",
            "たちばな「……それって、なんか、依存と似てない？」"
        )
    )

    var currentImageIndex by remember { mutableStateOf(0) }
    var currentLineIndex by remember { mutableStateOf(0) }
    var showFinalScene by remember { mutableStateOf(false) }
    val currentScenes = if (showFinalScene) finalScene else sceneList
    val (currentImage, currentLines) = currentScenes[currentImageIndex]

    val isChoiceScene = !showFinalScene &&
            currentImageIndex == sceneList.lastIndex &&
            currentLineIndex == sceneList.last().second.lastIndex

    val isFinalSceneEnd = showFinalScene &&
            currentImageIndex == finalScene.lastIndex &&
            currentLineIndex == finalScene.last().second.lastIndex

    val isAtLastScene = isChoiceScene || isFinalSceneEnd


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEEE8DD))
            .clickable {
                AudioManager.playSE(context, R.raw.cursor_move_se)
                if (currentLineIndex < currentLines.size - 1) {
                    currentLineIndex++
                } else if (currentImageIndex < currentScenes.size - 1) {
                    currentImageIndex++
                    currentLineIndex = 0
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = currentImage),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = currentLines[currentLineIndex],
                fontFamily = yuseiFont,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }

    if (!showFinalScene && currentImageIndex == sceneList.lastIndex && currentLineIndex == currentLines.lastIndex) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    showFinalScene = true
                    currentImageIndex = 0
                    currentLineIndex = 0
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("うん、君になら僕は喜んで…", fontFamily = yuseiFont)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    showFinalScene = true
                    currentImageIndex = 0
                    currentLineIndex = 0
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("いや、僕は自立したいな？", fontFamily = yuseiFont)
            }
        }
    }



    if (isAtLastScene && !isChoiceScene) {
        // → 選択肢の時点では表示しない！確実に「最後の行まで進んだあとだけ」
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    AudioManager.playSE(context, R.raw.cursor_move_se)
                    val consumed = prefs.getStringSet("consumedEvents", emptySet())?.toMutableSet() ?: mutableSetOf()
                    consumed.add("14")
                    editor.putStringSet("consumedEvents", consumed).apply()
                    navController.navigate("main")
                },
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                text = "（タップして戻る）",
                fontFamily = yuseiFont,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(32.dp)
            )
        }
    }


}

