package com.iruka.tachibana.ui.screens


import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.iruka.tachibana.R
import com.iruka.tachibana.ui.screens.YuseiMagic

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iruka.tachibana.ui.screens.YuseiMagic





@Composable
fun EventDay21Screen(navController: NavController) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("tachibana_prefs", Context.MODE_PRIVATE)
    val editor = prefs.edit()

    val sceneList = listOf(
        R.drawable.day21_ to listOf(
            "",
            "たちばな「おかえり！今日もお疲れ様！あ、今日の夜ごはん、ちょっとがんばって作ってみた。 お風呂も自動で沸かしておいたから」",
            "あなた「ありがとう」",
            "彼女のエプロン姿は新鮮だ。",
            "たちばな「今日は私が料理作ってあげるね！楽しみにしてて！」",

        ),
        R.drawable.day21_1 to listOf(
            "",
            "あなた「…なにしてんの？」",
            "たちばな「何って何？」",
            "あなた「なんでまな板、床に直置きなの？しかも正座で」",
            "たちばな「なんでって…日本人だからだよ…」",
            "見たことないぞまな板を床に直置きするやつなんて！",
            "あと日本関係ねーよ！"
        ),
        R.drawable.day21_2 to listOf(
            "",
            "あなた「…なにしてんの？」",
            "たちばな「何って何？」",
            "同じ時を繰り返してるのか？",
            "あなた「なんで正座のまま卵を割ってるの？」",
            "たちばな「なんでって…チャーハンを作るからよ…」",
            "卵を割る理由は問うてないんだよ…",
        ),
        R.drawable.day21_3 to listOf(
            "",
            "あなた「…なにしてんの？」",
            "たちばな「何って何？」",
            "やっぱり同じ時を繰り返してる！！",
            "あなた「…いやてゆーか危ないよ！正座のまま続けるきなん？！」",
            "たちばな「まあまあ…そっちで黙ってみてなさい…」",
        ),
        R.drawable.day21_4 to listOf(
            "",
            "…すごい…正座のままノールックでフライ返しをしている…あと両手でフライパン持ってるやつも初めて見た…",
            "たちばな「完成だよ！」"
        ),
        R.drawable.day21_5 to listOf(
            "あなた「なんで3つもチャーハンがあるの？」",
            "たちばな「なんでって？一つでもあれば幸せなのに、トリプルチャーハンなんて幸せ3倍じゃん」",
            "あなた「あとなんで床に直おきなの？」",
            "たちばな「それは日本人だからだよ、正座で食べなきゃ、日本人は」",
            "あなた「君はれんげで食べるの？？」",
            "たちばな「当り前じゃない。お箸だと食べづらいし。あなたは日本人じゃん。私もだけど。」",
            "あなた「野菜は？」",
            "たちばな「いらない。私には野菜の悲鳴が聞こえるの。主菜チャーハン、副菜チャーハン、スープもチャーハン」",
            "あなた「北京ダックの悲鳴は？」",
            "たちばな「美味しいんだから仕方ないじゃない…悲鳴ごと噛みしめるの」",
        ),
        R.drawable.day21_5_5 to listOf(
            "",
            "あなた「あとこれ何？」",
            "たちばな「飲むチャーハンだよ。「ゼンブ入れ歯ん」ってコンビニで売ってた」",
            "あなた「絶句なんだけど」",
            "たちばな「ローマ字で「NOMU　CHAHAN」って書いてるの最高にバカだよね。誰に伝える用なん？っていう。綴り違うし」」",
             "ほんとだ…これだとチャハンだ…",
            "あなた「…トリプルチャーハン…食べきれるかな…飲み物もチャーハンだし…」",
            "たちばな「安心して。味はそれぞれ違うの。しかも全部ちゃんと美味しいから」",
            "あなた（本当だ…！どれこれも画期的にうまい！あとこの飲むチャーハンも最高にうまい！なんで！？）"
        ),
        R.drawable.day21_6 to listOf(
            "",
            "たちばな「おいしかった？」",
            "あなた「うん信じられないくらい。なんで？すごくない？」",
            "たちばな「まあチャーハンつくりって凝りだすと止まらないんだよね…次はアイスチャーハンにチャレンジしたいな。」",
            "…実績って大事なんだな。彼女ならやり遂げられそうだ。狂気のアイスチャーハン…",
            "あなた「ご馳走様。こんなにおいしいチャーハンは初めてだ。皿は僕が洗っておくよ」",
            "たちばな「大丈夫だよ！わたしがやっておくから！」"
        ),
        R.drawable.day21_7 to listOf(
            "たちばな「あ。そうそう！」",
            "全部用意してあるからね！明日の準備も！靴も、明日ちゃんと歩きやすいように揃えてあるし、部屋の湿度も調整済み。",
            "照明の色温度は今の副交感神経に合うようにしてるから。",
            "で、たぶん今日の発汗量だと、後頭部からつま先の末節骨まで洗い終わるのに、平均でだいたい20分くらいだよね？",
        ),
        R.drawable.day21_8 to listOf(

        "だから……あなたのお風呂は8時55分には終わってると思う。",

    ),
        R.drawable.day21_8_1 to listOf(

            "それから、YouTubeでお気に入りのドラマ観るんだよね？",

        ),
        R.drawable.day21_8_2 to listOf(


            "……でもあれ、あんまり観すぎるの、脳波的に良くないよ？",

        ),
        R.drawable.day21_8_3 to listOf(


            "メラトニンが抑制されるし、特に他の女の人が映るよね？",

        ),
        R.drawable.day21_8_4 to listOf(


            "他の女の人もメラトニンを抑制するんだよ？",
        ),
        R.drawable.day21_8 to listOf(


            "エクスペンタブルズとか男臭い映画だけだからね見ていいのは！",
            "あ！でもミーガンフォックスが出るところは飛ばして！きれいなおんなのひとだから！",
            "明日も明後日も棺桶に入るその日まで私がずーーーーーーーーとおせわしていくから！それが一番いいから！",
            "……ねえ、わたし、これからもずっと、そうやってお世話してていいよね？"
        ),
    )
    // BGM再生
    val mediaPlayer = remember { mutableStateOf<MediaPlayer?>(null) }

    var currentImageIndex by remember { mutableStateOf(0) }
    var currentLineIndex by remember { mutableStateOf(0) }
    var showFinalChoice by remember { mutableStateOf(false) }

    val (currentImage, currentLines) = sceneList[currentImageIndex]


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEEE8DD))
            .clickable(enabled = !showFinalChoice) {
                AudioManager.playSE(context, R.raw.cursor_move_se)
                if (currentLineIndex < currentLines.lastIndex) {
                    currentLineIndex++
                } else if (currentImageIndex < sceneList.lastIndex) {
                    currentImageIndex++
                    currentLineIndex = 0
                } else {
                    showFinalChoice = true
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
                fontFamily = YuseiMagic,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                fontSize = 20.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

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
                        prefs.edit().putBoolean("bad2_selected", true).apply()
                        val consumed = prefs.getStringSet("consumedEvents", emptySet())?.toMutableSet() ?: mutableSetOf()
                        consumed.add("21")
                        editor.putStringSet("consumedEvents", consumed).apply()
                        navController.navigate("main") { popUpTo("main") { inclusive = true } }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("そうだね、僕は嬉しいよ", fontFamily = YuseiMagic)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        val consumed = prefs.getStringSet("consumedEvents", emptySet())?.toMutableSet() ?: mutableSetOf()
                        consumed.add("21")
                        editor.putStringSet("consumedEvents", consumed).apply()
                        navController.navigate("main") { popUpTo("main") { inclusive = true } }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("無理しなくていいんだよ", fontFamily = YuseiMagic)
                }
            }
        }
    }

    // 任意で右上にスキップボタン
    Debug.SkipButton(day = "21", navController = navController, context = context)





// ❶ 音楽を流す処理（画像が day21_7 に来たとき）
    LaunchedEffect(Unit) {
        AudioManager.playBgm(context, R.raw.day21_music)
    }


// ❷ 音楽を止める処理（セリフがあの一文のとき）
    LaunchedEffect(currentImageIndex, currentLineIndex) {
        val currentImage = sceneList[currentImageIndex].first
        val currentText = sceneList[currentImageIndex].second.getOrNull(currentLineIndex)

        if (currentImage == R.drawable.day21_8 && currentText == "だから……あなたのお風呂は8時55分には終わってると思う。") {
            AudioManager.stopBgm()
        }
    }


    DisposableEffect(Unit) {
        onDispose {
            AudioManager.stopBgm()
        }
    }



}



object Debug {

    fun toast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun log(tag: String = "DEBUG", message: String) {
        Log.d(tag, message)
    }

    fun resetPrefs(context: Context) {
        val prefs = context.getSharedPreferences("tachibana_prefs", Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
    }

    fun printConsumed(context: Context) {
        val prefs = context.getSharedPreferences("tachibana_prefs", Context.MODE_PRIVATE)
        val consumed = prefs.getStringSet("consumedEvents", emptySet())
        log("ConsumedEvents", consumed.toString())
        toast(context, "Consumed: $consumed")
    }

    fun printBad2Flag(context: Context) {
        val prefs = context.getSharedPreferences("tachibana_prefs", Context.MODE_PRIVATE)
        val flag = prefs.getBoolean("bad2_selected", false)
        log("Bad2Selected", flag.toString())
        toast(context, "Bad2: $flag")
    }

    @Composable
    fun SkipButton(day: String, navController: NavController, context: Context) {
        val prefs = context.getSharedPreferences("tachibana_prefs", Context.MODE_PRIVATE)
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopEnd
        ) {
            TextButton(
                onClick = {
                    val consumed = prefs.getStringSet("consumedEvents", emptySet())?.toMutableSet()
                        ?: mutableSetOf()
                    consumed.add(day)
                    prefs.edit().putStringSet("consumedEvents", consumed).apply()
                    navController.navigate("main") { popUpTo("main") { inclusive = true } }
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("スキップ", fontFamily = YuseiMagic)
            }
        }
    }
    fun getStartDestination(isDebug: Boolean, isInitialized: Boolean): String {
        return if (isDebug) {
            "event_day_21"  // ← 修正ポイント
        } else {
            if (isInitialized) "main_check" else "preinitial"
        }
    }
}
