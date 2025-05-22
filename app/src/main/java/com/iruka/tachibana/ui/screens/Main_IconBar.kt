package com.iruka.tachibana.ui.screens



import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.iruka.tachibana.R
import androidx.compose.ui.Alignment

@Composable
fun Main_IconBar(
    modifier: Modifier = Modifier,
    onIconClick: (Int) -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        for (i in 1..5) {
            val iconRes = when (i) {
                1 -> R.drawable.home_icons1
                2 -> R.drawable.home_icons2
                3 -> R.drawable.home_icons3
                4 -> R.drawable.home_icons4
                5 -> R.drawable.home_icons6
                else -> R.drawable.home_icons1
            }
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = "icon $i",
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(60.dp)
                    .clickable { onIconClick(i) }
            )
        }
    }
}
