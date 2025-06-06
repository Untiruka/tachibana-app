package com.iruka.tachibana.ui.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.iruka.tachibana.R
import kotlinx.coroutines.delay

@Composable
fun SoftHorrorBackHandler(
    navController: NavController,
    enabled: Boolean,
    onTriggerPanicModal: () -> Unit, // ← 追加したコールバック
    useAltStyle: Boolean = false
) {
    if (!enabled) return

    val context = LocalContext.current
    val prefs = context.getSharedPreferences("tachibana_prefs", Context.MODE_PRIVATE)
    var backPressCount by remember { mutableStateOf(0) }

    val toastMessages = listOf(
        stringResource(R.string.BackToast_line_0),
        stringResource(R.string.BackToast_line_1),
        stringResource(R.string.BackToast_line_2),
        stringResource(R.string.BackToast_line_3),
        stringResource(R.string.BackToast_line_4),
        stringResource(R.string.BackToast_line_5),
        stringResource(R.string.BackToast_line_6),
        stringResource(R.string.BackToast_line_7),
        stringResource(R.string.BackToast_line_8),
        stringResource(R.string.BackToast_line_9),
        stringResource(R.string.BackToast_line_10),
        stringResource(R.string.BackToast_line_11)
    )


    BackHandler(enabled = true) {
        if (backPressCount == 9) {
            prefs.edit().putBoolean("soft_horror_shown", true).apply()  // 🔴これを追加
            onTriggerPanicModal() // ← ここでモーダル起動
        } else if (backPressCount < toastMessages.lastIndex) {
            Toast.makeText(context, toastMessages[backPressCount], Toast.LENGTH_SHORT).show()
            backPressCount++
        } else {
            if (!prefs.getBoolean("soft_horror_shown", false)) {
                prefs.edit().putBoolean("soft_horror_shown", true).apply()
            }
            Toast.makeText(context, toastMessages.last(), Toast.LENGTH_SHORT).show()
            navController.popBackStack("main", inclusive = false)
        }
    }
}
