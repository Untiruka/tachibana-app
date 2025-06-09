package com.iruka.tachibana.ui.screens


import android.app.Activity
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
import android.os.Build
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.iruka.tachibana.ui.screens.YuseiMagic
import java.util.concurrent.TimeUnit


enum class BgmType(@StringRes val labelResId: Int, val fileResId: Int) {
    DEFAULT(R.string.bgm_default, R.raw.marinba_march),
    DAY20(R.string.bgm_day20, R.raw.day20);
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
    val elapsedDays = remember {
        val prefs = context.getSharedPreferences("tachibana_prefs", Context.MODE_PRIVATE)
        val startTime = prefs.getLong("startTimeInMillis", System.currentTimeMillis())
        TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - startTime).toInt()
    }
    val isSoftHorrorEnabledState = remember {
        mutableStateOf(prefs.getBoolean("soft_horror_enabled", false))
    }
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
            Text(stringResource(R.string.settings), style = MaterialTheme.typography.headlineMedium, fontFamily = YuseiMagic, color = Color.White)

            // ✅ ON/OFFトグル
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(stringResource(R.string.bgm_toggle), modifier = Modifier.weight(1f), fontFamily = YuseiMagic, color = Color.White)
                Switch(checked = isBgmEnabled, onCheckedChange = onBgmToggle)
            }

            // ✅ BGM選択
            Text(stringResource(R.string.bgm_select), fontFamily = YuseiMagic, color = Color.White)
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
                            Text(stringResource(type.labelResId), fontFamily = YuseiMagic)

                        }

                        if (!isUnlocked) {
                            Text(
                                stringResource(R.string.unlock_day21),
                                fontSize = 10.sp,
                                color = Color.LightGray,
                                fontFamily = YuseiMagic
                            )
                        }
                    }
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(stringResource(R.string.sound_effect), modifier = Modifier.weight(1f), fontFamily = YuseiMagic, color = Color.White)
                Switch(checked = isSoundEnabled, onCheckedChange = onSoundToggle)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(stringResource(R.string.soft_horror), fontFamily = YuseiMagic, color = Color.White)
                    Text(
                        stringResource(R.string.soft_horror_desc),
                        fontSize = 12.sp,
                        fontFamily = YuseiMagic,
                        color = Color.LightGray
                    )
                }
                Switch(checked = isSoftHorrorEnabled, onCheckedChange = onSoftHorrorToggle)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(stringResource(R.string.edge_position), modifier = Modifier.weight(1f), fontFamily = YuseiMagic, color = Color.White)

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
                        Text(stringResource(R.string.edge_left), fontFamily = YuseiMagic)
                    }

                    Button(
                        onClick = { onEdgeSideChange(EdgeSide.Right) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (edgeSide == EdgeSide.Right) Color.Gray else Color.DarkGray
                        )
                    ) {
                        Text(stringResource(R.string.edge_right), fontFamily = YuseiMagic)
                    }
                }
            }

          //  LanguageSelector()

            Button(onClick = onCreditOpen) {
                Text(stringResource(R.string.staff), fontFamily = YuseiMagic)
            }

            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = onClose, modifier = Modifier.align(Alignment.End)) {
                Text(stringResource(R.string.quit), fontFamily = YuseiMagic)
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


@Composable
fun LanguageSelector() {
    val context = LocalContext.current
    val activity = context as? Activity

    // ドロップダウンの状態
    var expanded by remember { mutableStateOf(false) }
    val options = listOf("日本語", "English")
    val currentLanguage = when (context.resources.configuration.locales[0].language) {
        "en" -> "English"
        else -> "日本語"
    }
    var selectedText by remember { mutableStateOf(currentLanguage) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Text(
            text = stringResource(R.string.language),
            style = MaterialTheme.typography.bodyLarge
        )
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(selectedText)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { label ->
                    DropdownMenuItem(
                        text = { Text(label) },
                        onClick = {
                            selectedText = label
                            expanded = false

                            val localeCode = when (label) {
                                "English" -> "en"
                                else -> "ja"
                            }

                            val appLocale = LocaleListCompat.forLanguageTags(localeCode)
                            AppCompatDelegate.setApplicationLocales(appLocale)

                            // ✅ 即時反映：Activityを再起動！
                            activity?.recreate()
                        }
                    )
                }
            }
        }
    }
}
