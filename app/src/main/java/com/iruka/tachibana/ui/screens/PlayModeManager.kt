package com.iruka.tachibana.ui.screens

import android.content.Context

object PlayModeManager {
    fun setCurrentMode(context: Context, mode: PlayMode) {
        val prefs = context.getSharedPreferences("tachibana_prefs", Context.MODE_PRIVATE)
        val modeStr = if (mode == PlayMode.NARRATIVE) "Narrative" else "Normal"
        prefs.edit().putString("play_mode", modeStr).apply()
    }
    fun getCurrentMode(context: Context): PlayMode {
        val prefs = context.getSharedPreferences("tachibana_prefs", Context.MODE_PRIVATE)
        val modeStr = prefs.getString("play_mode", "Normal")
        return if (modeStr == "Narrative") PlayMode.NARRATIVE else PlayMode.NORMAL
    }
}