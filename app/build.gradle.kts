plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.rove"  // The namespace for the application
    compileSdk = 34  // The SDK version to compile the app against

    defaultConfig {
        applicationId = "com.example.rove"  // The application ID for the app
        minSdk = 22  // Minimum SDK version the app supports
        targetSdk = 34  // Target SDK version the app aims to support
        versionCode = 1  // Version code for the app
        versionName = "1.0"  // Version name for the app

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"  // Test runner for instrumentation tests
        vectorDrawables {
            useSupportLibrary = true  // Enable support library for vector drawables
        }
    }

    applicationVariants.configureEach {
        resValue("string", "versionName", versionName)  // Add versionName as a string resource for each variant
    }

    buildTypes {
        release {
            isMinifyEnabled = true  // Enable code minification for release build
            isShrinkResources = true  // Enable resource shrinking for release build
            resValue("string", "app_name", "@string/app_name_release")  // Set app name for release build
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),  // Default ProGuard rules
                "proguard-rules.pro"  // Custom ProGuard rules
            )
        }
        debug {
            applicationIdSuffix = ".debug"  // Suffix for the application ID in debug build
            resValue("string", "app_name", "@string/app_name_debug")  // Set app name for debug build
            isDebuggable = true  // Enable debugging for debug build
        }
    }

    bundle {
        language {
            enableSplit = false  // Disable language splits in the app bundle
        }
    }

    compileOptions {
        encoding = "UTF-8"  // Set encoding to UTF-8
        sourceCompatibility = JavaVersion.VERSION_1_8  // Java source compatibility version
        targetCompatibility = JavaVersion.VERSION_1_8  // Java target compatibility version
    }

    kotlinOptions {
        jvmTarget = "1.8".toString()  // JVM target version for Kotlin
    }

    buildFeatures {
        compose = true  // Enable Jetpack Compose
        viewBinding = true  // Enable view binding
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"  // Jetpack Compose compiler extension version
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"  // Exclude certain resources from packaging
        }
    }
}

dependencies {
    // AndroidX dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.window)
    implementation(libs.androidx.preference.ktx)

    // Testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //support dependencies
    implementation(libs.androidx.activity)
    implementation(libs.androidx.fragment.ktx)

    // 3rd party dependencies
    implementation(libs.coil)  // Image loading library
    implementation(libs.moshi)  // JSON parsing library
    implementation(libs.library)
    implementation(libs.androidx.media)
    implementation(libs.preference.ktx)
    implementation(libs.androidx.browser)
}
