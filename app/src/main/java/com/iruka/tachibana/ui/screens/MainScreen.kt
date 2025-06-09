package com.iruka.tachibana.ui.screens

// ─── Android 標準 ─────────────────────
import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.util.Log
import android.widget.Toast

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavBackStackEntry

// ─── Coil (画像読み込み) ───────────────
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder

// ─── アプリ固有データ ─────────────────
import com.iruka.tachibana.R
import com.iruka.tachibana.ui.screens.ModalType
import com.iruka.tachibana.data.*
import com.iruka.tachibana.ui.screens.AudioManager.isBgmEnabled
import com.iruka.tachibana.ui.screens.AudioManager.isSoundEnabled
import java.time.LocalDate
import com.iruka.tachibana.ui.screens.SoftHorrorBackHandler
// ─── フェッチ受け取り ─────────────────
//import com.iruka.tachibana.ui.screens.fetchCalendarEvents
import com.iruka.tachibana.util.getActivity

import com.iruka.tachibana.ui.screens.EdgeSide

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    fromEnding: Boolean = false,
    forceZeroDisplay: Boolean = false

) {
    DoubleBackToExitHandler()
    // ─── Context & フォント ─────
    val context = LocalContext.current

    val prefs = context.getSharedPreferences("tachibana_prefs", Context.MODE_PRIVATE)

    var startTimeInMillis = remember {
        if (!prefs.contains("startTimeInMillis")) {
            val now = System.currentTimeMillis()
            prefs.edit().putLong("startTimeInMillis", now).apply()
            now
        } else {
            prefs.getLong("startTimeInMillis", System.currentTimeMillis())
        }
    }


    val yuseiFont = FontFamily(Font(R.font.yuseimagicregular))



    /////BGM



    val selectedBgmType = remember {
        mutableStateOf(
            prefs.getString("selected_bgm_type", BgmType.DEFAULT.name)?.let {
                runCatching { BgmType.valueOf(it) }.getOrDefault(BgmType.DEFAULT)
            }
        )
    }


    // 🔻 Box内の最後の方（Resetモーダルのちょい上とか）に追加
    val showAfterTrueModal = remember { mutableStateOf(false) }

    LaunchedEffect(fromEnding) {
        if (fromEnding) {
            showAfterTrueModal.value = true
        }
    }
    if (showAfterTrueModal.value) {
        AlertDialog(
            onDismissRequest = { showAfterTrueModal.value = false },
            confirmButton = {
                TextButton(onClick = { showAfterTrueModal.value = false }) {
                    Text(stringResource(R.string.main_screen_goodbye))
                }
            },
            title = {
                Text(
                    stringResource(R.string.main_screen_after_true_title),
                    fontFamily = YuseiMagic,
                    fontSize = 20.sp
                )
            },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(R.drawable.after_true6),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = stringResource(R.string.main_screen_after_true_text),
                        fontFamily = YuseiMagic,
                        color = Color(0xFFFFD8D8),
                        fontSize = 16.sp,
                        lineHeight = 22.sp
                    )
                }
            },
            containerColor = Color(0xFF2C1E1E),
            titleContentColor = Color.White,
            textContentColor = Color.White
        )
    }



    // ─── googleカレンダーの予約取得─────

   // val prefs = remember { context.getSharedPreferences("tachibana_prefs", Context.MODE_PRIVATE) }
  //  val accessToken = prefs.getString("access_token", null)

 //   val eventsByDate = remember { mutableStateMapOf<LocalDate, List<String>>() }

 //   LaunchedEffect(accessToken) {
 //       if (!accessToken.isNullOrBlank()) {
 //           try {
 //               val result = fetchCalendarEvents(accessToken)
////                Log.d("CALENDAR_EVENT", "取得した予定：${result}")
 //               eventsByDate.putAll(result)
 //           } catch (e: Exception) {
 ///               Log.e("GOOGLE_CALENDAR", "予定取得失敗: ${e.message}")
 ////           }
 //       }
 //   }

    // ─── 基本ステート（SharedPreferences & 時刻関連）─────
   // var startTimeInMillis by remember { mutableStateOf(0L) }
    var unitText by remember { mutableStateOf("？") }
    var amountPerDay by remember { mutableStateOf(1000) }
    var caloriesPerDay by remember { mutableStateOf(200) }
    var currentMillis by remember { mutableStateOf(System.currentTimeMillis()) }

    // ─── SharedPreferences 読み込み ─────
    var prefsLoaded by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {

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

    val elapsedDays = if (forceZeroDisplay) 0 else TimeUnit.MILLISECONDS.toDays(elapsedMillis).toInt()
    val elapsedHours = if (forceZeroDisplay) 0 else (TimeUnit.MILLISECONDS.toHours(elapsedMillis) % 24).toInt()
    val elapsedMinutes = if (forceZeroDisplay) 0 else (TimeUnit.MILLISECONDS.toMinutes(elapsedMillis) % 60).toInt()

    val millisPerWeek = 7 * 24 * 60 * 60 * 1000L
    val remainingMillis = millisPerWeek - (elapsedMillis % millisPerWeek)

    val remainingDays = if (forceZeroDisplay) 0 else TimeUnit.MILLISECONDS.toDays(remainingMillis).toInt()
    val remainingHours = if (forceZeroDisplay) 0 else (TimeUnit.MILLISECONDS.toHours(remainingMillis) % 24).toInt()
    val remainingMinutes = if (forceZeroDisplay) 0 else (TimeUnit.MILLISECONDS.toMinutes(remainingMillis) % 60).toInt()



    val savedAmount = if (forceZeroDisplay) 0 else elapsedDays * amountPerDay
    val savedCalories = if (forceZeroDisplay) 0 else elapsedDays * caloriesPerDay

    val goalDaysList = listOf(7, 14, 21, 30)
    val nextGoalDays = if (forceZeroDisplay) 0 else {
        goalDaysList.firstOrNull { it > elapsedDays } ?: ((elapsedDays / 30 + 1) * 30)
    }
    val daysToNextGoal = if (forceZeroDisplay) 0 else nextGoalDays - elapsedDays

    val nextGoalMillis = startTimeInMillis + TimeUnit.DAYS.toMillis(nextGoalDays.toLong())
    val remainingMillisToGoal = nextGoalMillis - currentMillis
    val remainingHoursToGoal = if (forceZeroDisplay) 0 else (TimeUnit.MILLISECONDS.toHours(remainingMillisToGoal) % 24).toInt()
    val remainingMinutesToGoal = if (forceZeroDisplay) 0 else (TimeUnit.MILLISECONDS.toMinutes(remainingMillisToGoal) % 60).toInt()






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
                val specialDayComments = buildList {
                    if (elapsedDays >= 7) addAll(getDay7Comments(context))
                    if (elapsedDays >= 14) addAll(getDay14Comments(context))
                    if (elapsedDays >= 15) addAll(getDay15Comments(context))
                    if (elapsedDays >= 21) addAll(getDay21Comments(context))
                    if (elapsedDays >= 28) addAll(getDay28Comments(context))
                }

                val allCandidates = getMindComments(context) +
                        getBrainComments(context) +
                        getHeartComments(context) +
                        getLifeComments(context) +
                        specialDayComments +
                        getExtraMindComments(context)

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


    LaunchedEffect(lineBuffer.value) {
        val line = lineBuffer.value
        if (line != null) {
            // 1文字ずつアニメーション表示にしたい場合：
            animatedComment = "" // 一旦空にする
            for (char in line.text) {
                animatedComment += char
                delay(50) // 文字ごとのウェイト
            }
            // 全文一気に表示するだけなら↓これだけでOK
            // animatedComment = line.text
        }
    }



 /////   一週間周期のイベント発生

    val isInitialized = false
    val startDestination = if (isInitialized) "main_check" else "preinitial"




    fun getElapsedDays(context: Context): Int {
        val prefs = context.getSharedPreferences("tachibana_prefs", Context.MODE_PRIVATE)
        val startTime = prefs.getLong("startTimeInMillis", System.currentTimeMillis())
        val currentTime = System.currentTimeMillis()
        return TimeUnit.MILLISECONDS.toDays(currentTime - startTime).toInt()
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

/////エンディング分岐
    val isNarrativeMode = remember {
        context.getSharedPreferences("tachibana_prefs", Context.MODE_PRIVATE)
            .getBoolean("isNarrativeMode", false)
    }


    // ─── モーダル表示制御 ─────
    var activeModal by remember { mutableStateOf<ModalType>(ModalType.None) }
    var showResetModal by remember { mutableStateOf(false) }


    fun closeModal() {
        activeModal = ModalType.None
    }


    val activity = context.getActivity()

    val isSoftHorrorEnabledState = remember { mutableStateOf(prefs.getBoolean("soft_horror_enabled", false)) }
    val hasShownSoftHorror = remember { mutableStateOf(prefs.getBoolean("soft_horror_shown", false)) }

    val isSoftHorrorCondition = isSoftHorrorEnabledState.value && elapsedDays in 21..29 && !hasShownSoftHorror.value

// 🔹1. モーダル用BackHandler（最優先）
    BackHandler(enabled = activeModal != ModalType.None) {
        closeModal()
    }

// 🔹2. ソフトホラー演出中（9回目で処理する想定なら追加で↓）
    var softHorrorBackPressCount by remember { mutableStateOf(0) }
    BackHandler(enabled = isSoftHorrorCondition) {
        softHorrorBackPressCount++
        if (softHorrorBackPressCount == 9) {
            hasShownSoftHorror.value = true
            prefs.edit().putBoolean("soft_horror_shown", true).apply()
            // 🎃 ホラー演出発火処理を書く
        }
    }

// 🔹3. 通常戻る処理（ソフトホラーOFF、モーダルなし）
    val backMessages = listOf(
        stringResource(R.string.main_screen_back_message_1),
        stringResource(R.string.main_screen_back_message_2),
        stringResource(R.string.main_screen_back_message_3),
        stringResource(R.string.main_screen_back_message_4),
        stringResource(R.string.main_screen_back_message_5)
    )


    var backPressCount by remember { mutableStateOf(0) }
    BackHandler(enabled = !isSoftHorrorCondition && activeModal == ModalType.None) {
        backPressCount++
        if (backPressCount == 1) {
            val randomMessage = backMessages.random()
            Toast.makeText(activity, randomMessage, Toast.LENGTH_SHORT).show()
        } else if (backPressCount >= 2) {
            activity?.finish()
        }
    }


// 🔁 3秒以内にリセット（トースト用）
    LaunchedEffect(backPressCount) {
        if (backPressCount > 0) {
            delay(3000)
            backPressCount = 0
        }
    }


    // デバッグログ
    LaunchedEffect(Unit) {
        Log.d(
            "SoftHorrorDebug",
            "elapsedDays=$elapsedDays, enabled=${isSoftHorrorEnabledState.value}, shown=${hasShownSoftHorror.value}"
        )
    }

    //////////day3



    LaunchedEffect(forceZeroDisplay) {
        if (forceZeroDisplay) {
            delay(10000)

            val prefs = context.getSharedPreferences("tachibana_prefs", Context.MODE_PRIVATE)
            val consumed = prefs.getStringSet("consumedEvents", emptySet()) ?: emptySet()

            prefs.edit()
                .putStringSet("consumedEvents", consumed + "3")
                .apply()

            navController.navigate("event_day3") {
                popUpTo("main_intro") { inclusive = true }
            }
        }
    }
//////////////////////day25event/////////////////////////////

    val showHorrorFigure = remember { mutableStateOf(false) }
    val showLineModal = remember { mutableStateOf(false) }
    val forceOverrideRes = remember { mutableStateOf<Any?>(null) }

// softHorror条件が満たされたら演出を順に起動
    LaunchedEffect(isSoftHorrorCondition, elapsedDays) {
        if (isSoftHorrorCondition && elapsedDays in 25..30) {
            delay(10_000)

            AudioManager.stopBgm()
            forceOverrideRes.value = R.drawable.tachibana_horror // ← ホラー画像強制表示

            delay(8_000)
            showLineModal.value = true
        }
    }




    LaunchedEffect(isSoftHorrorCondition) {
        if (isSoftHorrorCondition && elapsedDays in 25..30) {
            delay(10_000)
            showHorrorFigure.value = true
        }
    }


    LaunchedEffect(showHorrorFigure.value) {
        if (showHorrorFigure.value) {
            delay(8_000)
            showLineModal.value = true
        }
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



        ////桜アニメーション制御
        val opacity = remember { Animatable(1f) }

        LaunchedEffect(Unit) {
            delay(10000) // アニメ終わるタイミング（ミリ秒）
            opacity.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 1000) // 1秒で消える
            )
        }


        val showSakura = remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            // ガロウマックス級確率（例：3〜5%くらい）
            val rareChance = (1..100).random()
            if (rareChance <= 30) { // ← 5%の確率で桜演出発生
                showSakura.value = true
            }
        }


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

        if (isSoftHorrorCondition) {
            SoftHorrorBackHandler(
                navController = navController,
                enabled = true,
                onTriggerPanicModal = { activeModal = ModalType.SoftHorrorPanic } // ←★これ
            )
        }

/////config内でスイッチ機能追加




        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {

            LaunchedEffect(activeModal) {
                if (activeModal == ModalType.SoftHorrorPanic) {
                    AudioManager.stopBgm()
                }
            }


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

        val lastLine = currentLine
        val tachibanaImageRes: Any = forceOverrideRes.value ?: when (currentLine?.type) {
            CommentType.Day7 -> R.drawable.day7
            CommentType.Day14, CommentType.Day14Rare -> R.drawable.initlal_tachibana_situp
            CommentType.Day21 -> R.drawable.day21
            CommentType.Day28 -> R.drawable.day28
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

        // ─── BGM　───


//////
        // 💡 モーダル表示：activeModal に応じて1か所に集約！





// AudioManager にも反映しておく
        val context = LocalContext.current

        val prefs = remember { context.getSharedPreferences("tachibana_prefs", Context.MODE_PRIVATE) }

        var isPrefsLoaded by remember { mutableStateOf(false) }

        val isBgmEnabledState = remember {
            mutableStateOf(prefs.getBoolean("bgm_enabled", true))
        }
        val isSoundEnabledState = remember {
            mutableStateOf(prefs.getBoolean("sound_enabled", true))
        }


        val edgeSideState = remember {
            mutableStateOf(
                if (prefs.getString("edge_side", "right") == "right") EdgeSide.Right else EdgeSide.Left
            )
        }

// ✅ 初期化完了後にAudioManagerと同期（遅延なし）
        LaunchedEffect(Unit) {
            isPrefsLoaded = true
        }

       // val currentMediaPlayer = remember { mutableStateOf<MediaPlayer?>(null) }
        val selectedBgmType = remember {
            mutableStateOf(
                prefs.getString("selected_bgm_type", BgmType.DEFAULT.name)?.let {
                    runCatching { BgmType.valueOf(it) }.getOrDefault(BgmType.DEFAULT)
                }
            )
        }
        LaunchedEffect(elapsedDays, isBgmEnabledState.value, selectedBgmType.value) {
            if (!isBgmEnabledState.value) return@LaunchedEffect

            val selected = selectedBgmType.value ?: BgmType.DEFAULT

            val actualBgm = if (selected == BgmType.DEFAULT && elapsedDays >= 21) {
                BgmType.DAY20
            } else {
                selected
            }

            AudioManager.playBgm(context, actualBgm.fileResId)
        }
        when (activeModal) {
            ModalType.Config -> ConfigModal(
                onClose = ::closeModal,
                edgeSide = edgeSideState.value,
                onEdgeSideChange = {
                    edgeSideState.value = it
                    prefs.edit().putString("edge_side", if (it == EdgeSide.Right) "right" else "left").apply()
                },
                isBgmEnabled = isBgmEnabledState.value,
                onBgmToggle = {
                    isBgmEnabledState.value = it
                    prefs.edit().putBoolean("bgm_enabled", it).apply()
                    AudioManager.isBgmEnabled = it
                    if (it) AudioManager.playBgm(context, R.raw.marinba_march)
                    else AudioManager.stopBgm()
                },
                isSoundEnabled = isSoundEnabledState.value,
                onSoundToggle = {
                    isSoundEnabledState.value = it
                    prefs.edit().putBoolean("sound_enabled", it).apply()
                    AudioManager.isSoundEnabled = it
                },
                isSoftHorrorEnabled = isSoftHorrorEnabledState.value, // ← 追加①
                onSoftHorrorToggle = {                                 // ← 追加②
                    isSoftHorrorEnabledState.value = it
                    prefs.edit().putBoolean("soft_horror_enabled", it).apply()
                },
                onCreditOpen = { activeModal = ModalType.Credit }
            )


            ModalType.Credit -> CreditModal(onClose = ::closeModal)

            ModalType.Tips -> Box(
                modifier = Modifier.fillMaxSize()
            ) {
                TipsModal(
                    onClose = ::closeModal,
                    setComment = { /* no-op */ }
                )
            }

            ModalType.Menu -> MenuModal(onClose = ::closeModal, navController = navController)

            ModalType.Home -> HomeModal(onClose = ::closeModal)

            ModalType.Calendar -> {
                if (startTimeInMillis != -1L) {
                    CalendarModal(
                        onClose = { activeModal = ModalType.None },
                        selectedDateTime = startTimeInMillis
                        // eventsByDate = eventsByDate // ← Google連携封印中
                    )
                }
            }


            ModalType.SoftHorrorPanic -> {
                AlertDialog(
                    onDismissRequest = { activeModal = ModalType.None },
                    confirmButton = {
                        TextButton(onClick = { activeModal = ModalType.None }) {
                            Text(stringResource(R.string.main_screen_soft_horror_confirm))
                        }
                    },
                    title = {
                        Text(
                            stringResource(R.string.main_screen_soft_horror_title),
                            fontFamily = yuseiFont,
                            fontSize = 18.sp,
                            color = Color.Red
                        )
                    },
                    text = {
                        Text(
                            stringResource(R.string.main_screen_soft_horror_text),
                            fontFamily = yuseiFont,
                            fontSize = 14.sp,
                            color = Color.White,
                            lineHeight = 20.sp
                        )
                    },
                    containerColor = Color.Black,
                    titleContentColor = Color.White,
                    textContentColor = Color.White
                )
            }


            else -> {} // ModalType.None など
        }

        Main_IconBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .zIndex(99f),
            onIconClick = {

                index ->
                AudioManager.playSE(context, R.raw.cursor_move_se) // ← ここに追加！
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
                    text = stringResource(R.string.main_screen_reset_warning),
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

       // 🔻 モードによる分岐：演出やセリフ表示の分離ポイント
        if (isNarrativeMode) {
            NarrativeModeContent(
                elapsedDays = elapsedDays,
                navController = navController
                // 他必要なステートを引数で
            )
        } else {
            NormalModeContent(
                elapsedDays = elapsedDays,
                navController = navController
                // 同様に
            )
        }

        // ─── ステータス表示（右側パネル）───
        Column(
            modifier = Modifier
                .absoluteOffset(x = 221.5.dp, y = 61.dp)
        ) {
            // 🏁 ユーザー目標タイトル
            Row {
                Text(
                    text = stringResource(R.string.main_screen_your),
                    fontSize = 12.sp,
                    color = Color(0xFF555555),
                    fontFamily = yuseiFont
                )
                Text(
                    text = stringResource(R.string.main_screen_goal_label),
                    fontSize = 14.sp,
                    color = Color(0xFF555555),
                    fontFamily = yuseiFont
                )
                Text(
                    text = stringResource(R.string.main_screen_goal_suffix),
                    fontSize = 12.sp,
                    color = Color(0xFF555555),
                    fontFamily = yuseiFont
                )
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
            Text(
                text = stringResource(R.string.main_screen_abstinence_label),
                fontSize = 16.sp,
                color = Color(0xFF555555),
                fontFamily = yuseiFont
            )
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = "$elapsedDays",
                    fontSize = 24.sp,
                    color = Color(0xFFF29C9C),
                    fontFamily = yuseiFont
                )
                Text(
                    text = stringResource(R.string.main_screen_days),
                    fontSize = 14.sp,
                    color = Color(0xFFD99494),
                    fontFamily = yuseiFont,
                    modifier = Modifier.alpha(alpha)
                )
                Text(
                    text = "$elapsedHours",
                    fontSize = 24.sp,
                    color = Color(0xFFF29C9C),
                    fontFamily = yuseiFont
                )
                Text(
                    text = stringResource(R.string.main_screen_hours),
                    fontSize = 14.sp,
                    color = Color(0xFFD99494),
                    fontFamily = yuseiFont,
                    modifier = Modifier.alpha(alpha)
                )
                Text(
                    text = "$elapsedMinutes",
                    fontSize = 24.sp,
                    color = Color(0xFFF29C9C),
                    fontFamily = yuseiFont
                )
                Text(
                    text = stringResource(R.string.main_screen_minutes),
                    fontSize = 14.sp,
                    color = Color(0xFFD99494),
                    fontFamily = yuseiFont,
                    modifier = Modifier.alpha(alpha)
                )
            }


            Spacer(modifier = Modifier.height(30.dp))

            // 🎯 次の目標
            Text(
                text = stringResource(R.string.main_screen_next_goal_label),
                fontSize = 16.sp,
                color = Color(0xFF555555),
                fontFamily = yuseiFont
            )

            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    "$daysToNextGoal",
                    fontSize = 24.sp,
                    color = Color(0xFFF29C9C),
                    fontFamily = yuseiFont
                )
                Text(
                    text = stringResource(R.string.main_screen_days),
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
                    text = stringResource(R.string.main_screen_hours),
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
                    text = stringResource(R.string.main_screen_minutes),
                    fontSize = 14.sp,
                    color = Color(0xFFD99494),
                    fontFamily = yuseiFont,
                    modifier = Modifier.alpha(alpha)
                )
            }

            Spacer(modifier = Modifier.height(21.dp))


            Row {
                Text(
                    text = stringResource(R.string.main_screen_saved_money_prefix),
                    fontSize = 16.sp,
                    color = Color(0xFF555555),
                    fontFamily = yuseiFont
                )
                Text(
                    text = stringResource(R.string.main_screen_saved_money_middle),
                    fontSize = 14.sp,
                    color = Color(0xFF555555),
                    fontFamily = yuseiFont
                )
                Text(
                    text = stringResource(R.string.main_screen_saved_money_suffix),
                    fontSize = 16.sp,
                    color = Color(0xFF555555),
                    fontFamily = yuseiFont
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            // 節約したお金の表示

            Text(
                "${savedAmount}円",
                fontSize = 24.sp,
                fontFamily = yuseiFont,
                color = Color(0xFF7A5E45)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text(stringResource(R.string.main_screen_saved_cal_prefix), fontSize = 16.sp, color = Color(0xFF555555), fontFamily = yuseiFont)
                Text(stringResource(R.string.main_screen_saved_cal_middle), fontSize = 14.sp, color = Color(0xFF555555), fontFamily = yuseiFont)
                Text(stringResource(R.string.main_screen_saved_cal_suffix), fontSize = 16.sp, color = Color(0xFF555555), fontFamily = yuseiFont)
            }
            Spacer(modifier = Modifier.height(8.dp))

// 節約カロリー表示
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

            val context = LocalContext.current

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
                        detectTapGestures(
                            onLongPress = {
                                AudioManager.playSE(context, R.raw.long_press_fx) // 長めSEをここで鳴らす
                                showResetModal = true
                            }
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.reset_question),
                    fontFamily = yuseiFont,
                    color = Color.White
                )
            }


        }
        val isEdgeTouchable = !(showResetModal || activeModal != ModalType.None)


// 画面右端にエッジパネルを表示（最背面配置）
        EdgePanelWithHandle(
            side = edgeSideState.value,
            isInteractionEnabled = isEdgeTouchable,
        ) {
            SampleEdgeContent()
        }





///リセットモーダル！！！！！
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
                            Text(stringResource(R.string.reset_modal_title), fontFamily = yuseiFont)
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = {
                                AudioManager.playSE(context, R.raw.cursor_move_se)
                                val prefs = context.getSharedPreferences(
                                    "tachibana_prefs",
                                    Context.MODE_PRIVATE
                                )

                                // --- ここから ---
                                val allEntries = prefs.all
                                val memoEntries = allEntries.filterKeys { it.startsWith("memo_") }
                                prefs.edit().clear().apply()
                                val editor = prefs.edit()
                                for ((k, v) in memoEntries) {
                                    when (v) {
                                        is String -> editor.putString(k, v)
                                        is Boolean -> editor.putBoolean(k, v)
                                        is Int -> editor.putInt(k, v)
                                        is Long -> editor.putLong(k, v)
                                        is Float -> editor.putFloat(k, v)
                                        is Set<*> -> editor.putStringSet(k, v as Set<String>)
                                    }
                                }
                                editor.apply()
                                // --- ここまで ---
                              //  prefs.edit().clear().apply()
                                navController.navigate("bad1") {
                                    popUpTo("main") { inclusive = true }
                                }
                            }
                            )

                            {
                                Text(stringResource(R.string.reset_button), fontFamily = yuseiFont)
                            }

                            Spacer(modifier = Modifier.height(4.dp))
                            Button(onClick = {
                                AudioManager.playSE(context, R.raw.cursor_move_se)
                                showResetModal = false
                            }) {
                                Text(stringResource(R.string.continue_button), fontFamily = yuseiFont)
                            }
                        }


                    }

                }
            }

        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(999f) // 最大値クラスのZインデックス
        ) {
        if (showLineModal.value) {
            Day25LineScreen()
        }}
    }
}



@Composable
fun SampleEdgeContent() {
    val icons = listOf(R.drawable.home_icons1, R.drawable.home_icons2)
    icons.forEach {
        Image(
            painter = painterResource(it),
            contentDescription = null,
            modifier = Modifier.size(48.dp).padding(4.dp)
        )
    }
}