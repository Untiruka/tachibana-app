package com.iruka.tachibana.ui.screens


import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iruka.tachibana.R
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.decode.ImageDecoderDecoder
import coil.decode.GifDecoder

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.iruka.tachibana.data.getMamaCommentsForState
import kotlinx.coroutines.delay
import androidx.compose.ui.text.font.Font
import com.iruka.tachibana.ui.screens.YuseiMagic


@Composable
fun ShopScreen(onBack: () -> Unit,
               elapsedDays: Int
               ) {
    // --- ステート ---
    var mamaState by remember { mutableStateOf(MamaUiState.Eating) }
    var purchasedItems by remember { mutableStateOf(setOf<String>()) }

    // --- コメントリスト＆ランダム選択 ---
    val context = LocalContext.current
    val randomComment = remember { mutableStateOf("") }
    val displayedComment = remember { mutableStateOf("") }

    val yuseiFont = FontFamily(Font(R.font.yuseimagicregular))  // yusei.ttfを指定

    // --- アイテムリスト ---
    val allItems = listOf(
        Item("広告削除", R.drawable.remove_ad),
        Item("エピソード１", R.drawable.episode_1)
    )
    val items = allItems.map { item ->
        if (purchasedItems.contains(item.name)) {
            Item(item.name, R.drawable.sold_out)
        } else {
            item
        }
    }

    // --- コメントリスト＆ランダム選択 ---


    LaunchedEffect(mamaState) {
        // コメントリストを動的に取得し、ランダムに選択
        val commentsForState = getMamaCommentsForState(context, mamaState)
        randomComment.value = commentsForState.randomOrNull()?.text ?: ""
    }




    // --- ランダム切り替えのための効果 ---
    LaunchedEffect(elapsedDays) {
        while (true) {
            delay(10_000)
            val validStates = getMamaUiStatesForShop(elapsedDays)
            mamaState = validStates.random()
        }
    }
    LaunchedEffect(randomComment.value) {
        for (i in 0 until randomComment.value.length) {
            delay(30)  // 文字が表示されるスピード（ミリ秒）
            displayedComment.value = randomComment.value.take(i + 1)
        }
    }

    // --- ランダム切り替えのための効果 ---
    LaunchedEffect(elapsedDays) {
        while (true) {
            delay(10_000)
            // 節目ごとに有効なMamaUiStateだけからランダム選択
            val validStates = getMamaUiStatesForShop(elapsedDays)
            mamaState = validStates.random()
        }
    }
    val imageRes = when (mamaState) {
        MamaUiState.Eating -> R.drawable.mama_eating
        MamaUiState.Cute -> R.drawable.mama_cute
        MamaUiState.ByeBye -> R.drawable.mama_byebye
        MamaUiState.Banzai -> R.drawable.mama_banzai
        MamaUiState.FriedMask -> R.drawable.mama_fried_mask    // 7日目以降
        MamaUiState.Relax -> R.drawable.mama_relax              // 14日目以降
        MamaUiState.Shy -> R.drawable.mama_shy                  // 21日目以降
    }

    // --- メインレイアウト ---
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // 背景画像（最背面）
        Image(
            painter = painterResource(id = R.drawable.shop_bar_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize().zIndex(0f)
        )

        // 吹き出しコメント（右上固定、他UIに干渉しない）
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 24.dp, end = 16.dp)
                .width(250.dp)
                .height(100.dp)
                .zIndex(3f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.home_fukidashi),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        rotationY = 180f         // 左右反転
                        scaleX = 1.3f            // 横方向に1.5倍
                        scaleY = 1.5f            // 縦方向に1.5倍
                    }
            )
            Text(
                text = displayedComment.value,  // ここで文字を順番に表示
                fontSize = 12.sp,
                fontFamily = yuseiFont,
                color = Color(0xFF333333),
                lineHeight = 14.sp,
                modifier = Modifier
                    .padding(start = 20.dp, top = 6.dp, end = 12.dp)
                    .zIndex(1f)
            )
        }
        // ママのWebPアニメ or 画像
        Spacer(modifier = Modifier.height(50.dp))
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageRes)
                .decoderFactory(
                    if (Build.VERSION.SDK_INT >= 28) ImageDecoderDecoder.Factory()
                    else GifDecoder.Factory()
                )
                .allowHardware(false)
                .crossfade(true)
                .build(),
            contentDescription = "ママの状態",
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .zIndex(2f)
                .offset(x = 70.dp, y = 150.dp)
                .shadow(10.dp, RoundedCornerShape(12.dp)) // 影を加える

        )

        // アイテム一覧部分（下層に設置）
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 440.dp) // ママ画像の下にずらす
                .zIndex(1f)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text( (stringResource(R.string.PaidContentList)), fontSize = 20.sp,fontFamily = yuseiFont, color = Color.White)
            Spacer(modifier = Modifier.height(18.dp))

            // テーブル背景＋アイテム表示
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.shop_bar_table),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize().zIndex(1f)
                )

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 50.dp, bottom = 8.dp)  // topも追加
                        .zIndex(2f)
                ) {
                    items(items) { item ->
                        ItemCard(item = item, onClick = {
                            if (!purchasedItems.contains(item.name)) {
                                purchasedItems = purchasedItems + item.name
                                mamaState = MamaUiState.Cute
                            }
                        })
                    }
                }
                // フッター（最前面・下固定）
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .align(Alignment.BottomCenter)
                        .zIndex(3f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 戻るボタン
                    IconButton(onClick = {
                        mamaState = MamaUiState.ByeBye
                        onBack()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "戻る")
                    }

                    // 「そよ風堂P」のテキストもクリック可能に
                    Text(
                        (stringResource(R.string.soyokazedou)),
                        fontSize = 26.sp,
                        fontFamily = yuseiFont,
                        color = Color.White,
                        modifier = Modifier.clickable {
                            mamaState = MamaUiState.ByeBye
                            onBack()
                        }
                    )

        }}}
    }
}

fun getMamaUiStatesForShop(elapsedDays: Int): List<MamaUiState> {
    return buildList {
        addAll(listOf(MamaUiState.Eating, MamaUiState.Cute, MamaUiState.ByeBye, MamaUiState.Banzai))
        if (elapsedDays >= 7) add(MamaUiState.FriedMask)
        if (elapsedDays >= 14) add(MamaUiState.Relax)
        if (elapsedDays >= 21) add(MamaUiState.Shy)
    }
}

// --- アイテムカード表示 ---
@Composable
fun ItemCard(item: Item, onClick: () -> Unit) {
    val yuseiFont = FontFamily(Font(R.font.yuseimagicregular))  // yusei.ttfを指定


    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() }
            .width(120.dp)
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = item.image),
            contentDescription = item.name,
            modifier = Modifier.size(80.dp)
        )
        Text(item.name,
            fontSize = 15.sp,
            fontFamily = yuseiFont,
            color = Color.White,
            )
    }
}

// --- データクラス ---
data class Item(val name: String, val image: Int)

enum class MamaUiState {
    Eating, Cute, ByeBye, Banzai,
    FriedMask, Relax, Shy  // ← 新規追加（画像ファイル名ベース例）
}
