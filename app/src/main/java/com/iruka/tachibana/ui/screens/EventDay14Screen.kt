package com.iruka.tachibana.ui.screens


import android.content.Context
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.iruka.tachibana.R
import com.iruka.tachibana.ui.screens.yuseiFont
import kotlinx.coroutines.delay

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
@Composable
fun EventDay14Screen(navController: NavController) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("tachibana_prefs", Context.MODE_PRIVATE)
    val editor = prefs.edit()

    val sceneList = listOf(
        R.drawable.day_14_image to listOf(
            stringResource(R.string.day14_line_0),
            stringResource(R.string.day14_line_1),
            stringResource(R.string.day14_line_2),
            stringResource(R.string.day14_line_3),
            stringResource(R.string.day14_line_4),
            stringResource(R.string.day14_line_5),
        ),
        R.drawable.day_14_image_1 to listOf(
            stringResource(R.string.day14_line_6),
            stringResource(R.string.day14_line_7),
            stringResource(R.string.day14_line_8),
            stringResource(R.string.day14_line_9),
            stringResource(R.string.day14_line_10),
            stringResource(R.string.day14_line_11),
            stringResource(R.string.day14_line_12),
        ),
        R.drawable.day_14_image2 to listOf(
            stringResource(R.string.day14_line_13),
            stringResource(R.string.day14_line_14),
            stringResource(R.string.day14_line_15),
        )
    )

    val finalScene = listOf(
        R.drawable.day_14_image3 to listOf(
            stringResource(R.string.day14_line_16),
            stringResource(R.string.day14_line_17),
            stringResource(R.string.day14_line_18)
        )
    )

    val tapToReturn = stringResource(R.string.tap_to_return)

    var currentImageIndex by remember { mutableIntStateOf(0) }
    var currentLineIndex by remember { mutableIntStateOf(0) }
    var showChoice by remember { mutableStateOf(false) }
    var inFinal by remember { mutableStateOf(false) }
    var currentImageResId by remember { mutableStateOf<Int?>(null) }

    val currentScenes = if (inFinal) finalScene else sceneList
    val (currentImage, currentLines) = currentScenes[currentImageIndex]
    val currentLine = currentLines.getOrNull(currentLineIndex)

    // ✅ 画像切替
    LaunchedEffect(currentLine) {
        currentImageResId = currentImage
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEEE8DD))
            .clickable(enabled = !showChoice) {
                AudioManager.playSE(context, R.raw.cursor_move_se)
                if (currentLineIndex < currentLines.lastIndex) {
                    currentLineIndex++
                } else if (currentImageIndex < currentScenes.lastIndex) {
                    currentImageIndex++
                    currentLineIndex = 0
                } else if (!inFinal) {
                    showChoice = true
                } else {
                    // Finalの最後：タップで戻る
                    val consumed = prefs.getStringSet("consumedEvents", emptySet())?.toMutableSet() ?: mutableSetOf()
                    consumed.add("14")
                    editor.putStringSet("consumedEvents", consumed).apply()
                    navController.navigate("main")
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            currentImageResId?.let {
                Image(
                    painter = painterResource(id = it),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(300.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            currentLine?.let {
                Text(
                    text = it,
                    fontFamily = yuseiFont,
                    fontSize = 20.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            if (showChoice) {
                Spacer(modifier = Modifier.height(32.dp))
                Column(modifier = Modifier.padding(horizontal = 32.dp)) {
                    Button(
                        onClick = {
                            inFinal = true
                            currentImageIndex = 0
                            currentLineIndex = 0
                            showChoice = false
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(text = stringResource(R.string.day14_choice_0), fontFamily = yuseiFont)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = {
                            inFinal = true
                            currentImageIndex = 0
                            currentLineIndex = 0
                            showChoice = false
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(text = stringResource(R.string.day14_choice_1), fontFamily = yuseiFont)
                    }
                }
            }

            // ✅ Final最後：タップして戻る表示
            if (inFinal &&
                currentImageIndex == finalScene.lastIndex &&
                currentLineIndex == finalScene.last().second.lastIndex
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = tapToReturn,
                    fontFamily = yuseiFont,
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(32.dp)
                )
            }
        }

        Skipp.SkipButton(day = "14", navController = navController, context = context)
    }
}
