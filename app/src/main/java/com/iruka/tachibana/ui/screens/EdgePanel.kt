package com.iruka.tachibana.ui.screens

// ✅ たちばな用 EdgePanel.kt（仮実装：スライド検出＋仮アイコン表示）

import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.iruka.tachibana.R

import androidx.compose.material3.Text
import androidx.compose.ui.res.stringResource

enum class EdgeSide { Left, Right }


@Composable
fun EdgePanelWithHandle(
    side: EdgeSide = EdgeSide.Right,
    panelWidth: Dp = 120.dp,
    handleWidth: Dp = 12.dp,
    isInteractionEnabled: Boolean = true,
    content: @Composable ColumnScope.() -> Unit
) {
    var isOpen by remember { mutableStateOf(false) }
    val animatedWidth by animateDpAsState(if (isOpen) panelWidth else handleWidth)
    val alignment = if (side == EdgeSide.Right) Alignment.CenterEnd else Alignment.CenterStart
    Box(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(0f),
        contentAlignment = alignment
    ) {
        Box(
            modifier = Modifier
                .width(animatedWidth)
                .fillMaxHeight()
        ) {
            Image(
                painter = painterResource(id = R.drawable.edge_cropped_250x1920),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize()
                    .then(
                        if (side == EdgeSide.Left) Modifier.graphicsLayer {
                            scaleX = -1f
                        } else Modifier
                    )
            )

            if (isOpen) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                        .then(
                            if (isInteractionEnabled) Modifier.pointerInput(Unit) {
                                detectHorizontalDragGestures { _, dragAmount ->
                                    if (side == EdgeSide.Right && dragAmount > 12) isOpen = false
                                    if (side == EdgeSide.Left && dragAmount < -12) isOpen = false
                                }
                            } else Modifier
                        ),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // ✅ 未実装プレースホルダー表示（あとで削除してOK）
                    Text(
                        text = stringResource(R.string.edge_panel_unimplemented),
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // ✅ 呼び出し元の content() を表示
                    content()
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(handleWidth)
                        .background(Color.DarkGray)
                        .then(
                            if (isInteractionEnabled) Modifier.pointerInput(Unit) {
                                detectHorizontalDragGestures { _, dragAmount ->
                                    if (side == EdgeSide.Right && dragAmount < -12) isOpen = true
                                    if (side == EdgeSide.Left && dragAmount > 12) isOpen = true
                                }
                            } else Modifier
                        )
                )
            }
        }
    }
}



