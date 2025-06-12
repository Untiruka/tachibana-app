package com.iruka.tachibana

import java.util.concurrent.TimeUnit

import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.android.gms.ads.MobileAds
import com.iruka.tachibana.ui.screens.AudioManager
import com.iruka.tachibana.ui.screens.Bad1Screen
import com.iruka.tachibana.ui.screens.Bad2Screen
import com.iruka.tachibana.ui.screens.Skipp
import com.iruka.tachibana.ui.screens.EventDay3Screen
import com.iruka.tachibana.ui.screens.EventDay14Screen
import com.iruka.tachibana.ui.screens.EventDay21Screen
import com.iruka.tachibana.ui.screens.EventDay28Screen
import com.iruka.tachibana.ui.screens.EventDay30Screen
import com.iruka.tachibana.ui.screens.EventDay7Screen
import com.iruka.tachibana.ui.screens.InitialScreen
import com.iruka.tachibana.ui.screens.LoadingScreen
import com.iruka.tachibana.ui.screens.MainCheckScreen
import com.iruka.tachibana.ui.screens.MainScreen
import com.iruka.tachibana.ui.screens.PreInitialScreen
import com.iruka.tachibana.ui.screens.TrueEndScreen
import com.iruka.tachibana.ui.theme.TachibanaTheme
import com.iruka.tachibana.ui.components.SecureScreenWrapper
import com.iruka.tachibana.ui.screens.Day25LineScreen
import com.iruka.tachibana.ui.screens.EndingRollScreen
import com.iruka.tachibana.ui.screens.EventDay10Screen
import com.iruka.tachibana.ui.screens.MainIntroScreen
import com.iruka.tachibana.ui.screens.ShopScreen

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
        MobileAds.initialize(this)
        val sharedPref = getSharedPreferences("tachibana_prefs", MODE_PRIVATE)
        AudioManager.isBgmEnabled = sharedPref.getBoolean("bgm_enabled", true)
        AudioManager.isSoundEnabled = sharedPref.getBoolean("sound_enabled", true)
      //  window.setFlags(
      //      WindowManager.LayoutParams.FLAG_SECURE,
    //        WindowManager.LayoutParams.FLAG_SECURE
   //     )
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
                if (!sharedPref.contains("startTimeInMillis")) {
                    val startTime = System.currentTimeMillis()
                    sharedPref.edit().putLong("startTimeInMillis", startTime).apply()
                }
                val startDestination = if (sharedPref.contains("startTimeInMillis")) "main_check" else "preinitial"


                // デバッグ用に一時的に固定したい場合はこちらを使用
                // val startDestination = "bad1"

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = startDestination,
                        modifier = Modifier.padding(innerPadding)
                    ) {



                        composable("main_check") {
                            val context = LocalContext.current
                            LaunchedEffect(Unit) {
                                val prefs = context.getSharedPreferences("tachibana_prefs", Context.MODE_PRIVATE)
                                val consumed = prefs.getStringSet("consumedEvents", emptySet()) ?: emptySet()
                                val start = prefs.getLong("startTimeInMillis", System.currentTimeMillis())
                                val elapsed = TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - start).toInt()
                                val pending = listOf(3, 7,10, 14, 21, 28, 30).filter { it <= elapsed && !consumed.contains(it.toString()) }

                                val route = when (pending.minOrNull()) {
                                    3 -> "event_day3"
                                    7 -> "event_day7"
                                    10 -> "event_day10"
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
                        composable("main?fromEnding={fromEnding}",
                            arguments = listOf(navArgument("fromEnding") {
                                defaultValue = "false"  // `var` じゃなくて `defaultValue =`
                            })
                        ) { backStackEntry ->
                            val fromEnding = backStackEntry.arguments?.getString("fromEnding") == "true"
                            MainScreen(navController = navController, fromEnding = fromEnding) // ← これだけでOK

                        }


                        composable("bad1") {
                            Bad1Screen(navController = navController)
                        }
                        composable("main_intro") {
                            MainScreen(navController = navController, forceZeroDisplay = true)
                        }


                        composable("event_day3") {
                            EventDay3Screen(navController = navController)
                        }
                        composable("shop_screen/{elapsedDays}") { backStackEntry ->
                            val days = backStackEntry.arguments?.getString("elapsedDays")?.toIntOrNull() ?: 0
                            ShopScreen(
                                onBack = { navController.popBackStack() },
                                elapsedDays = days  // ← ここでelapsedDaysを渡す
                            )
                        }
                        composable("event_day7") { EventDay7Screen(navController = navController) }
                        composable("event_day10") { EventDay10Screen(navController = navController) }

                        composable("event_day14") { EventDay14Screen(navController = navController) }
                        composable("event_day21") { EventDay21Screen(navController = navController) }

                        composable("event_day28") { EventDay28Screen(navController = navController) }
                        composable("event_day30") { EventDay30Screen(navController = navController) }
                        composable("bad2") {
                            SecureScreenWrapper {
                                Bad2Screen(navController = navController)

                            }
                        }
                        composable("true_end") {
                            SecureScreenWrapper {
                                TrueEndScreen(navController = navController)
                            }
                        }
                        composable("main_check") {
                            MainCheckScreen(navController = navController)
                        }
                        composable("ending_roll") {
                            EndingRollScreen(
                                onFinished = {
                                    navController.navigate("main?fromEnding=true") {
                                        popUpTo("ending_roll") { inclusive = true }
                                    }

                                }

                            )
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

