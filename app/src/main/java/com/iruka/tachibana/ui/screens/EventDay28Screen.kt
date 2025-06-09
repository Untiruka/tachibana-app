package com.iruka.tachibana.ui.screens

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.iruka.tachibana.R
import com.iruka.tachibana.ui.screens.YuseiMagic
import kotlinx.coroutines.delay

@Composable
fun EventDay28Screen(navController: NavController) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("tachibana_prefs", Context.MODE_PRIVATE)
    val editor = prefs.edit()

    var currentImageIndex by remember { mutableStateOf(0) }
    var currentLineIndex by remember { mutableStateOf(0) }
    var showFinalChoice by remember { mutableStateOf(false) }

    val sceneList = listOf(
        R.drawable.day28_0 to listOf(
            stringResource(R.string.day28_line_0)
        ),
        R.drawable.day28_0 to listOf(
            stringResource(R.string.day28_line_1)
        ),
        R.drawable.day28_1 to listOf(
            stringResource(R.string.day28_line_2)
        ),
        R.drawable.day28_2 to listOf(
            stringResource(R.string.day28_line_3)
        ),
        R.drawable.day28_3 to listOf(
            stringResource(R.string.day28_line_4),
            stringResource(R.string.day28_line_5),
            stringResource(R.string.day28_line_6),
            stringResource(R.string.day28_line_7),
            stringResource(R.string.day28_line_8),
            stringResource(R.string.day28_line_9)
        )
    )


    val (currentImage, currentLines) = sceneList[currentImageIndex]
    val mediaPlayer = remember { mutableStateOf<MediaPlayer?>(null) }

    LaunchedEffect(Unit) {
        AudioManager.stopBgm() // ← 念のため前のBGMを止める
        AudioManager.playBgm(context, R.raw.day_28) // ← あなたのbad1用BGM
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEEE8DD))
            .clickable(enabled = !showFinalChoice) {
                AudioManager.playSE(context, R.raw.cursor_move_se)

                if (currentLineIndex < currentLines.lastIndex) {
                    currentLineIndex++
                } else if (currentImageIndex < sceneList.lastIndex) {
                    // day28_1 の最後のセリフが終わって day28_2 の1行目へ進むタイミング
                    if (currentImageIndex == 2 && currentLineIndex == currentLines.lastIndex) {
                        mediaPlayer.value = MediaPlayer.create(context, R.raw.door_open2)
                        mediaPlayer.value?.start()
                    }
                    currentImageIndex++
                    currentLineIndex = 0
                } else {
                    showFinalChoice = true
                }
            }
        ,
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
                        val consumed = prefs.getStringSet("consumedEvents", emptySet())?.toMutableSet()
                            ?: mutableSetOf()
                        consumed.add("28")
                        prefs.edit().putStringSet("consumedEvents", consumed).apply()
                        navController.navigate("main") { popUpTo("main") { inclusive = true } }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(stringResource(R.string.common_yes), fontFamily = YuseiMagic)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        val consumed = prefs.getStringSet("consumedEvents", emptySet())?.toMutableSet()
                            ?: mutableSetOf()
                        consumed.add("28")
                        prefs.edit().putStringSet("consumedEvents", consumed).apply()
                        navController.navigate("main") { popUpTo("main") { inclusive = true } }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(stringResource(R.string.common_no), fontFamily = YuseiMagic)
                }

            }
        }
    }

    Skipp.SkipButton(day = "28", navController = navController, context = context)

    LaunchedEffect(currentImageIndex, currentLineIndex) {
        val currentText = currentLines.getOrNull(currentLineIndex)
        if (currentImageIndex == 3 && currentLineIndex == 0) {
            mediaPlayer.value = MediaPlayer.create(context, R.raw.door_open2)
            mediaPlayer.value?.start()
        }
    }


    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.value?.stop()
            mediaPlayer.value?.release()
        }
    }

}