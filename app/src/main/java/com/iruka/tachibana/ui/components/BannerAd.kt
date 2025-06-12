package com.iruka.tachibana.ui.components

import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.*
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize

@Composable
fun BannerAdView(modifier: Modifier = Modifier) {
    val context = LocalContext.current  // LocalContextでコンテキストを取得

    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        factory = { ctx ->
            val adView = AdView(ctx).apply {
                // setAdSizeを使用してサイズを設定する
                setAdSize(AdSize.BANNER)
                adUnitId =  "ca-app-pub-3940256099942544/6300978111"


                //    adUnitId = "ca-app-pub-8069319460842938/4764760957"
                loadAd(AdRequest.Builder().build())
            }
            adView
        },
        update = { adView ->
            // 必要なら、更新処理をここに書きます。例えば、AdRequestを再読み込みするなど。
        },
    )
}