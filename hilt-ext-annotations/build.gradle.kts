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
