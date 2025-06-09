package com.iruka.tachibana.ui.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.iruka.tachibana.R

@Composable
fun EventDay10Screen(navController: NavController) {
    val context = LocalContext.current
    val yuseiFont = FontFamily(Font(R.font.yuseimagicregular))

    val lines = listOf(
        stringResource(R.string.day10_line_0),
        stringResource(R.string.day10_line_1),
        stringResource(R.string.day10_line_2),
        stringResource(R.string.day10_line_3),
        stringResource(R.string.day10_line_4),
        stringResource(R.string.day10_line_5),
        stringResource(R.string.day10_line_6),
        stringResource(R.string.day10_line_7),
        stringResource(R.string.day10_line_8),
        stringResource(R.string.day10_line_9),
        stringResource(R.string.day10_line_10),
        stringResource(R.string.day10_line_11),
        stringResource(R.string.day10_line_12),
        stringResource(R.string.day10_line_13),
        stringResource(R.string.day10_line_14),
        stringResource(R.string.day10_line_15),
        stringResource(R.string.day10_line_16),
        stringResource(R.string.day10_line_17),
        stringResource(R.string.day10_line_18),
        stringResource(R.string.day10_line_19),
        stringResource(R.string.day10_line_19_1),
        stringResource(R.string.day10_line_20),
        stringResource(R.string.day10_line_21),
        stringResource(R.string.day10_line_22),
        stringResource(R.string.day10_line_23),
        stringResource(R.string.day10_line_24),
        stringResource(R.string.day10_line_25),
        stringResource(R.string.day10_line_26),
        stringResource(R.string.day10_line_27),
        stringResource(R.string.day10_line_28),
        stringResource(R.string.day10_line_29),
        stringResource(R.string.day10_line_30),
        stringResource(R.string.day10_line_31),
        stringResource(R.string.day10_line_32),
        stringResource(R.string.day10_line_33),
        stringResource(R.string.day10_line_34),
        stringResource(R.string.day10_line_35),
        stringResource(R.string.day10_line_36),
        stringResource(R.string.day10_line_37),
        stringResource(R.string.day10_line_38),
        stringResource(R.string.day10_line_39),
        stringResource(R.string.day10_line_40),
        stringResource(R.string.day10_line_41),
        stringResource(R.string.day10_line_42),
        stringResource(R.string.day10_line_43),
        stringResource(R.string.day10_line_44),
        stringResource(R.string.day10_line_45),
        stringResource(R.string.day10_line_45_1),
        stringResource(R.string.day10_line_46),
        stringResource(R.string.day10_line_47),
        stringResource(R.string.day10_line_48),
        stringResource(R.string.day10_line_49),
        stringResource(R.string.day10_line_50),
        stringResource(R.string.day10_line_51),
        stringResource(R.string.day10_line_52),
        stringResource(R.string.day10_line_53),
        stringResource(R.string.day10_line_54),
        stringResource(R.string.day10_line_55),
        stringResource(R.string.day10_line_56),
        stringResource(R.string.day10_line_57),
        stringResource(R.string.day10_line_58),
        stringResource(R.string.day10_line_59),
        stringResource(R.string.day10_line_60),
        stringResource(R.string.day10_line_61),
        stringResource(R.string.day10_line_62),
        stringResource(R.string.day10_line_63),
        stringResource(R.string.day10_line_64),
        stringResource(R.string.day10_line_65),
        stringResource(R.string.day10_line_66),
        stringResource(R.string.day10_line_67),
        stringResource(R.string.day10_line_68),
        stringResource(R.string.day10_line_69),
        stringResource(R.string.day10_line_70),
        stringResource(R.string.day10_line_71),
        stringResource(R.string.day10_line_72),
        stringResource(R.string.day10_line_73),
        stringResource(R.string.day10_line_74),
        stringResource(R.string.day10_line_75),
        stringResource(R.string.day10_line_76),
        stringResource(R.string.day10_line_77),
        stringResource(R.string.day10_line_78),
        stringResource(R.string.day10_line_79),
        stringResource(R.string.day10_line_80),
        stringResource(R.string.day10_line_81)

    )

    val fadeTriggers = listOf(
        /////00////
        R.drawable.day10_1,
        /////01////
        R.drawable.day10_2,
        /////02////
        null,
        /////03////
        R.drawable.day10_3,
        /////04////
        null,
        /////05////
        null,
        /////06////
        null,
        /////07////
        R.drawable.day10_4,
        /////08////
        R.drawable.day10_5,
        /////09////
        R.drawable.day10_6,
        /////10////
        null,
        /////11////
        null,
        /////12////
        R.drawable.day10_7,
        /////13////
        null,
        /////14////
        R.drawable.day10_8,
        /////15////
        null,
        /////16////
        null,
        /////17////
        R.drawable.day10_9,
        /////18////
        R.drawable.day10_10,
        /////19////
        null,
        /////20////
        R.drawable.day10_11,
        /////21////
        null,
        /////22////
        R.drawable.day10_10,
        /////23////
        null,
        /////24////
        R.drawable.day10_11,
        /////25////
        null,
        /////26////
        R.drawable.day10_10,
        /////27////
        null,
        /////28////
        null,
        /////29////
        R.drawable.day10_12,
        /////30////
        null,
        /////31////
        R.drawable. day10_12_1,
        /////32////
        null,
        /////33////
        null,
        /////34////
        R.drawable.day10_13,
        /////35////
        null,
        /////36////
        null,
        /////37////
        R.drawable.day10_14,
        /////38////
        null,
        /////39////
        null,
        /////40////
        null,
        /////41////
        null,
        /////42////
        null,
        /////43////
        null,
        /////44////
        null,
        /////45////
        R.drawable.day10_15,
        /////46////
        R.drawable.day10_16,
        /////47////
        null,
        /////48////
        null,
        /////49////
        null,
        /////50////
        null,
        /////51////
        null,
        /////52////
        null,
        /////53////
        R.drawable.day10_15,
        /////54////
        null,
        /////55////
        R.drawable.day10_17,
        /////56////
        null,
        /////57////
        null,
        /////58////
        null,
        /////59////
        null,
        /////60////
        null,

        /////61////
        R.drawable.day10_18,

        /////62////
        null,
        /////63////
        null,
        /////64////
        R.drawable.day10_19,
        /////65////
        null,
        /////66////
        null,
        /////67////
        R.drawable.day10_21,
        /////68////
        null,
        /////69////
        null,
        /////70////
        null,
        /////71////
        null,
        /////72////
        null,
        /////73////
        null,
        /////74////

        R.drawable.day10_22,
        null,
        /////75////
        null,
        /////76////
        null,
        /////77////
        null,
        /////78////
        null,
        /////79////
        null, null,
        /////80////
        R.drawable.day10_last,
        /////81////
        null
    )


    LaunchedEffect(Unit) {
        AudioManager.stopBgm() // ← 念のため前のBGMを止める
        AudioManager.playBgm(context, R.raw.day10) // ← あなたのbad1用BGM
    }


    val index = remember { mutableStateOf(0) }
    var currentImageResId by remember { mutableStateOf(R.drawable.day10_1) }

    val currentLine = lines.getOrNull(index.value)

    LaunchedEffect(index.value) {
        fadeTriggers[index.value]?.let {
            currentImageResId = it
        }
    }

    // イベント消費登録（初回のみ）
    LaunchedEffect(Unit) {
        val prefs = context.getSharedPreferences("tachibana_prefs", Context.MODE_PRIVATE)
        val consumed = prefs.getStringSet("consumedEvents", emptySet()) ?: emptySet()
        prefs.edit().putStringSet("consumedEvents", consumed + "10").apply()
    }

    LaunchedEffect(index.value) {
        fadeTriggers[index.value]?.let {
            currentImageResId = it
        }

        when (index.value) {
            14 -> {
                AudioManager.playSE(context, R.raw.day10_rustling_grass)
            }
            29 -> {
                AudioManager.playSE(context, R.raw.day10_heavy_kick)
            }
            8,9 -> {
                AudioManager.playSE(context, R.raw.day10_punch_slow)
            }
            64-> {
                AudioManager.playSE(context, R.raw.day10_beach_ambient_se)
            }
            // 他にも必要なら追加してください
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFBEFEF))
            .padding(16.dp)
            .clickable {
                AudioManager.playSE(context, R.raw.cursor_move_se)
                if (index.value < lines.lastIndex) {
                    index.value++
                } else {
                    navController.navigate("main") {
                        popUpTo("event_day10") { inclusive = true }
                    }
                }
            }
    ) {
        Text(
            text = stringResource(R.string.day10_title),
            fontFamily = yuseiFont,
            fontSize = 20.sp,
            color = Color.Black
        )
        Spacer(Modifier.height(16.dp))

        Image(
            painter = painterResource(id = currentImageResId),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = currentLine ?: "",
            fontFamily = yuseiFont,
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        )

        Spacer(Modifier.height(24.dp))

    }
    Skipp.SkipButton(day = "10", navController = navController, context = context)

}

