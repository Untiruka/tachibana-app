package com.iruka.tachibana

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.iruka.tachibana.ui.screens.Bad1Screen
import com.iruka.tachibana.ui.screens.InitialScreen
import com.iruka.tachibana.ui.screens.LoadingScreen
import com.iruka.tachibana.ui.screens.MainScreen
import com.iruka.tachibana.ui.theme.TachibanaTheme
import java.util.Calendar


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(
            window,
            false
        ) // ← edge-to-edge対応（元の enableEdgeToEdge 相当）
        setContent {
            TachibanaTheme {
                val navController = rememberNavController()

                val sharedPref = getSharedPreferences("tachibana_prefs", MODE_PRIVATE)
                val isInitialized = sharedPref.contains("startTimeInMillis")
                val startDestination = if (isInitialized) "main" else "initial"

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = startDestination,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("initial") {
                            InitialScreen(
                                modifier = Modifier.fillMaxSize(),
                                navController = navController
                            )
                        }
                        composable("loading") {
                            LoadingScreen(navController = navController)
                        }

                        composable("main") {
                            MainScreen(navController = navController)
                            // ← SharedPreferencesからMainScreen内で読み取る
                        }
                        composable("bad1") {
                            Bad1Screen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}