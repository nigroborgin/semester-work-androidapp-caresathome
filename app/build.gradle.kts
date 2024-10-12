plugins {
    id("com.android.application") version "8.7.0"
    id("org.jetbrains.kotlin.android") version "2.0.20"
    id("androidx.room") version "2.6.1"
    id("com.google.devtools.ksp") version "2.0.20-1.0.25"
}

android {
    namespace = "ru.marsu.semester_work_androidapp_caresathome"
    compileSdk = 34

    defaultConfig {
        applicationId = "ru.marsu.semester_work_androidapp_caresathome"
        minSdk = 34
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    room {
        schemaDirectory("$projectDir/schemas")
    }
}

dependencies {
    // My dependency
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.cardview)

    // DB dependency
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)

    // Default dependency
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}