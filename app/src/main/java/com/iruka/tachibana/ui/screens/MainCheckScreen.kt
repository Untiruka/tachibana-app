package com.iruka.tachibana.ui.screens

// MainCheckScreen.kt


import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

@Composable
fun MainCheckScreen(navController: NavController) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        val prefs = context.getSharedPreferences("tachibana_prefs", Context.MODE_PRIVATE)
        val consumed = prefs.getStringSet("consumedEvents", emptySet()) ?: emptySet()
        val isNarrativeMode = PlayModeManager.getCurrentMode(context) == PlayMode.NARRATIVE
        val start = prefs.getLong("startTimeInMillis", System.currentTimeMillis())
        val elapsed = TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - start).toInt()

        val allEvents = if (isNarrativeMode) {
            listOf(7, 14, 21, 28, 30)
        } else {
            listOf(7, 14) // ← ノーマルはこれだけ
        }

        val pending = allEvents.filter { it <= elapsed && !consumed.contains(it.toString()) }

        val route = when (pending.minOrNull()) {
            7 -> "event_day7"
            14 -> "event_day14"
            21 -> "event_day21"
            28 -> "event_day28"
            30 -> "event_day30"
            else -> "main"
        }

        navController.navigate("loading/$route") {
            popUpTo("main_check") { inclusive = true }
        }
    }
}