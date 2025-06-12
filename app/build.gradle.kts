plugins {
    id("com.android.application") version "8.9.1"
    id("org.jetbrains.kotlin.android") version "2.1.21"
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.21"

}

android {
    namespace = "com.iruka.tachibana"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.iruka.tachibana"
        minSdk = 28
        targetSdk = 35
        versionCode = 2
        versionName = "1.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isDebuggable = false // ★この1行を追加
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
                
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    composeOptions {

        kotlinCompilerExtensionVersion = "1.6.10"
    }

    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}



dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.espresso.core)
    implementation(libs.androidx.ui.text)
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.navigation.runtime.android)
   // implementation(libs.androidx.benchmark.macro)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.navigation.compose)
    dependencies {
        implementation("com.google.android.gms:play-services-ads:24.4.0")  // 最新のAdMob SDK
     //   implementation(libs.play.services.ads.compose)  // 適切なバージョンを使用
    }
    implementation("com.kizitonwose.calendar:compose:2.7.0")



    implementation(platform("io.coil-kt:coil-bom:2.6.0"))

    // 個々のCoilモジュールからはバージョン指定を削除します。
    // BOMが2.6.0を指定するので、これらのモジュールも自動的に2.6.0が適用されます。
    implementation("io.coil-kt:coil")         // :2.5.0 を削除
    implementation("io.coil-kt:coil-compose") // :2.5.0 を削除
    implementation("io.coil-kt:coil-gif")     // :2.5.0 を削除

// appレベルの build.gradle.kts に追加（dependencies内）
    implementation("com.google.android.gms:play-services-auth:21.0.0")
    implementation("com.google.android.gms:play-services-base:18.2.0")


}