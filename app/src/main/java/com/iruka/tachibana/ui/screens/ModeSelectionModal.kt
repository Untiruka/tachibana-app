package com.iruka.tachibana.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp

val yuseiFont = FontFamily.Default // ← これ適宜修正してな


@Composable
fun ModeSelectionModal(onModeSelected: (PlayMode) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { onModeSelected(PlayMode.NORMAL) }) {
            Text("通常モード", fontFamily = yuseiFont)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { onModeSelected(PlayMode.NARRATIVE) }) {
            Text("ナラティブモード", fontFamily = yuseiFont)
        }
    }
}
