package com.iruka.tachibana.ui.screens

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.iruka.tachibana.R


@Composable
fun DoubleBackToExitHandler() {
    val context = LocalContext.current
    val backToastText = stringResource(R.string.BackToast_line_0) // ← Compose内で取得しておく
    var backPressedTime by remember { mutableStateOf(0L) }

    BackHandler {
        val currentTime = System.currentTimeMillis()
        if (currentTime - backPressedTime < 2000) {
            (context as? Activity)?.finish()
        } else {
            backPressedTime = currentTime
            Toast.makeText(context, backToastText, Toast.LENGTH_SHORT).show()
        }
    }
}
