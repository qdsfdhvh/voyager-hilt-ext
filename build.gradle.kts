// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.android.library) apply false
  alias(libs.plugins.jetbrains.kotlin.android) apply false
  alias(libs.plugins.jetbrains.kotlin.jvm) apply false
  alias(libs.plugins.ksp) apply false
  alias(libs.plugins.dagger.hilt) apply false
  alias(libs.plugins.spotless)
}

spotless {
  kotlin {
    target("*.kt", "**/*.kt")
    targetExclude("build/", "**/build/")
    ktlint(libs.versions.ktlint.get())
      .editorConfigOverride(editorConfigOverride)
  }
  kotlinGradle {
    target("*.gradle.kts", "**/*.gradle.kts")
    targetExclude("build/", "**/build/")
    ktlint(libs.versions.ktlint.get())
      .editorConfigOverride(editorConfigOverride)
  }
}

val editorConfigOverride = mapOf(
  "ktlint_function_naming_ignore_when_annotated_with" to "Composable",
)
