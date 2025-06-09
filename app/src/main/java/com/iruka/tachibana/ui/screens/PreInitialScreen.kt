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
import androidx.compose.ui.res.stringResource
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
            text = stringResource(R.string.preInitial_title),
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
            Text(stringResource(R.string.preInitial_mode_normal), fontFamily = yuseiFont)
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
            Text(stringResource(R.string.preInitial_mode_narrative), fontFamily = yuseiFont)
        }

        // 確認ダイアログ
        if (showConfirmDialog.value && selectedMode != null) {
            AlertDialog(
                onDismissRequest = { showConfirmDialog.value = false },
                title = {
                    Text(
                        text = stringResource(R.string.preInitial_dialog_title),
                        fontFamily = yuseiFont,
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Text(
                        text = when (selectedMode) {
                            PlayMode.NORMAL -> stringResource(R.string.preInitial_dialog_text_normal)
                            PlayMode.NARRATIVE -> stringResource(R.string.preInitial_dialog_text_narrative)
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
                        Text(
                            stringResource(R.string.preInitial_button_proceed),
                            fontFamily = yuseiFont
                        )
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showConfirmDialog.value = false
                    }) {
                        Text(
                            stringResource(R.string.preInitial_button_cancel),
                            fontFamily = yuseiFont
                        )
                    }
                }
            )
        }
    }
}