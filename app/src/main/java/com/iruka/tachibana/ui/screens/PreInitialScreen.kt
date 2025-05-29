package com.iruka.tachibana.ui.screens




// ボタン類など UI は material3
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton

// AlertDialog だけ material（AlertDialogはmaterial3にない）
import androidx.compose.material3.AlertDialog

// その他
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import com.iruka.tachibana.R




@Composable
fun PreInitialScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val context = LocalContext.current
    val yuseiFont = FontFamily(Font(R.font.yuseimagicregular))

    val showConfirmDialog = remember { mutableStateOf(false) }
    var selectedMode by remember { mutableStateOf<PlayMode?>(null) }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "気をつけて選んでね",
            fontFamily = yuseiFont,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // 通常モードボタン
        Button(
            onClick = {
                selectedMode = PlayMode.NORMAL
                showConfirmDialog.value = true
            },
            modifier = Modifier
                .width(200.dp)
                .padding(vertical = 8.dp)
        ) {
            Text("通常モード", fontFamily = yuseiFont)
        }

        // ナラティブモードボタン
        Button(
            onClick = {
                selectedMode = PlayMode.NARRATIVE
                showConfirmDialog.value = true
            },
            modifier = Modifier
                .width(200.dp)
                .padding(vertical = 8.dp)
        ) {
            Text("ナラティブモード", fontFamily = yuseiFont)
        }

        // 確認ダイアログ
        if (showConfirmDialog.value && selectedMode != null) {
            AlertDialog(
                onDismissRequest = { showConfirmDialog.value = false },
                title = {
                    Text(
                        text = "本当に進みますか？",
                        fontFamily = yuseiFont,
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Text(
                        text = when (selectedMode) {
                            PlayMode.NORMAL -> "うん。それが正しい選択だよ。"
                            PlayMode.NARRATIVE -> "私はおすすめしない…けどあなたに覚悟があるのなら…"
                            else -> ""
                        },
                        fontFamily = yuseiFont
                    )
                },
                confirmButton = {
                    TextButton(onClick = {
                        showConfirmDialog.value = false
                        AudioManager.playSE(context, R.raw.close_door_se)
                        PlayModeManager.setCurrentMode(context, selectedMode!!)
                        navController.navigate("loading/initial")
                    }) {
                        Text("進む", fontFamily = yuseiFont)
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showConfirmDialog.value = false
                    }) {
                        Text("戻る", fontFamily = yuseiFont)
                    }
                }
            )
        }
    }
}