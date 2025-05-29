package com.iruka.tachibana.ui.screens





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


// MenuModal.kt 構成：背景 + モーダル + 選択肢5つ（全てYusei Font使用）

@Composable
fun MenuModal(onClose: () -> Unit) {
    val showDialog = remember { mutableStateOf(false) }
    val yuseiFont = FontFamily(Font(R.font.yuseimagicregular))
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

                MenuItem("問い合わせ") {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:you@example.com") // ←ここあとで実メールに
                    }
                    context.startActivity(intent)
                }

                MenuItem("レビュー") {
                    launchUrl("https://play.google.com/store/apps/details?id=com.iruka.tachibana")
                }

                MenuItem("我が家") {
                    launchUrl("https://lucaverse-site.vercel.app/")
                }

                MenuItem("プレミアムプラン") {
                    launchUrl("https://your-premium.example.com") // ←後で差し替え
                }

                MenuItem("引継ぎ") {
                    showDialog.value = true
                }
            }

            if (showDialog.value) {
                AlertDialog(
                    onDismissRequest = {showDialog.value = true},
                    title = { Text("引継ぎについて", fontFamily = yuseiFont) },
                    text = {
                        Text(
                            "このアプリのデータはGoogleアカウントに自動でバックアップされます。\n\n" +
                                    "機種変更の前に 設定 > システム > バックアップ から有効になっていることをご確認ください。",
                            fontFamily = yuseiFont
                        )
                    },
                    confirmButton = {
                        TextButton(onClick = { showDialog.value = false }) {
                            Text("閉じる", fontFamily = yuseiFont)
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
