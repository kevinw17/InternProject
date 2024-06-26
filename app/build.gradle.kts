plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinKapt)
    alias(libs.plugins.daggerHilt)
}

android {
    namespace = "com.example.internproject"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.internproject"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "CLIENT_ID","\"buojwn4j7smykbken5koabb6tg9m9m\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        viewBinding = true
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    testImplementation(libs.junit)
    testImplementation("junit:junit:4.12")
    testImplementation("junit:junit:4.12")
//    testImplementation(libs.junit.jupiter)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Dagger Hilt for Dependency Injection
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation (libs.okhttp)
    implementation(libs.logging.interceptor)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // ViewModel
    implementation(libs.lifecycle.viewmodel.ktx)

    // LiveData
    implementation(libs.lifecycle.livedata.ktx)

    implementation (libs.lifecycle.runtime.ktx)
    implementation(libs.glide)

    implementation(platform(libs.compose.bom))
    androidTestImplementation(platform(libs.compose.bom))

    implementation(libs.activity.compose)
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.compose.material)
    implementation(libs.material.icons.core)
    implementation(libs.material.icons.extended)
    implementation(libs.runtime.livedata)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.coil.compose)
    implementation(libs.animation)

    // Android Studio Preview support
    implementation (libs.ui.tooling.preview)
    debugImplementation (libs.ui.tooling)

    // Unit Test
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)

}
kapt {
    correctErrorTypes = true
}