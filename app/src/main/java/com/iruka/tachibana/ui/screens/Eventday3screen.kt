package com.iruka.tachibana.ui.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.iruka.tachibana.R
import kotlinx.coroutines.delay

@Composable
fun EventDay3Screen(navController: NavController) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("tachibana_prefs", Context.MODE_PRIVATE)
    val editor = prefs.edit()

    val lines = listOf(
        stringResource(R.string.day3_line_0),
        stringResource(R.string.day3_line_1),
        stringResource(R.string.day3_line_2),
        stringResource(R.string.day3_line_3),
        stringResource(R.string.day3_line_4),
        stringResource(R.string.day3_line_5),
        stringResource(R.string.day3_line_6),
        stringResource(R.string.day3_line_7),
        stringResource(R.string.day3_line_8),
        stringResource(R.string.day3_line_9),
        stringResource(R.string.day3_line_10),
        stringResource(R.string.day3_line_11),
        stringResource(R.string.day3_line_12),
        stringResource(R.string.day3_line_13),
        stringResource(R.string.day3_line_14),
        stringResource(R.string.day3_line_15),
        stringResource(R.string.day3_line_16),
        stringResource(R.string.day3_line_17),
        stringResource(R.string.day3_line_18),
        stringResource(R.string.day3_line_19),
        stringResource(R.string.day3_line_20),
        stringResource(R.string.day3_line_21),
        stringResource(R.string.day3_line_22),
        stringResource(R.string.day3_line_23),
        stringResource(R.string.day3_line_24),
        stringResource(R.string.day3_line_25),
        stringResource(R.string.day3_line_26),
        stringResource(R.string.day3_line_27),
        stringResource(R.string.day3_line_28),
        stringResource(R.string.day3_line_29),
        stringResource(R.string.day3_line_30),
        stringResource(R.string.day3_line_31),
        stringResource(R.string.day3_line_32),
        stringResource(R.string.day3_line_33),
        stringResource(R.string.day3_line_34),
        stringResource(R.string.day3_line_35),
        stringResource(R.string.day3_line_36)
    )


    val fadeTriggers = mapOf(
        stringResource(R.string.day3_line_0) to R.drawable.white_background,
        stringResource(R.string.day3_line_2) to R.drawable.day3_2,
        stringResource(R.string.day3_line_5) to R.drawable.day3_1,
        stringResource(R.string.day3_line_9) to R.drawable.day3_0,
        stringResource(R.string.day3_line_15) to R.drawable.day3_3,
        stringResource(R.string.day3_line_20) to R.drawable.day3_1,
        stringResource(R.string.day3_line_24) to R.drawable.day3_0,
                stringResource(R.string.day3_line_32) to R.drawable.day3_4
    )

    val index = remember { mutableIntStateOf(0) }
    val currentLine = lines.getOrNull(index.value)

    var currentImageResId by remember { mutableStateOf<Int?>(null) }

    // 画像切替ロジック
    LaunchedEffect(currentLine) {
        if (currentLine in fadeTriggers) {
            currentImageResId = fadeTriggers[currentLine]
        }
    }

    val isAtLast = index.value == lines.lastIndex

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F3F3))
            .clickable {
                if (index.value < lines.lastIndex) {
                    index.value++
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            currentImageResId?.let {
                Image(
                    painter = painterResource(id = it),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            currentLine?.let {
                Text(
                    text = it,
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontFamily = YuseiMagic,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }

        Skipp.SkipButton(day = "3", navController = navController, context = context)
    }

    if (isAtLast) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    AudioManager.playSE(context, R.raw.cursor_move_se)
                    val consumed = prefs.getStringSet("consumedEvents", emptySet())?.toMutableSet() ?: mutableSetOf()
                    consumed.add("3")
                    editor.putStringSet("consumedEvents", consumed).apply()
                    navController.navigate("main")
                },
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                text = "（タップして戻る）",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                fontFamily = YuseiMagic,
                modifier = Modifier.padding(32.dp)
            )
        }
    }
}
