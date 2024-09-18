plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.velocip.ybs.database"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {

    implementation(project(":core"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Room + support libs
    ksp(libs.androidx.room.compiler)
    implementation(libs.bundles.room)
    implementation(libs.kotlinx.serialization.json)


    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    debugImplementation(libs.ui.tooling)
    testImplementation(libs.hilt.android.testing)
    ksp(libs.hilt.android.compiler)


    // Tests
    implementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.google.truth)
    testImplementation(libs.test.mockk)
    testImplementation(libs.test.robolectric)
}