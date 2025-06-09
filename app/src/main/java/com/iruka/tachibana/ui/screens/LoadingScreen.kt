package com.iruka.tachibana.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.iruka.tachibana.R
import kotlinx.coroutines.delay

@Composable
fun LoadingScreen(navController: NavController, next: String) {
    // ここでBGMなどを止める処理とかあればやる

    LaunchedEffect(Unit) {
        delay(1500)
        navController.navigate(next) {
            popUpTo("loading") { inclusive = true }
        }
    }

    // ローディング画面のUI（アニメーション等）
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

    }



    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.foregraoundicon),
                contentDescription = null,
                modifier = Modifier.size(200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("準備中…", fontSize = 18.sp, fontFamily = yuseiFont)
        }
    }
}
