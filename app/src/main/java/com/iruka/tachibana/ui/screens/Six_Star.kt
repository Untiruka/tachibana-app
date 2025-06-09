package com.iruka.tachibana.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.geometry.Size



import kotlin.math.*




@Composable
fun EyeOfProvidenceAnimation() {
    val infiniteTransition = rememberInfiniteTransition(label = "eye_anim")

    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "progress"
    )

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "rotation"
    )

    Canvas(
        modifier = Modifier
            .size(300.dp) // 表示サイズ
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12 * density // 透視崩壊を防ぐ
            }
    ) {
        // サイズと中心位置
        val radius = size.minDimension / 2.5f
        val center = Offset(size.width / 2, size.height / 2)

        // 中心から指定角度と倍率で点を出す関数
        fun polarOffset(r: Float, angleDeg: Float): Offset {
            val angleRad = Math.toRadians(angleDeg.toDouble())
            return Offset(
                center.x + (cos(angleRad) * r * radius).toFloat(),
                center.y + (sin(angleRad) * r * radius).toFloat()
            )
        }

            // 一筆書き順のポイント（"神の目"構造）
        val points = listOf(
            polarOffset(1.0f, 150f),  // B
            polarOffset(0.5f, 150f),  // F
            polarOffset(0.4f, -90f),  // E
            polarOffset(0.5f, 30f),   // D
            polarOffset(1.0f, 30f),   // C
            polarOffset(1.0f, 150f),  // B2
            polarOffset(0.5f, 150f),  // F2
            polarOffset(0.2f, 150f),  // G
            center,                   // O
            polarOffset(0.2f, 30f),   // H
            polarOffset(0.5f, 30f)    // D2
        )

            val totalSegments = points.size - 1
            val currentSegmentFloat = progress * totalSegments
            val currentSegment = currentSegmentFloat.toInt().coerceAtMost(totalSegments - 1)
            val segmentProgress = currentSegmentFloat - currentSegment

            // 完全に描く部分
            for (i in 0 until currentSegment) {
                drawLine(
                    color = Color.Cyan,
                    start = points[i],
                    end = points[i + 1],
                    strokeWidth = 4f
                )
            }

            // 途中まで描く部分
            if (currentSegment < totalSegments) {
                val start = points[currentSegment]
                val end = points[currentSegment + 1]
                val intermediate = Offset(
                    start.x + (end.x - start.x) * segmentProgress,
                    start.y + (end.y - start.y) * segmentProgress
                )

                drawLine(
                    color = Color.Cyan,
                    start = start,
                    end = intermediate,
                    strokeWidth = 4f
                )
            }
        }
    }
