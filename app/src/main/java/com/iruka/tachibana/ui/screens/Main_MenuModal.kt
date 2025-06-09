package com.iruka.tachibana.ui.screens





import android.content.Context
import android.view.Menu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.ui.Alignment // ← これをimport
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iruka.tachibana.R
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController

// MenuModal.kt 構成：背景 + モーダル + 選択肢5つ（全てYusei Font使用）

@Composable
fun MenuModal(onClose: () -> Unit, navController: NavController) {
    val showDialog = remember { mutableStateOf(false) }
    val yuseiFont = FontFamily(Font(R.font.yuseimagicregular))
    val showEventsDialog = remember { mutableStateOf(false) }

    ModalWrapper(onClose = onClose) {
        Box(modifier = Modifier.fillMaxSize()) {
            // ✅ 背景画像（最背面）
            Image(
                painter = painterResource(id = R.drawable.menu_modal),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )

            // ✅ コンテンツ（上に重ねる）
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 32.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            )  {
                val context = LocalContext.current

                fun launchUrl(url: String) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(intent)
                }

                MenuItem(stringResource(R.string.menu_inquiry)) {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:tachibana@proton.me") // 任意で別アドレスに変えてOK
                        putExtra(Intent.EXTRA_SUBJECT, "お問い合わせについて")
                        putExtra(Intent.EXTRA_TEXT, "以下にご用件をご記入ください。\n\n")
                    }

                    // メールアプリがあるか確認してから起動（安全策）
                    if (intent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(intent)
                    } else {
                        Toast.makeText(context, "メールアプリが見つかりません", Toast.LENGTH_SHORT).show()
                    }
                }


                MenuItem(stringResource(R.string.menu_review)) {
                    launchUrl("https://play.google.com/store/apps/details?id=com.iruka.tachibana")
                }

                MenuItem(stringResource(R.string.menu_Preferences )) {
                    showEventsDialog.value = true
                }
                if (showEventsDialog.value) {
                    val prefs = LocalContext.current.getSharedPreferences("tachibana_prefs", Context.MODE_PRIVATE)
                    val consumedEvents = prefs.getStringSet("consumedEvents", emptySet()) ?: emptySet()
                 //   val navController = LocalNavController.current // ← `CompositionLocal` または引数で渡してもOK

                    AlertDialog(
                        onDismissRequest = { showEventsDialog.value = false },
                        title = { Text("イベント履歴", fontFamily = yuseiFont) },
                        text = {
                            Column {
                                listOf(3, 7,10, 14, 21, 28).forEach { day ->
                                    if (consumedEvents.contains(day.toString())) {
                                        Text(
                                            text = "Day $day",
                                            fontFamily = yuseiFont,
                                            modifier = Modifier
                                                .clickable {
                                                    showEventsDialog.value = false
                                                    navController.navigate("event_day$day")
                                                }
                                                .padding(8.dp)
                                        )
                                    }
                                }
                            }
                        },
                        confirmButton = {
                            TextButton(onClick = { showEventsDialog.value = false }) {
                                Text("閉じる", fontFamily = yuseiFont)
                            }
                        }
                    )
                }

                MenuItem(stringResource(R.string.menu_premium)) {
                    launchUrl("https://your-premium.example.com") // ←後で差し替え
                }

                MenuItem(stringResource(R.string.menu_transfer)) {
                    showDialog.value = true
                }

            }

            if (showDialog.value) {
                AlertDialog(
                    onDismissRequest = { showDialog.value = true },
                    title = { Text(stringResource(R.string.menu_transfer_title), fontFamily = yuseiFont) },
                    text = {
                        Text(
                            stringResource(R.string.menu_transfer_description),
                            fontFamily = yuseiFont
                        )
                    },
                    confirmButton = {
                        TextButton(onClick = { showDialog.value = false }) {
                            Text(stringResource(R.string.menu_transfer_close), fontFamily = yuseiFont)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun MenuItem(text: String, fontSize: TextUnit = 20.sp, onClick: () -> Unit) {

    val yuseiFont = FontFamily(Font(R.font.yuseimagicregular))

    Text(
        text = text,
        fontSize = 30.sp,
        fontFamily = yuseiFont,
        color = Color.Black,
        modifier = Modifier
            .clickable { onClick() }
            .padding(vertical = 25.dp, horizontal = 12.dp)
            .fillMaxWidth()
    )
}
