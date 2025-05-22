package com.iruka.tachibana.ui.screens
 // ← パッケージ名は合ってる場所に

// 標準 Compose
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.widget.TimePicker
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.TextFieldValue



// Coil（GIF画像系）
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest

// Compose UIコンポーネント
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.navigation.NavController
import androidx.compose.animation.AnimatedVisibility


// Material3
import androidx.compose.material3.*

import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.zIndex
import com.iruka.tachibana.R
import kotlinx.coroutines.delay
import java.util.Calendar
import java.net.URLEncoder


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InitialScreen(modifier: Modifier = Modifier,
                  navController: NavController) {

    var showWelcome by remember { mutableStateOf(true) }
// スクロール制御・フォーカス制御
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val focusRequester = remember { FocusRequester() }
    Modifier.focusRequester(focusRequester)

    val context = LocalContext.current
    var selectedDateTime by remember { mutableStateOf("") }
    var selectedUnit by remember { mutableStateOf(TextFieldValue("")) }
    var expanded by remember { mutableStateOf(false) }
    var enteredValue by remember { mutableStateOf("") }
    var selected by remember { mutableStateOf("お金") }
    var amount by remember { mutableStateOf("") }
    val yuseiFont = FontFamily(Font(R.font.yuseimagicregular))

// 入力完了状態
    val isUnitDone = remember { mutableStateOf(false) }




    // 共通アニメーション付き修飾子
    @Composable
    fun animatedBorderModifier(isDone: State<Boolean>, color: Color): Modifier {
        val animateColor by animateColorAsState(
            targetValue = if (isDone.value) color else Color.Transparent,
            animationSpec = tween(300)
        )
        return Modifier
            .border(2.dp, animateColor, RoundedCornerShape(10.dp))
            .drawBehind {
                if (isDone.value) {
                    drawRect(
                        color = animateColor,
                        size = size,
                        alpha = 0.3f
                    )
                }
            }
    }




    Box(modifier = modifier.fillMaxSize()) {
        // 背景画像
        Image(
            painter = painterResource(id = R.drawable.background_all), // ← これ
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        if (showWelcome) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
                    .zIndex(999f),
                contentAlignment = Alignment.Center
            ) {
                // 立ち絵：上に浮かせてCardと独立
                Image(
                    painter = painterResource(id = R.drawable.initial_tachibana_welcome1),
                    contentDescription = "たちばな立ち絵",
                    modifier = Modifier
                        .size(300.dp)
                        .absoluteOffset(y = (-160).dp)
                        .zIndex(2f)
                )

                // カード本体
                Card(
                    modifier = Modifier
                        .width(320.dp)
                        .wrapContentHeight()
                        .padding(top = 100.dp) // Imageとのスペース
                        .zIndex(1f),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(
                                top = 100.dp,
                                bottom = 24.dp,
                                start = 24.dp,
                                end = 24.dp
                            ), // ← たちばな頭スペース
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val welcomeText = "来てくれて、ありがとう!\n私が断ち花。"

                        Text(
                            buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = 28.sp,
                                        color = Color(0xFFDA6E6E)
                                    )
                                ) {
                                    append(welcomeText.first())
                                }
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = 18.sp,
                                        color = Color.Black
                                    )
                                ) {
                                    append(welcomeText.drop(1))
                                }
                            },
                            fontFamily = yuseiFont
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "わたし…うれしいの。\nあなたとなら、がんばれる気がするんだ。",
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            fontFamily = yuseiFont,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(onClick = { showWelcome = false }) {
                            Text("いっしょにはじめる", fontFamily = yuseiFont)
                        }
                    }
                }
            }
        }




        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp)
        ) {
            // Aエリア（テキスト）
            Column(
                modifier = Modifier.width(196.5.dp),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Text(
                    "あなたの目標は…",
                    fontSize = 24.sp,
                    fontFamily = yuseiFont,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(45.dp))
                Text(
                    "一日で抑えられるカロリー/節約できる金額は？",
                    fontSize = 24.sp,
                    fontFamily = yuseiFont,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(45.dp))
                Text(
                    "あなたがやめると決心したのは",
                    fontSize = 24.sp,
                    fontFamily = yuseiFont,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(45.dp))
            }

            // Bエリア（入力欄）


            Column(
                modifier = Modifier
                    .width(196.5.dp)
                    .padding(start = 5.dp)
                    .verticalScroll(scrollState) // ← これ追加！
                    .bringIntoViewRequester(bringIntoViewRequester), // こっちじゃなく親に持たせるのも手
                horizontalAlignment = Alignment.Start
            ) {
                // 1行目：何？（自由入力）

                TextField(
                    value = selectedUnit,
                    onValueChange = {
                        if (it.text.length <= 15) {
                            selectedUnit = it
                            isUnitDone.value = false
                            scope.launch {
                                bringIntoViewRequester.bringIntoView()
                            }
                        }
                    },
                    placeholder = { Text("何？") },
                    modifier = Modifier
                        .width(160.dp)
                        .then(animatedBorderModifier(isUnitDone, Color(0xFFDFF4F1)))// ← ここ
                        .focusRequester(focusRequester)
                        .bringIntoViewRequester(bringIntoViewRequester)
                        .clickable { focusRequester.requestFocus() },
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        color = Color.Black,
                        fontFamily = yuseiFont
                    ),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            selectedUnit = selectedUnit.copy(selection = TextRange(0))
                            isUnitDone.value = true
                        }
                    )
                )



                Spacer(modifier = Modifier.height(15.dp))

                // 2行目：カロリー入力
                val isCalorieDone = remember { mutableStateOf(false) }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    TextField(
                        value = enteredValue,
                        onValueChange = {
                            enteredValue = it.filter { c -> c.isDigit() }
                            isCalorieDone.value = false
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Number
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                                isCalorieDone.value = true
                            }
                        ),
                        placeholder = { Text("カロリー数") },
                        modifier = Modifier
                            .width(120.dp)
                            .then(animatedBorderModifier(isCalorieDone, Color(0xFF66BB6A))), // ← ここ
                        textStyle = TextStyle(
                            fontSize = 14.sp,
                            color = Color.Black,
                            fontFamily = yuseiFont
                        ),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent
                        ),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        "kcal",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }


                Spacer(modifier = Modifier.height(15.dp))

                // 3行目：お金入力
                val isAmountDone = remember { mutableStateOf(false) }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    TextField(
                        value = amount,
                        onValueChange = {
                            amount = it.filter { c -> c.isDigit() }
                            isAmountDone.value = false
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Number
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                                isAmountDone.value = true
                            }
                        ),
                        placeholder = { Text("金額") },
                        modifier = Modifier
                            .width(120.dp)
                            .then(animatedBorderModifier(isAmountDone, Color(0xFFBBBB61))), // ← ここ
                        textStyle = TextStyle(
                            fontSize = 14.sp,
                            color = Color.Black,
                            fontFamily = yuseiFont
                        ),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent
                        ),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("円", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                }

                // 4行目：やめようと決心した日（DatePickerに戻す）
                Box(
                    modifier = Modifier
                        .width(180.dp)
                        .height(80.dp)
                        .clickable {
                            val calendar = Calendar.getInstance()
                            val datePickerDialog = DatePickerDialog(
                                context,
                                { _, year, month, day ->
                                    TimePickerDialog(
                                        context, fun(_: TimePicker?, hour: Int, minute: Int) {
                                            val pickedDateTime = Calendar.getInstance().apply {
                                                set(Calendar.YEAR, year)
                                                set(Calendar.MONTH, month)
                                                set(Calendar.DAY_OF_MONTH, day)
                                                set(Calendar.HOUR_OF_DAY, hour)
                                                set(Calendar.MINUTE, minute)
                                            }

                                            if (pickedDateTime.timeInMillis > System.currentTimeMillis()) {
                                                Toast.makeText(
                                                    context,
                                                    "未来の時間は選べません",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                return
                                            }

                                            selectedDateTime = "%04d/%02d/%02d %02d:%02d".format(
                                                year, month + 1, day, hour, minute
                                            )
                                        },
                                        calendar.get(Calendar.HOUR_OF_DAY),
                                        calendar.get(Calendar.MINUTE),
                                        true
                                    ).show()

                                },
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)
                            )
                            datePickerDialog.datePicker.maxDate =
                                System.currentTimeMillis() // ← ここで制限
                            datePickerDialog.show()
                        },
                    contentAlignment = Alignment.CenterStart
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.textbox1),
                        contentDescription = null,
                        modifier = Modifier
                            .width(220.dp)
                            .height(70.dp),
                        contentScale = ContentScale.FillBounds
                    )
                    Text(
                        text = if (selectedDateTime.isEmpty()) "いつ？" else selectedDateTime,
                        modifier = Modifier.padding(start = 12.dp),
                        color = if (selectedDateTime.isEmpty()) Color.Gray else Color.Black
                    )
                }

            }


        }


        val comments = listOf(
            "私は腹筋を一回できるようになるのが目標！！",
            "カロリーと金額は空欄でもいいよ！",
            "時間は今より過去を指定してね！",
            "目標には文字数制限があるよ！",
            "必須項目を打ち終わると物語が始まるよ。あなたと私の！"
        )
        val currentComment = remember { mutableStateOf(comments.random()) }
        LaunchedEffect(Unit) {
            while (true) {
                delay(8000L)
                var newComment: String
                do {
                    newComment = comments.random()
                } while (newComment == currentComment.value)

                currentComment.value = newComment
            }
        }

        // 吹き出し（左上 位置固定）
        Box(
            modifier = Modifier
                .absoluteOffset(x = 15.dp, y = 345.dp)
                .size(width = 234.dp, height = 124.dp),
            contentAlignment = Alignment.Center

        ) {
            Image(
                painter = painterResource(id = R.drawable.initial_fukidasi),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )

            Text(
                text = currentComment.value,
                fontSize = 14.sp,
                fontFamily = yuseiFont,
                color = Color.Black,
                lineHeight = 18.sp,
                modifier = Modifier
                    .padding(top = 10.dp, start = 24.dp, end = 24.dp)
                    .absoluteOffset(y = (-15).dp),

                )
        }
        // たちばなGIF
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(R.drawable.initlal_tachibana_situp)
                    .decoderFactory(
                        if (Build.VERSION.SDK_INT >= 28) {
                            ImageDecoderDecoder.Factory()
                        } else {
                            GifDecoder.Factory()
                        }
                    )
                    .build(),
                contentDescription = "腹筋たちばな",
                modifier = Modifier
                    .size(300.dp)
                    .padding(bottom = 32.dp)
            )
        }

        val shouldShowStart = selectedUnit.text.isNotBlank() && selectedDateTime.isNotBlank()

        AnimatedVisibility(
            visible = shouldShowStart,
            modifier = Modifier.fillMaxSize() // ← align可能にするため
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                Button(
                    onClick = {
                        val parts = selectedDateTime.split(" ", ":", "/")
                        if (parts.size >= 5) {
                            val year = parts[0].toInt()
                            val month = parts[1].toInt() - 1 // 月は0始まりにゃ！
                            val day = parts[2].toInt()
                            val hour = parts[3].toInt()
                            val minute = parts[4].toInt()

                            val calendar = Calendar.getInstance()
                            calendar.set(year, month, day, hour, minute)
                            val startTimeInMillis = calendar.timeInMillis

                            val rawUnitText = selectedUnit.text
                            val encodedUnitText = URLEncoder.encode(rawUnitText, "UTF-8") // ← ✨ navController用の安全エンコード
                            val safeAmount = amount.ifBlank { "0" }
                            val safeCalorie = enteredValue.ifBlank { "0" }

                            val sharedPref = context.getSharedPreferences("tachibana_prefs", Context.MODE_PRIVATE)

                            with(sharedPref.edit()) {
                                putLong("startTimeInMillis", startTimeInMillis)
                                putString("unitText", selectedUnit.text)
                                putString("amount", amount)
                                putString("calorie", enteredValue)
                                apply() // UIがカクつかないように非同期で保存！
                            }

                            navController.navigate("loading") {
                                popUpTo("initial") { inclusive = true }
                            }
}
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8EC3B0)),
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .absoluteOffset(x = 250.dp, y = (-300).dp)
                ) {
                    Text("はじめる", fontFamily = yuseiFont)
                }


            }
        }
    }
}