# voyager-hilt-ext

This is a Hilt extension for [Voyager](https://github.com/adrielcafe/voyager).

## Setup

Add the following dependency to your `build.gradle.kts` file:

```diff
plugins {
    id("com.google.devtools.ksp") version "<KSP_VERSION>"
    id("com.google.dagger.hilt.android") version "<HILT_VERSION>"
}

dependencies {
    implementation("com.google.dagger:hilt-android:<HILT_VERSION>")
    ksp("com.google.dagger:hilt-compiler:<HILT_VERSION>")
    ksp("com.google.dagger:dagger-compiler:<HILT_VERSION>")

+    compileOnly("io.github.qdsfdhvh:voyager-hilt-ext-anootations:<VERSION>")
+    ksp("io.github.qdsfdhvh:voyager-hilt-ext-compiler:<VERSION>")
}
```

## Usage

### @HiltScreenModel

Just like @HiltViewModel, but for Voyager ScreenModel.

```kotlin
@HiltScreenModel
class MyScreenModel @Inject constructor(
    private val myRepository: MyRepository,
) : ScreenModel {
    // ...
}
```

```kotlin
@HiltScreenModel(MyScreenModel.Factory::class)
class MyScreenModel @AssistedInject constructor(
    @Assisted val id: Int,
    private val myRepository: MyRepository,
) : ScreenModel {
    // ...
    @AssistedFactory
    interface Factory : ScreenModelFactory {
        fun create(id: Int): MyScreenModel
    }
}
```
