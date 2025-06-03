package com.iruka.tachibana.ui.screens


import android.content.Context
import android.media.MediaPlayer
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.foundation.text.ClickableText
import androidx.compose.ui.platform.LocalUriHandler

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.iruka.tachibana.R
import com.iruka.tachibana.ui.screens.YuseiMagic


enum class BgmType(val label: String, val fileResId: Int) {
    DEFAULT("通常", R.raw.marinba_march),
    DAY20("21日目", R.raw.day20);
}

@Composable
fun ConfigModal(
    onClose: () -> Unit,
    edgeSide: EdgeSide,
    onEdgeSideChange: (EdgeSide) -> Unit,
    isBgmEnabled: Boolean,
    onBgmToggle: (Boolean) -> Unit,
    isSoundEnabled: Boolean,
    onSoundToggle: (Boolean) -> Unit,
    isSoftHorrorEnabled: Boolean,
    onSoftHorrorToggle: (Boolean) -> Unit,
    onCreditOpen: () -> Unit,
) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("tachibana_prefs", Context.MODE_PRIVATE)
    val elapsedDays = prefs.getInt("elapsed_days", 0)

    val selectedBgmType = remember {
        val saved = prefs.getString("selected_bgm_type", BgmType.DEFAULT.name)
        val allowed = BgmType.values().filter {
            it != BgmType.DAY20 || elapsedDays >= 21
        }
        mutableStateOf(allowed.find { it.name == saved } ?: BgmType.DEFAULT)
    }

    val currentMediaPlayer = remember { mutableStateOf<MediaPlayer?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(999f)
    ) {
        Image(
            painter = painterResource(id = R.drawable.config_backgroound1),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("設定", style = MaterialTheme.typography.headlineMedium, fontFamily = YuseiMagic, color = Color.White)

            // ✅ ON/OFFトグル
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("BGM有効化", modifier = Modifier.weight(1f), fontFamily = YuseiMagic, color = Color.White)
                Switch(checked = isBgmEnabled, onCheckedChange = onBgmToggle)
            }

            // ✅ BGM選択
            Text("BGM選択", fontFamily = YuseiMagic, color = Color.White)
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                BgmType.values().forEach { type ->
                    val isUnlocked = when (type) {
                        BgmType.DAY20 -> elapsedDays >= 21
                        else -> true
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Button(
                            onClick = {
                                if (!isUnlocked) return@Button
                                selectedBgmType.value = type
                                prefs.edit().putString("selected_bgm_type", type.name).apply()
                                if (isBgmEnabled) {
                                    AudioManager.playBgm(context, type.fileResId)
                                }
                            },
                            enabled = isUnlocked,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selectedBgmType.value == type) Color.Gray else Color.DarkGray,
                                disabledContainerColor = Color.DarkGray.copy(alpha = 0.3f)
                            )
                        ) {
                            Text(type.label, fontFamily = YuseiMagic)
                        }

                        if (!isUnlocked) {
                            Text(
                                "21日目で解放",
                                fontSize = 10.sp,
                                color = Color.LightGray,
                                fontFamily = YuseiMagic
                            )
                        }}}}


            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("効果音", modifier = Modifier.weight(1f), fontFamily = YuseiMagic, color = Color.White)
                Switch(checked = isSoundEnabled, onCheckedChange = onSoundToggle)
            }


            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("ソフトホラー演出", fontFamily = YuseiMagic, color = Color.White)
                    Text(
                        "※一部演出が過激になります",
                        fontSize = 12.sp,
                        fontFamily = YuseiMagic,
                        color = Color.LightGray
                    )
                }
                Switch(
                    checked = isSoftHorrorEnabled,
                    onCheckedChange = {
                        onSoftHorrorToggle(it)
                    }
                )
            }


            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "エッジパネルの位置",
                    modifier = Modifier.weight(1f),
                    fontFamily = YuseiMagic,
                    color = Color.White
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { onEdgeSideChange(EdgeSide.Left) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (edgeSide == EdgeSide.Left) Color.Gray else Color.DarkGray
                        )
                    ) {
                        Text("左", fontFamily = YuseiMagic)
                    }

                    Button(
                        onClick = { onEdgeSideChange(EdgeSide.Right) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (edgeSide == EdgeSide.Right) Color.Gray else Color.DarkGray
                        )
                    ) {
                        Text("右", fontFamily = YuseiMagic)
                    }
                }
            }

            Button(onClick = onCreditOpen) {
                Text("クレジットを見る", fontFamily = YuseiMagic)
            }

            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = onClose, modifier = Modifier.align(Alignment.End)) {
                Text("閉じる", fontFamily = YuseiMagic)
            }
        }
    }
}


@Composable
fun CreditModal(onClose: () -> Unit) {
    val uriHandler = LocalUriHandler.current

    // YouTubeとBOOTHリンク：しゃろう
    val sharouCredit = buildAnnotatedString {
        append("BGM：lost / しゃろう（BOOTH）\n")
        pushStringAnnotation("URL", "https://www.youtube.com/@Sharou")
        withStyle(SpanStyle(color = Color.Cyan, textDecoration = TextDecoration.Underline)) {
            append("https://www.youtube.com/@Sharou")
        }
        pop()
        append("\n")
        pushStringAnnotation("URL", "https://shll.booth.pm/")
        withStyle(SpanStyle(color = Color.Cyan, textDecoration = TextDecoration.Underline)) {
            append("https://shll.booth.pm/")
        }
        pop()
    }

    // On-Jin 効果音
    val onjinCredit = buildAnnotatedString {
        append("効果音：On-Jin ～音人～（")
        pushStringAnnotation("URL", "https://on-jin.com/")
        withStyle(SpanStyle(color = Color.Cyan, textDecoration = TextDecoration.Underline)) {
            append("https://on-jin.com/")
        }
        pop()
        append("）")
    }

    // DOVA全般リンク（任意で分けられる）
    val dovaCredit = "BGM：『マリンバマーチ』 by shimtone（DOVA-SYNDROME）\n" +
            "BGM：『かえるのピアノ』 by こおろぎ（DOVA-SYNDROME）\n" +
            "BGM：『はじまりのマリンバ』 by OK-Sounds（DOVA-SYNDROME）\n" +
            "効BGM：春の箱舟 / まんぼう二等兵（DOVA-SYNDROME）"

    val fukiDesignCredit = buildAnnotatedString {
        append("素材提供：ふきだしデザイン（")
        pushStringAnnotation("URL", "https://fukidesign.com/")
        withStyle(SpanStyle(color = Color.Cyan, textDecoration = TextDecoration.Underline)) {
            append("https://fukidesign.com/")
        }
        pop()
        append("）")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF111111))
            .padding(24.dp)
            .zIndex(999f)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "クレジット",
                style = MaterialTheme.typography.headlineMedium,
                fontFamily = YuseiMagic,
                color = Color.White
            )

            Text(dovaCredit, fontFamily = YuseiMagic, color = Color.White)

            ClickableText(
                text = onjinCredit,
                style = TextStyle(fontFamily = YuseiMagic, color = Color.White),
                onClick = { offset ->
                    onjinCredit.getStringAnnotations("URL", offset, offset)
                        .firstOrNull()?.let { uriHandler.openUri(it.item) }
                }
            )

            ClickableText(
                text = sharouCredit,
                style = TextStyle(fontFamily = YuseiMagic, color = Color.White),
                onClick = { offset ->
                    sharouCredit.getStringAnnotations("URL", offset, offset)
                        .firstOrNull()?.let { uriHandler.openUri(it.item) }
                }
            )
            ClickableText(
                text = fukiDesignCredit,
                style = TextStyle(fontFamily = YuseiMagic, color = Color.White),
                onClick = { offset ->
                    fukiDesignCredit.getStringAnnotations("URL", offset, offset)
                        .firstOrNull()?.let { uriHandler.openUri(it.item) }
                }
            )
            Spacer(Modifier.height(32.dp))
            Button(onClick = onClose) {
                Text("閉じる", fontFamily = YuseiMagic)
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(999f),
                contentAlignment = Alignment.Center
            ) {
                EyeOfProvidenceAnimation()
            }
        }
    }
}
