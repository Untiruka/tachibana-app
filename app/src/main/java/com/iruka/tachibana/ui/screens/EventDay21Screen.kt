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
import androidx.compose.animation.Crossfade

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iruka.tachibana.ui.screens.YuseiMagic
import androidx.compose.ui.res.stringResource
import com.iruka.tachibana.ui.components.BannerAdView

import com.iruka.tachibana.ui.screens.AudioManager


@Composable
fun EventDay21Screen(navController: NavController) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("tachibana_prefs", Context.MODE_PRIVATE)
    val editor = prefs.edit()

    LaunchedEffect(Unit) {
        AudioManager.playBgm(context, R.raw.day21_music)
    }

    val lines = listOf(
        stringResource(R.string.day21_line_0),
        stringResource(R.string.day21_line_1),
        stringResource(R.string.day21_line_2),
        stringResource(R.string.day21_line_3),
        stringResource(R.string.day21_line_4),
        stringResource(R.string.day21_line_5),
        stringResource(R.string.day21_line_6),
        stringResource(R.string.day21_line_7),
        stringResource(R.string.day21_line_8),
        stringResource(R.string.day21_line_9),
        stringResource(R.string.day21_line_10),
        stringResource(R.string.day21_line_11),
        stringResource(R.string.day21_line_12),
        stringResource(R.string.day21_line_13),
        stringResource(R.string.day21_line_14),
        stringResource(R.string.day21_line_15),
        stringResource(R.string.day21_line_16),
        stringResource(R.string.day21_line_17),
        stringResource(R.string.day21_line_18),
        stringResource(R.string.day21_line_19),
        stringResource(R.string.day21_line_20),
        stringResource(R.string.day21_line_21),
        stringResource(R.string.day21_line_22),
        stringResource(R.string.day21_line_23),
        stringResource(R.string.day21_line_24),
        stringResource(R.string.day21_line_25),
        stringResource(R.string.day21_line_26),
        stringResource(R.string.day21_line_27),
        stringResource(R.string.day21_line_28),
        stringResource(R.string.day21_line_29),
        stringResource(R.string.day21_line_30),
        stringResource(R.string.day21_line_31),
        stringResource(R.string.day21_line_32),
        stringResource(R.string.day21_line_33),
        stringResource(R.string.day21_line_34),
        stringResource(R.string.day21_line_35),
        stringResource(R.string.day21_line_36),
        stringResource(R.string.day21_line_37),
        stringResource(R.string.day21_line_38),
        stringResource(R.string.day21_line_39),
        stringResource(R.string.day21_line_40),
        stringResource(R.string.day21_line_41),
        stringResource(R.string.day21_line_42),
        stringResource(R.string.day21_line_43),
        stringResource(R.string.day21_line_44),
        stringResource(R.string.day21_line_45),
        stringResource(R.string.day21_line_46),
        stringResource(R.string.day21_line_47),
        stringResource(R.string.day21_line_48),
        stringResource(R.string.day21_line_49),
        stringResource(R.string.day21_line_50),
        stringResource(R.string.day21_line_51),
        stringResource(R.string.day21_line_52),
        stringResource(R.string.day21_line_53),
        stringResource(R.string.day21_line_54),
        stringResource(R.string.day21_line_55),
        stringResource(R.string.day21_line_56),
        stringResource(R.string.day21_line_57),
        stringResource(R.string.day21_line_58),
        stringResource(R.string.day21_line_59),
        stringResource(R.string.day21_line_60),
        stringResource(R.string.day21_line_61),
        stringResource(R.string.day21_line_62),
        stringResource(R.string.day21_line_63),
        stringResource(R.string.day21_line_64)
    )

    val fadeTriggers = mapOf(
        1 to R.drawable.day21_,
        5 to R.drawable.day21_1,
        13 to R.drawable.day21_2,
        19 to R.drawable.day21_3,
        25 to R.drawable.day21_4,
        28 to R.drawable.day21_5,
        38 to R.drawable.day21_5_5,
        47 to R.drawable.day21_6,
        54 to R.drawable.day21_7,
        55 to R.drawable.day21_8,
        56 to R.drawable.day21_8_1,
        57 to R.drawable.day21_8_2,
        58 to R.drawable.day21_8_3,
        59 to R.drawable.day21_8_4,
        60 to R.drawable.day21_8
    )


    val index = remember { mutableStateOf(0) }
    var currentImageResId by remember { mutableStateOf(R.drawable.day21_) }

    val currentLine = lines.getOrNull(index.value)

    LaunchedEffect(index.value) {
        fadeTriggers[index.value]?.let {
            currentImageResId = it
        }
    }


    val stopLine = stringResource(R.string.day21_line_55)

    LaunchedEffect(index.value) {
        if (currentLine == stopLine) {
            AudioManager.stopBgm()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            AudioManager.stopBgm()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F3F3))
    ) {
        // 一番上のバナー表示
        BannerAdView(modifier = Modifier.fillMaxWidth())

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEEE8DD))
                .clickable {
                    AudioManager.playSE(context, R.raw.cursor_move_se)
                    if (index.value < lines.lastIndex) {
                        index.value++
                    }
                },

            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                key(currentImageResId) {
                    Image(
                        painter = painterResource(id = currentImageResId),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        contentScale = ContentScale.Fit
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                currentLine?.let {
                    Text(
                        text = it,
                        fontFamily = YuseiMagic,
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }

            if (index.value == lines.lastIndex) {
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
                            val consumed =
                                prefs.getStringSet("consumedEvents", emptySet())?.toMutableSet()
                                    ?: mutableSetOf()
                            consumed.add("21")
                            editor.putStringSet("consumedEvents", consumed).apply()
                            navController.navigate("main") { popUpTo("main") { inclusive = true } }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.day21_choice_0),
                            fontFamily = YuseiMagic
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            val consumed =
                                prefs.getStringSet("consumedEvents", emptySet())?.toMutableSet()
                                    ?: mutableSetOf()
                            consumed.add("21")
                            editor.putStringSet("consumedEvents", consumed).apply()
                            navController.navigate("main") { popUpTo("main") { inclusive = true } }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.day21_choice_1),
                            fontFamily = YuseiMagic
                        )
                    }
                }
            }
            Skipp.SkipButton(day = "21", navController = navController, context = context)
        }


    }

}



object Skipp {

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

        Column {
            Spacer(modifier = Modifier.height(50.dp))

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
                Text("Skip", fontFamily = YuseiMagic)
            }
        }}
    }
    fun getStartDestination(isDebug: Boolean, isInitialized: Boolean): String {
        return if (isDebug) {
            "event_day_21"  // ← 修正ポイント
        } else {
            if (isInitialized) "main_check" else "preinitial"
        }
    }
}
