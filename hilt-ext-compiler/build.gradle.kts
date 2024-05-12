plugins {
  alias(libs.plugins.jetbrains.kotlin.jvm)
  alias(libs.plugins.maven.publish)
  alias(libs.plugins.dokka)
}

java {
  sourceCompatibility = JavaVersion.toVersion(libs.versions.jvmToolchain.get())
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(libs.versions.jvmToolchain.get()))
  }
}

kotlin {
  sourceSets.all {
    languageSettings {
      optIn("com.google.devtools.ksp.KspExperimental")
    }
  }
}

dependencies {
  compileOnly(libs.ksp.api)
  implementation(project(":hilt-ext-annotations"))
  implementation(libs.kotlinpoet.ksp)
}
