package com.iruka.tachibana.ui.screens



import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.iruka.tachibana.R
import com.iruka.tachibana.ui.screens.YuseiMagic

@Composable
fun ConfigModal(
    onClose: () -> Unit,
    edgeSide: EdgeSide,
    onEdgeSideChange: (EdgeSide) -> Unit,
    isBgmEnabled: Boolean,
    onBgmToggle: (Boolean) -> Unit,
    isSoundEnabled: Boolean,
    onSoundToggle: (Boolean) -> Unit,
    onCreditOpen: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(999f)
    ) {
        Image(
            painter = painterResource(id = R.drawable.config_backgroound1),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("設定", style = MaterialTheme.typography.headlineMedium, fontFamily = YuseiMagic, color = Color.White)

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("BGM", modifier = Modifier.weight(1f), fontFamily = YuseiMagic, color = Color.White)
                Switch(checked = isBgmEnabled, onCheckedChange = onBgmToggle)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("効果音", modifier = Modifier.weight(1f), fontFamily = YuseiMagic, color = Color.White)
                Switch(checked = isSoundEnabled, onCheckedChange = onSoundToggle)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "エッジパネルの位置",
                    modifier = Modifier.weight(1f),
                    fontFamily = YuseiMagic,
                    color = Color.White
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { onEdgeSideChange(EdgeSide.Left) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (edgeSide == EdgeSide.Left) Color.Gray else Color.DarkGray
                        )
                    ) {
                        Text("左", fontFamily = YuseiMagic)
                    }

                    Button(
                        onClick = { onEdgeSideChange(EdgeSide.Right) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (edgeSide == EdgeSide.Right) Color.Gray else Color.DarkGray
                        )
                    ) {
                        Text("右", fontFamily = YuseiMagic)
                    }
                }
            }

            Button(onClick = onCreditOpen) {
                Text("クレジットを見る", fontFamily = YuseiMagic)
            }

            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = onClose, modifier = Modifier.align(Alignment.End)) {
                Text("閉じる", fontFamily = YuseiMagic)
            }
        }
    }
}

@Composable
fun CreditModal(onClose: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF111111))
            .padding(24.dp)
            .zIndex(999f)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("クレジット", style = MaterialTheme.typography.headlineMedium, fontFamily = YuseiMagic, color = Color.White)
            Text("BGM：『マリンバマーチ』 by shimtone（DOVA-SYNDROME）\n" +
                    "BGM：『かえるのピアノ』 by こおろぎ（DOVA-SYNDROME）\n" +
                    "BGM：『はじまりのマリンバ』 by OK-Sounds（DOVA-SYNDROME）", fontFamily = YuseiMagic, color = Color.White)
            Text("効果音: 効果音：On-Jin ～音人～（https://on-jin.com/）\n", fontFamily = YuseiMagic, color = Color.White)
            Text("効BGM：春の箱舟 / まんぼう二等兵（DOVA-SYNDROME））\n", fontFamily = YuseiMagic, color = Color.White)

            Spacer(Modifier.height(32.dp))
            Button(onClick = onClose) {
                Text("閉じる", fontFamily = YuseiMagic)
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(999f),
                contentAlignment = Alignment.Center
            ) {
                EyeOfProvidenceAnimation()
            }
        }
    }
}

