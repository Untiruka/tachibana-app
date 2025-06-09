package com.iruka.tachibana.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.iruka.tachibana.R
import kotlinx.coroutines.delay


@Composable
fun MainIntroScreen(navController: NavController) {
    val context = LocalContext.current

    // ここでゼロ演出
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.main_intro_streak), fontSize = 20.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(stringResource(R.string.main_intro_next_goal), fontSize = 20.sp)
    }

    // 5秒後にEventDay3へ遷移
    LaunchedEffect(Unit) {
        delay(5000)
        navController.navigate("event_day3") {
            popUpTo("main_intro") { inclusive = true }
        }
    }
}
