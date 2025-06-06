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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.iruka.tachibana.R
import com.iruka.tachibana.ui.screens.YuseiMagic

@Composable
fun EventDay30Screen(navController: NavController) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("tachibana_prefs", Context.MODE_PRIVATE)

    var currentLineIndex by remember { mutableStateOf(0) }
    var showFinalChoice by remember { mutableStateOf(false) }

    val lines = listOf(
        stringResource(R.string.day30_line_0),
        stringResource(R.string.day30_line_1),
        stringResource(R.string.day30_line_2)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEEE8DD))
            .clickable(enabled = !showFinalChoice) {
                AudioManager.playSE(context, R.raw.cursor_move_se)
                if (currentLineIndex < lines.lastIndex) {
                    currentLineIndex++
                } else {
                    showFinalChoice = true
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.facial_profile),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = lines[currentLineIndex],
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
                // ボタンUI：30日目の選択肢（Bad2分岐）
                Button(
                    onClick = {
                        val consumed = prefs.getStringSet("consumedEvents", emptySet()) ?: emptySet()
                        prefs.edit()
                            .putBoolean("bad2_selected", true)
                            .putStringSet("consumedEvents", consumed + "30")
                            .apply()
                        navController.navigate("loading/bad2") { popUpTo("main") { inclusive = true } }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(stringResource(R.string.common_yes), fontFamily = YuseiMagic) // 「はい」
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        val consumed = prefs.getStringSet("consumedEvents", emptySet()) ?: emptySet()
                        prefs.edit()
                            .putStringSet("consumedEvents", consumed + "30")
                            .apply()
                        navController.navigate("loading/true_end") { popUpTo("main") { inclusive = true } }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(stringResource(R.string.common_no), fontFamily = YuseiMagic) // 「いいえ」
                }

            }
            }
        }
    }

