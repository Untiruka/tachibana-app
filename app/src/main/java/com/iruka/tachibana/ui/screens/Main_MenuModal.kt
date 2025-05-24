package com.iruka.tachibana.ui.screens





import android.view.Menu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.ui.Alignment // ← これをimport



@Composable
fun MenuModal(onClose: () -> Unit) {
    ModalWrapper(onClose = onClose) {
        Text(
            text = "設定画面（仮）",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}