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

val lines = listOf(
    "ユーザー：（…）",
    "ユーザー：はあ…また俺は諦めちゃったのか…",
    "？？？：ちょっと！",
    /////chibibana_angry
    "ユーザー：？！",
    "？？？：あんた何やってんのよ？！",
    "ユーザー：だれ？！",
    ///chibibana_arms_crossed
    "ちび花：あたしよあたし！ちび花よ！\n",
    "ユーザー：だから誰だよ！\n",
    ////chibibana_pointing.png
    "ちび花：たちばなの！妹！あんたたちとずっと一緒にいたじゃない！\n",
    "ユーザー：？？？",
    ///chibibana_angry.png
    "ちび花：そんなことより！わたしずっと見てたけどあんたけっこう頑張ってたのになんで諦めちゃったの！ぶっ飛ばすわよ！\n",
    "（気の強い子だなぁ…）\n",
    "ユーザー：わからない…特に自分の意志で押したというわけでもないんだ…なんだか吸い寄せられたっていうか…言い訳になっちゃうけど…　",
    ///chibibana_yareyare
    "ちび花：ふーん…なんかわけわかんないこと言ってんのね…依存ってそんなもんなのかしら…って！言い訳してんじゃないわよ！\n",
    "（気が強いなぁ…）\n",
    ///chibibana_arms_crossed
    "ちび花：（…）\n",
    /////chibibana_angry
    "ちび花：依存と協力ってにてると思わない？\n",
    "ユーザー：え？\n",
    "ちび花：多分あんたが今やめたいものって、元々は何でもないし、",
    "ちび花：どっちかって言うとあなたの生きる気力の、謂わば添え木みたいなものだったと思うのよ。",
    "ちび花：働くのが大変だとか、失恋したときとか、",
    "ちび花：なにか心の隙間を埋める必要があると感じた時に始めたものが、",
    "ちび花：やがて欠かせないものじゃなかったはずなのに、いつの間にか依存していた。",
    "ちび花：鍵穴に偽物ぶっ刺してるようなもんよね。\n",
    "ユーザー：…\n",
    ////chibibana_relax
    "ちび花：依存っていうのは、枯れた井戸の底に、一人で座って“誰かが水をくれる”のを待ち続けること。\n",
    "ちび花：自分ではもう水を汲みに行けない。\n",
    "ちび花：登るのも怖いし、空も見たくない。\n",
    "ちび花：けど、「きっと誰かが降りてきて、水をくれる」って信じてる。\n",
    "ちび花：それしか望みがない。\n",
    "ちび花：たまに水をくれる人が来ると、\n",
    "ちび花：その人を“救い”やと思って、全力でしがみつく。\n",
    "ちび花：「あなたがいないと私は渇いて死ぬ」って。\n",
    "ちび花：でも本当は――\n",
    /////chibibana_angry
    "ちび花：「自分が汲みに行かない限り、この井戸に水は戻らない」\n",
    /////chibibana_angry　このあと　chibibana_cry1　三秒くらい待機　///chibibana_arms_crossed

    "ちび花：ってこと、どこかで知ってるのに。\n",
    "ちび花：それでも動けない。\n",
    "ちび花：その麻痺こそが、依存の正体。\n",
    "（ぐうの音も出ない）\n",
    "ちび花：一人で這い出る勇気が出ないならわたしとお姉ちゃんが協力してあげる。3人でなら、やめられるでしょ？\n",
    "ユーザー：君はなにかに依存してるのか？\n",
    //////chibibana_happy.png
    "ちび花：そりゃ…決まってるでしょ…お姉ちゃんよ！博識で！ギターも弾けて！かわいい！しかもたまにアホ！私の憧れよ！でも良い依存でしょ！\n",
    "ユーザー：そうなんだ…彼女もなにかに依存してるの？\n",
    ////chibibana_question
    "ちび花：さあ？お姉ちゃんがなにかに依存してるなんて想像もつかないな。",
    ////chibibana_smile.png
    "ちび花：でも昔一回あったかも！でも今は…強いて言うなら私にじゃない？共依存よ！",
    ////chibibana_happy.png
    "ちび花：でもどこぞの山椒魚とはちがうの！違うもっと良い結末を私達は迎えるの！\n",
    "（それは共依存とは違うだろ…）\n",
    "（でも彼女たちとなら乗り越えられそうだな。どれだけ辛くても。）\n",
    //////chibibana_smile2.png
    "ちび花：さあ！明日があるぞ若人よ！明るい明日が！\n",
        ////chibibana_smile3.png
    "[BAD_END]"
)

@Composable
fun Bad1Screen(navController: NavController) {

    DoubleBackToExitHandler()
    val index = remember { mutableIntStateOf(0) }
    val currentLine = lines.getOrNull(index.value)
    val isAnimating = remember { mutableStateOf(false) }
    val context = LocalContext.current

    // 🔥 表情切替リスト（全体）
    val fadeTriggers = listOf(
        "？？？：ちょっと！" to ChibiExpression.Angry,
        "？？？：あんた何やってんのよ？！" to ChibiExpression.Pointing1,
        "ちび花：あたしよあたし！ちび花よ！" to ChibiExpression.ArmsCrossed,
        "ちび花：たちばなの！妹！あんたたちとずっと一緒にいたじゃない！" to ChibiExpression.Pointing,
        "ちび花：そんなことより！わたしずっと見てたけどあんたけっこう頑張ってたのになんで諦めちゃったの！ぶっ飛ばすわよ！" to ChibiExpression.Pointing,
        "ちび花：ふーん…なんかわけわかんないこと言ってんのね…" to ChibiExpression.YareYare,
        "ちび花：（…）" to ChibiExpression.ArmsCrossed,
        "ちび花：依存と協力ってにてると思わない？" to ChibiExpression.Angry,
        "ちび花：依存っていうのは、枯れた井戸の底に、一人で座って“誰かが水をくれる”のを待ち続けること。" to ChibiExpression.Relax,
        "ちび花：「自分が汲みに行かない限り、この井戸に水は戻らない」" to ChibiExpression.Cry2,
        "ちび花：ってこと、どこかで知ってるのに。" to ChibiExpression.Cry,
        "ちび花：一人で這い出る勇気が出ないならわたしとお姉ちゃんが協力してあげる。3人でなら、やめられるでしょ？" to ChibiExpression.ArmsCrossed,
        "ちび花：そりゃ…決まってるでしょ…" to ChibiExpression.Happy,
        "ちび花：さあ？お姉ちゃんがなにかに依存してるなんて想像もつかないな。" to ChibiExpression.Question,
        "ちび花：でも昔一回あったかも！" to ChibiExpression.Smile,
        "ちび花：でもどこぞの山椒魚とはちがうの！" to ChibiExpression.Happy,
        "ちび花：さあ！明日があるぞ若人よ！明るい明日が！" to ChibiExpression.Smile2
    )

    val fadeInTriggers = listOf(
        "？？？：ちょっと！",
        "ちび花：「自分が汲みに行かない限り、この井戸に水は戻らない」",
        "ちび花：さあ！明日があるぞ若人よ！明るい明日が！"
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
            if (fadeInTriggers.any { currentLine?.startsWith(it) == true }) {
                ChibiHanaFadeIn(
                    imageResId = imageResId,
                    appearAtLine = currentLine ?: "",
                    currentLine = currentLine ?: "",
                    onAnimationStateChange = { isAnimating.value = it }
                )
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
                        fontSize = 16.sp,
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
    onAnimationStateChange: (Boolean) -> Unit
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

    if (alpha.value > 0f) {
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
                    .graphicsLayer(alpha = alpha.value)
            )
        }
    }
}

