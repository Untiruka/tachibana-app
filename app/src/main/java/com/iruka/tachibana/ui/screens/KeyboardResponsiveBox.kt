package com.iruka.tachibana.ui.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.zIndex


@Composable
fun KeyboardResponsiveBox(
    modifier: Modifier = Modifier,
    baseOffsetY: Dp = (-100).dp,
    floatOffsetY: Dp = (-200).dp,
    content: @Composable BoxScope.() -> Unit
) {
    val imeBottom = WindowInsets.ime.getBottom(LocalDensity.current)
    val offsetY by animateDpAsState(
        targetValue = if (imeBottom > 0) floatOffsetY else 0.dp,
        animationSpec = tween(300)
    )

    Box(
        modifier = modifier
            .offset { IntOffset(0, (baseOffsetY + offsetY).roundToPx()) }
            .zIndex(30f) // 上に浮かせる
    ) {
        content()
    }
}

