package com.iruka.tachibana.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.iruka.tachibana.R

@Composable
fun ModalWrapper(
    onClose: () -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.8f))
            .blur(1.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    AudioManager.playSE(context, R.raw.close_door_se) // ← ここでSE鳴らす！
                    onClose()
                })
            }
            .zIndex(9f)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(10f),
        content = content
    )
}
