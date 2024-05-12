plugins {
  alias(libs.plugins.jetbrains.kotlin.jvm)
}

java {
  sourceCompatibility = JavaVersion.toVersion(libs.versions.jvmToolchain.get())
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(libs.versions.jvmToolchain.get()))
  }
}
