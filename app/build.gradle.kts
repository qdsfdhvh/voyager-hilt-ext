plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.jetbrains.kotlin.android)
  alias(libs.plugins.dagger.hilt)
  alias(libs.plugins.ksp)
}

android {
  namespace = "com.seiko.voyager.hilt.ext.demo"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.seiko.voyager.hilt.ext.demo"
    minSdk = 24
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    vectorDrawables {
      useSupportLibrary = true
    }
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro",
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
  buildFeatures {
    compose = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = "1.5.10"
  }
  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}

dependencies {
  implementation(libs.androidx.activity.compose)
  implementation(libs.bundles.androidx.compose)
  implementation(libs.bundles.voyager)
  debugImplementation(libs.bundles.androidx.compose.test)
  testImplementation(libs.junit)

  compileOnly(project(":hilt-ext-annotations"))
  ksp(project(":hilt-ext-compiler"))

  implementation(libs.hilt.android)
  ksp(libs.hilt.compiler)
  ksp(libs.dagger.compiler)
}
