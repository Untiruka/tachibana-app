package com.iruka.tachibana.ui.screens

// ─── Android 標準 ─────────────────────
import android.content.Context
import android.os.Build
import android.util.Log

// ─── Kotlin 標準ライブラリ ──────────────
import java.util.concurrent.TimeUnit
import kotlin.math.cos
import kotlinx.coroutines.delay

// ─── Compose: 基本 ────────────────────
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// ─── Compose: UI系 ─────────────────────
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.zIndex

// ─── Compose: テキスト系 ────────────────
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily

// ─── Compose: アニメーション ────────────
import androidx.compose.animation.core.*

// ─── Compose: ナビゲーション・戻る処理 ──────
import androidx.navigation.NavController
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateColorAsState

// ─── Coil (画像読み込み) ───────────────
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder

// ─── アプリ固有データ ─────────────────
import com.iruka.tachibana.R
import com.iruka.tachibana.ui.screens.ModalType
import com.iruka.tachibana.data.*



@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    // ─── Context & フォント ─────
    val context = LocalContext.current
    val yuseiFont = FontFamily(Font(R.font.yuseimagicregular))

    // ─── 基本ステート（SharedPreferences & 時刻関連）─────
    var startTimeInMillis by remember { mutableStateOf(0L) }
    var unitText by remember { mutableStateOf("？") }
    var amountPerDay by remember { mutableStateOf(1000) }
    var caloriesPerDay by remember { mutableStateOf(200) }
    var currentMillis by remember { mutableStateOf(System.currentTimeMillis()) }

    // ─── SharedPreferences 読み込み ─────
    var prefsLoaded by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        val prefs = context.getSharedPreferences("tachibana_prefs", Context.MODE_PRIVATE)
        startTimeInMillis = prefs.getLong("startTimeInMillis", 0L)
        unitText = prefs.getString("unitText", "？") ?: "？"
        amountPerDay = prefs.getString("amount", "1000")?.toIntOrNull() ?: 1000
        caloriesPerDay = prefs.getString("calorie", "200")?.toIntOrNull() ?: 200
        prefsLoaded = true
    }

    // ─── 現在時刻を1分ごとに更新 ─────
    LaunchedEffect(Unit) {
        while (true) {
            delay(60_000)
            currentMillis = System.currentTimeMillis()
        }
    }

    // ─── 計算系（経過・目標・節約）─────
    val elapsedMillis = currentMillis - startTimeInMillis
    val elapsedDays = TimeUnit.MILLISECONDS.toDays(elapsedMillis).toInt()
    val elapsedHours = (TimeUnit.MILLISECONDS.toHours(elapsedMillis) % 24).toInt()
    val elapsedMinutes = (TimeUnit.MILLISECONDS.toMinutes(elapsedMillis) % 60).toInt()

    val millisPerWeek = 7 * 24 * 60 * 60 * 1000L
    val remainingMillis = millisPerWeek - (elapsedMillis % millisPerWeek)
    val remainingDays = TimeUnit.MILLISECONDS.toDays(remainingMillis).toInt()
    val remainingHours = (TimeUnit.MILLISECONDS.toHours(remainingMillis) % 24).toInt()
    val remainingMinutes = (TimeUnit.MILLISECONDS.toMinutes(remainingMillis) % 60).toInt()

    val savedAmount = elapsedDays * amountPerDay
    val savedCalories = elapsedDays * caloriesPerDay

    // ─── 次の目標日計算 ─────
    val goalDaysList = listOf(7, 14, 21, 30)
    val nextGoalDays =
        goalDaysList.firstOrNull { it > elapsedDays } ?: ((elapsedDays / 30 + 1) * 30)
    val daysToNextGoal = nextGoalDays - elapsedDays

    val nextGoalMillis = startTimeInMillis + TimeUnit.DAYS.toMillis(nextGoalDays.toLong())
    val remainingMillisToGoal = nextGoalMillis - currentMillis
    val remainingHoursToGoal = (TimeUnit.MILLISECONDS.toHours(remainingMillisToGoal) % 24).toInt()
    val remainingMinutesToGoal =
        (TimeUnit.MILLISECONDS.toMinutes(remainingMillisToGoal) % 60).toInt()

    // ─── コメント表示ステート ─────
    var animatedComment by remember { mutableStateOf("") }

    // ─── currentLine（台詞）状態管理 ─────
    val lineBuffer = remember { mutableStateOf<TachibanaLine?>(null) }
    val currentLine by rememberUpdatedState(lineBuffer.value)

    // ─── currentLine更新ループ ─────
// ─── currentLine更新ループ（全タイプ最初から解禁）─────
    LaunchedEffect(prefsLoaded) {
        if (prefsLoaded) {
            var lastLine: TachibanaLine? = null
            while (true) {
                val allCandidates = getMindComments() +
                        getBrainComments() +
                        getHeartComments() +
                        getLifeComments() +
                        tachibanaComments.map { TachibanaLine(it, CommentType.ExtraMind) }

                val filtered = allCandidates.filter {
                    it != lastLine && it.type != lastLine?.type
                }

                val newLine = (filtered.ifEmpty { allCandidates }).random()

                lastLine = newLine
                lineBuffer.value = newLine
                delay(10_000)
            }
        }
    }

    // ─── アニメーション表示エフェクト ─────
    LaunchedEffect(currentLine) {
        val target = currentLine?.text ?: ""
        animatedComment = ""
        for (i in target.indices) {
            animatedComment = target.substring(0, i + 1)
            delay(
                when (target[i]) {
                    '、', '，' -> 180L
                    '。', '！', '？' -> 250L
                    else -> 60L
                }
            )
        }
    }

    // ─── アニメーション: 無限遷移 ─────
    val infiniteTransition = rememberInfiniteTransition()

    // 点滅（数字の単位とか）
    val alpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    // 呼吸アニメ
    val breathing by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 5000
                0f at 0 with LinearOutSlowInEasing
                1f at 2500 with FastOutSlowInEasing
                1f at 3500
                0f at 5000 with FastOutLinearInEasing
            }
        )
    )
    val isExhaling = breathing > 0.8f


    ////桜アニメーション制御


    val opacity = remember { Animatable(1f) }

    LaunchedEffect(Unit) {
        delay(8000) // アニメ終わるタイミング（ミリ秒）
        opacity.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = 1000) // 1秒で消える
        )
    }


    val showSakura = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        // ガロウマックス級確率（例：3〜5%くらい）
        val rareChance = (1..100).random()
        if (rareChance <= 5) { // ← 5%の確率で桜演出発生
            showSakura.value = true
        }
    }


    // ─── モーダル表示制御 ─────
    var activeModal by remember { mutableStateOf<ModalType>(ModalType.None) }
    var showResetModal by remember { mutableStateOf(false) }


    fun closeModal() {
        activeModal = ModalType.None
    }

    BackHandler(enabled = activeModal != ModalType.None) {
        closeModal()
    }


    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background_all),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )


        // ─── 表示切替ステート tipsModal.kt─────
        val isTipsOpen = remember { derivedStateOf { activeModal == ModalType.Tips } }

//桜アニメーション
        if (showSakura.value) {
            val opacity = remember { Animatable(1f) }

            LaunchedEffect(Unit) {
                delay(8000)
                opacity.animateTo(0f, animationSpec = tween(1000))
            }

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(R.drawable.sakura_fall_effect_final_v5)
                    .decoderFactory(
                        if (Build.VERSION.SDK_INT >= 28) ImageDecoderDecoder.Factory()
                        else GifDecoder.Factory()
                    )
                    .allowHardware(false)
                    .crossfade(true)
                    .build(),
                contentDescription = "WebP Sakura",
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(10f)
                    .alpha(opacity.value)
            )
        }


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            Text("広告", fontSize = 12.sp)
        }
        Box(
            modifier = Modifier
                .absoluteOffset(x = 10.dp, y = 10.dp)
                .size(width = 205.dp, height = 301.dp),
            contentAlignment = Alignment.CenterStart

        ) {

            ///コメント
            Image(
                painter = painterResource(id = R.drawable.home_fukidashi),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )

            //  if (isExhaling) {
            Text(
                text = animatedComment,
                fontSize = 11.sp,
                color = Color(0xFF333333),
                fontFamily = yuseiFont,
                lineHeight = 13.sp,
                modifier = Modifier
                    .padding(start = 20.dp, top = 1.dp, end = 16.dp)
                    .zIndex(1f)
            )
            //       }
        }

        // ─── コメントボックス（※位置合わせだけ、描画はanimatedCommentで別管理）───
        Box(
            modifier = Modifier
                .absoluteOffset(x = 20.dp, y = 118.dp) // 吹き出し内にピッタリ
                .width(180.dp)
        ) {
            // コメント描画は上で完結してるので、空Boxでもレイアウト調整に必要
        }

        // ─── 表情差分画像の切り替え（currentLine.typeに応じて）───
        val tachibanaImageRes = when (currentLine?.type) {
            CommentType.Mind -> R.drawable.home_tachibana_neutral1
            CommentType.Brain -> R.drawable.home_tachibana_blush
            CommentType.Heart -> R.drawable.home_tachibana_sweat
            CommentType.Life -> R.drawable.home_tachibana_wink
            CommentType.ExtraMind -> R.drawable.home_tachibana_neutral1
            else -> R.drawable.home_tachibana_neutral1
        }

        // ─── 呼吸＋上下に揺れる動き（Y方向オフセット + スケール）───
        val cosTime by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = (2 * Math.PI).toFloat(),
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 4000, easing = LinearEasing)
            )
        )
        val offsetY = (cos(cosTime.toDouble()) * 2f).toFloat().dp
        val scaleValue = 1f + (breathing * 0.01f)

//////
        // 💡 モーダル表示：activeModal に応じて1か所に集約！
        when (activeModal) {
            ModalType.Config -> ConfigModal(onClose = ::closeModal)

            ModalType.Tips -> Box(
                modifier = Modifier.fillMaxSize()
            ) {
                TipsModal(
                    onClose = ::closeModal,
                    setComment = { /* no-op */ }
                )
            }

            ModalType.Menu -> MenuModal(onClose = ::closeModal)

            ModalType.Home -> HomeModal(onClose = ::closeModal)

            ModalType.Calendar -> {
                if (startTimeInMillis != -1L) {
                    CalendarModal(
                        onClose = { activeModal = ModalType.None },
                        selectedDateTime = startTimeInMillis
                    )
                }
            }

            else -> {} // ModalType.None など
        }

        Main_IconBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .zIndex(99f),
            onIconClick = { index ->
                activeModal = when (index) {
                    1 -> ModalType.Home
                    2 -> ModalType.Tips
                    3 -> ModalType.Calendar
                    4 -> ModalType.Menu
                    5 -> ModalType.Config
                    else -> ModalType.None
                }
            }
        )


        // ─── 立ち絵表示（GIF/WebP対応、位置アニメ付き）───
        if (activeModal != ModalType.Tips) {
            // 通常の立ち絵
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(tachibanaImageRes)
                    .decoderFactory(
                        if (Build.VERSION.SDK_INT >= 28) ImageDecoderDecoder.Factory()
                        else GifDecoder.Factory()
                    )
                    .allowHardware(false)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .absoluteOffset(x = 0.dp, y = 135.dp + offsetY)
                    .graphicsLayer {
                        scaleX = scaleValue
                        scaleY = scaleValue
                    }
                    .size(width = 250.dp, height = 550.dp)
            )
        } else {
            // Tips中はションボリver
            Image(
                painter = painterResource(id = R.drawable.tachibana_syonbori),
                contentDescription = "ションボリたちばな",
                modifier = Modifier
                    .absoluteOffset(x = 0.dp, y = 135.dp + offsetY)
                    .graphicsLayer {
                        scaleX = scaleValue
                        scaleY = scaleValue
                    }
                    .zIndex(110f)
                    .size(width = 250.dp, height = 550.dp)
            )
        }


///tipsmodal　　切り替えアニメーション

        Box(
            modifier = Modifier
                .absoluteOffset(x = 10.dp, y = 10.dp)
                .size(width = 205.dp, height = 301.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Image(
                painter = painterResource(id = R.drawable.home_fukidashi),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )

            // 元々のanimatedCommentを出すか
            if (!isTipsOpen.value) {
                Text(
                    text = animatedComment,
                    fontSize = 11.sp,
                    color = Color(0xFF333333),
                    fontFamily = yuseiFont,
                    lineHeight = 13.sp,
                    modifier = Modifier
                        .padding(start = 20.dp, top = 1.dp, end = 16.dp)
                        .zIndex(1f)
                )
            }

            // Tips表示中は上に重ねて別のふきだし＋メッセージ
            if (isTipsOpen.value) {
                Image(
                    painter = painterResource(id = R.drawable.home_fukidashi),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .zIndex(105f)
                )
                Text(
                    text = "リセットは長押しで反応するけど…そこはあまり押してほしくない…かな？",
                    fontSize = 11.sp,
                    color = Color(0xFF333333),
                    fontFamily = yuseiFont,
                    lineHeight = 13.sp,
                    modifier = Modifier
                        .padding(start = 20.dp, top = 1.dp, end = 16.dp)
                        .zIndex(120f)
                )
            }
        }

        // ─── ステータス表示（右側パネル）───
        Column(
            modifier = Modifier
                .absoluteOffset(x = 221.5.dp, y = 61.dp)
        ) {
            // 🏁 ユーザー目標タイトル
            Row {
                Text(
                    "あなたの",
                    fontSize = 12.sp,
                    color = Color(0xFF555555),
                    fontFamily = yuseiFont
                )
                Text("目標", fontSize = 14.sp, color = Color(0xFF555555), fontFamily = yuseiFont)
                Text("は…", fontSize = 12.sp, color = Color(0xFF555555), fontFamily = yuseiFont)
            }

            Spacer(modifier = Modifier.height(6.dp))

            // 📌 ユーザー設定された目標テキスト
            val displayText = if (unitText.isNotEmpty()) {
                if (unitText.length > 7) {
                    unitText.chunked(7).joinToString("\n") + "！！"
                } else {
                    unitText + "！！"
                }
            } else {
                "？！！"
            }

            val fontSize = when {
                unitText.isEmpty() -> 16.sp
                unitText.length <= 3 -> 32.sp
                unitText.length <= 6 -> 24.sp
                else -> 16.sp
            }

            Text(
                text = displayText,
                fontSize = fontSize,
                fontFamily = yuseiFont,
                color = Color(0xFF5C4630)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // 🔥 禁欲時間
            Text("禁欲時間", fontSize = 16.sp, color = Color(0xFF555555), fontFamily = yuseiFont)
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    "$elapsedDays",
                    fontSize = 24.sp,
                    color = Color(0xFFF29C9C),
                    fontFamily = yuseiFont
                )
                Text(
                    "日",
                    fontSize = 14.sp,
                    color = Color(0xFFD99494),
                    fontFamily = yuseiFont,
                    modifier = Modifier.alpha(alpha)
                )
                Text(
                    "$elapsedHours",
                    fontSize = 24.sp,
                    color = Color(0xFFF29C9C),
                    fontFamily = yuseiFont
                )
                Text(
                    "時間",
                    fontSize = 14.sp,
                    color = Color(0xFFD99494),
                    fontFamily = yuseiFont,
                    modifier = Modifier.alpha(alpha)
                )
                Text(
                    "$elapsedMinutes",
                    fontSize = 24.sp,
                    color = Color(0xFFF29C9C),
                    fontFamily = yuseiFont
                )
                Text(
                    "分",
                    fontSize = 14.sp,
                    color = Color(0xFFD99494),
                    fontFamily = yuseiFont,
                    modifier = Modifier.alpha(alpha)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            // 🎯 次の目標
            Text("次の目標", fontSize = 16.sp, color = Color(0xFF555555), fontFamily = yuseiFont)
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    "$daysToNextGoal",
                    fontSize = 24.sp,
                    color = Color(0xFFF29C9C),
                    fontFamily = yuseiFont
                )
                Text(
                    "日",
                    fontSize = 14.sp,
                    color = Color(0xFFD99494),
                    fontFamily = yuseiFont,
                    modifier = Modifier.alpha(alpha)
                )
                Text(
                    "$remainingHoursToGoal",
                    fontSize = 24.sp,
                    color = Color(0xFFF29C9C),
                    fontFamily = yuseiFont
                )
                Text(
                    "時間",
                    fontSize = 14.sp,
                    color = Color(0xFFD99494),
                    fontFamily = yuseiFont,
                    modifier = Modifier.alpha(alpha)
                )
                Text(
                    "$remainingMinutesToGoal",
                    fontSize = 24.sp,
                    color = Color(0xFFF29C9C),
                    fontFamily = yuseiFont
                )
                Text(
                    "分",
                    fontSize = 14.sp,
                    color = Color(0xFFD99494),
                    fontFamily = yuseiFont,
                    modifier = Modifier.alpha(alpha)
                )
            }


            Spacer(modifier = Modifier.height(21.dp))
            Row {
                Text("節約", fontSize = 16.sp, color = Color(0xFF555555), fontFamily = yuseiFont)
                Text("した", fontSize = 14.sp, color = Color(0xFF555555), fontFamily = yuseiFont)
                Text("お金", fontSize = 16.sp, color = Color(0xFF555555), fontFamily = yuseiFont)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "${savedAmount}円",
                fontSize = 24.sp,
                fontFamily = yuseiFont,
                color = Color(0xFF7A5E45)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text("我慢", fontSize = 16.sp, color = Color(0xFF555555), fontFamily = yuseiFont)
                Text("できた", fontSize = 14.sp, color = Color(0xFF555555), fontFamily = yuseiFont)
                Text(
                    "カロリー",
                    fontSize = 16.sp,
                    color = Color(0xFF555555),
                    fontFamily = yuseiFont
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "${savedCalories}Kcal",
                fontSize = 24.sp,
                fontFamily = yuseiFont,
                color = Color(0xFF7A5E45)
            )
            Spacer(modifier = Modifier.height(20.dp))


            /////リセットボタン
            val hoverAnim by rememberInfiniteTransition().animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 4000
                        0f at 0 with LinearEasing
                        2f at 2000
                        0f at 4000
                    }
                )
            )

            val hoverOffset = (hoverAnim - 1f).dp
            val hoverColor by animateColorAsState(
                if (showResetModal) Color.Red else Color(0xFF9E7360)
            )

            Box(
                modifier = Modifier
                    .size(width = 130.dp, height = 60.dp)
                    .offset(y = hoverOffset)
                    .graphicsLayer {
                        scaleX = 1f + (hoverAnim * 0.02f)
                        scaleY = 1f + (hoverAnim * 0.02f)
                    }
                    .background(hoverColor, shape = RoundedCornerShape(12.dp))
                    .pointerInput(Unit) {
                        detectTapGestures(onLongPress = { showResetModal = true })
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "リセット？",
                    fontFamily = yuseiFont,
                    color = Color.White
                )
            }
        }


///リセットボタン！！！！！

        if (showResetModal) {
            ModalWrapper(onClose = { showResetModal = false }) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f))
                        .zIndex(99f),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier.wrapContentSize(),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.home_tachibana_question),
                                contentDescription = null,
                                modifier = Modifier.size(150.dp)
                            )
                            Text("本当にやめるの…？", fontFamily = yuseiFont)
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = {
                                val prefs = context.getSharedPreferences(
                                    "tachibana_prefs",
                                    Context.MODE_PRIVATE
                                )
                                prefs.edit().clear().apply()
                                navController.navigate("bad1") {
                                    popUpTo("main") { inclusive = true }
                                }
                            }) {
                                Text("リセット", fontFamily = yuseiFont)
                            }

                            Spacer(modifier = Modifier.height(4.dp))
                            Button(onClick = { showResetModal = false }) {
                                Text("禁欲を続ける", fontFamily = yuseiFont)
                            }
                        }
                    }
                }
            }

        }

    }
}