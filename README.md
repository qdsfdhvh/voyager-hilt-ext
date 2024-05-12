# voyager-hilt-ext

This is a Hilt extension for [Voyager](https://github.com/adrielcafe/voyager).

## Setup

Add the following dependency to your `build.gradle.kts` file:

```kotlin
plugins {
    id("com.google.devtools.ksp") version "<KSP_VERSION>"
}

dependencies {
    compileOnly("io.github.qdsfdhvh:voyager-hilt-ext-anootations:<VERSION>")
    ksp("io.github.qdsfdhvh:voyager-hilt-ext-compiler:<VERSION>")
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
