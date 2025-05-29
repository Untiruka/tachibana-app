package com.iruka.tachibana


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.iruka.tachibana.ui.screens.AudioManager
import com.iruka.tachibana.ui.screens.Bad1Screen
import com.iruka.tachibana.ui.screens.InitialScreen
import com.iruka.tachibana.ui.screens.LoadingScreen
import com.iruka.tachibana.ui.screens.MainScreen
import com.iruka.tachibana.ui.screens.PreInitialScreen
import com.iruka.tachibana.ui.theme.TachibanaTheme

// 今は使わないが、Google連携のために保持（再有効化しやすく）
/*
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.android.gms.common.api.ApiException
import org.json.JSONObject
import java.net.URL
import java.net.HttpURLConnection
*/

class MainActivity : ComponentActivity() {

    // private val RC_SIGN_IN = 100 // ← Google連携用のリクエストコード

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // --- Google連携（現在は封印中） ---
        /*
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestScopes(Scope("https://www.googleapis.com/auth/calendar"))
            .requestServerAuthCode("YOUR_WEB_CLIENT_ID")
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
        */

        // --- Compose UI 設定 ---
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            TachibanaTheme {
                val navController = rememberNavController()

                val sharedPref = getSharedPreferences("tachibana_prefs", MODE_PRIVATE)
                val isInitialized = sharedPref.contains("startTimeInMillis")

                // 起動先ルート判定（本番用）
                val startDestination = if (isInitialized) "main" else "preinitial"

                // デバッグ用に一時的に固定したい場合はこちらを使用
                // val startDestination = "bad1"

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = startDestination,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("preinitial") {
                            PreInitialScreen(
                                modifier = Modifier.fillMaxSize(),
                                navController = navController
                            )
                        }
                        composable("initial") {
                            InitialScreen(
                                modifier = Modifier.fillMaxSize(),
                                navController = navController
                            )
                        }
                        composable("loading/{next}") { backStackEntry ->
                            val next = backStackEntry.arguments?.getString("next") ?: "main"
                            LoadingScreen(navController = navController, next = next)
                        }
                        composable("main") {
                            MainScreen(navController = navController)
                        }
                        composable("bad1") {
                            Bad1Screen(navController = navController)
                        }
                    }
                    }
                }
            }
        }


    override fun onStart() {
        super.onStart()
    //    if (AudioManager.isBgmEnabled) {
    //        AudioManager.playBgm(this, R.raw.marinba_march)
    //    }
    }

    override fun onStop() {
        super.onStop()
        AudioManager.stopBgm()
    }
}
    // Google認証結果受け取り（今は使用せず封印）
    /*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                val authCode = account?.serverAuthCode

                val prefs = getSharedPreferences("tachibana_prefs", MODE_PRIVATE)

                Thread {
                    try {
                        val tokenResponse =
                            URL("https://oauth2.googleapis.com/token").openConnection().apply {
                                doOutput = true
                                setRequestProperty(
                                    "Content-Type",
                                    "application/x-www-form-urlencoded"
                                )
                                (this as HttpURLConnection).requestMethod = "POST"
                                outputStream.write(
                                    "code=$authCode&client_id=YOUR_CLIENT_ID&client_secret=YOUR_CLIENT_SECRET&redirect_uri=&grant_type=authorization_code"
                                        .toByteArray()
                                )
                            }.getInputStream().bufferedReader().readText()

                        val json = JSONObject(tokenResponse)
                        val accessToken = json.getString("access_token")
                        prefs.edit().putString("access_token", accessToken).apply()
                    } catch (e: Exception) {
                        Log.e("GOOGLE_TOKEN", "トークン取得失敗: ${e.message}")
                    }
                }.start()

                prefs.edit().putString("auth_code", authCode).apply()

            } catch (e: ApiException) {
                Log.e("GOOGLE_SIGNIN", "失敗…コード: ${e.statusCode}")
            }
        }
    }
    */

