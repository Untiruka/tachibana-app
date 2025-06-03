package com.iruka.tachibana.ui.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.iruka.tachibana.R
import kotlinx.coroutines.delay

@Composable
fun SoftHorrorBackHandler(
    navController: NavController,
    enabled: Boolean
) {
    if (!enabled) return

    val context = LocalContext.current
    val prefs = context.getSharedPreferences("tachibana_prefs", Context.MODE_PRIVATE)
    var backPressCount by remember { mutableStateOf(0) }

    val toastMessages = listOf(
        "もう一回タップすると戻れます",
        "嘘です",
        "ねえ",
        "なんで戻ろうとするの？",
        "ここにずっといればいいじゃない。",
        "戻っても良いことなんて何もないじゃない",
        "もう一回タップすると戻れます",
        "戻れます",
        "戻れません",
        "戻れません戻れません戻れません戻れません戻れません戻れません戻れません戻れません戻れません戻れません戻れません戻れません戻れません戻れません戻れません戻れません戻れません戻れません戻れません戻れません戻れません…",
        "戻れませっていってるでしょ",
        "……やっぱり、帰っちゃうんだね。"
    )

    BackHandler(enabled = true) {
        if (backPressCount < toastMessages.lastIndex) {
            Toast.makeText(context, toastMessages[backPressCount], Toast.LENGTH_SHORT).show()
            backPressCount++
        } else {
            // 🔥 最後のトーストのあとに一度だけ保存する
            if (!prefs.getBoolean("soft_horror_shown", false)) {
                prefs.edit().putBoolean("soft_horror_shown", true).apply()
            }

            Toast.makeText(context, toastMessages.last(), Toast.LENGTH_SHORT).show()
            navController.popBackStack("main", inclusive = false)
        }
    }
}


