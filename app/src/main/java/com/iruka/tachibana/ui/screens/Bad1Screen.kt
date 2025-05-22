

package com.iruka.tachibana.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.iruka.tachibana.R
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp

@Composable
fun Bad1Screen(navController: NavController) {
    val yuseiFont = FontFamily(Font(R.font.yuseimagicregular))

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.home_tachibana_question),
                contentDescription = "たちばなBAD顔",
                modifier = Modifier.size(200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "また明日からがんばろうね…",
                fontSize = 20.sp,
                fontFamily = yuseiFont,
                color = Color(0xFF6A4C4C)
            )
        }
    }
}
