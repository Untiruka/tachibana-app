package com.iruka.tachibana.ui.screens



import android.content.Context
import android.media.MediaPlayer
import androidx.annotation.RawRes

object AudioManager {
    var isSoundEnabled: Boolean = true
    var isBgmEnabled: Boolean = true

    private var bgmPlayer: MediaPlayer? = null

    fun playBgm(context: Context, @RawRes musicResId: Int) {
        if (!isBgmEnabled) return

        stopBgm() // ← 🔥 これを追加！！

        bgmPlayer = MediaPlayer.create(context, musicResId).apply {
            isLooping = true
            setVolume(0.6f, 0.6f)
            start()
        }
    }

    fun stopBgm() {
        bgmPlayer?.stop()
        bgmPlayer?.release()
        bgmPlayer = null
    }

    fun playSE(context: Context, @RawRes soundResId: Int) {
        if (!isSoundEnabled) return
        val player = MediaPlayer.create(context, soundResId)
        player.setOnCompletionListener {
            it.release()
        }
        player.start()
    }
}

