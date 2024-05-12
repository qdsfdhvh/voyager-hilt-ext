package com.seiko.voyager.hilt.ext.compiler

import com.squareup.kotlinpoet.ClassName

val ClassDaggerModule = ClassName("dagger", "Module")
val ClassDaggerBinds = ClassName("dagger", "Binds")
val ClassDaggerInstallIn = ClassName("dagger.hilt", "InstallIn")
val ClassDaggerIntoMap = ClassName("dagger.multibindings", "IntoMap")
val ClassActivityComponent = ClassName("dagger.hilt.android.components", "ActivityComponent")

val ClassScreenModel = ClassName("cafe.adriel.voyager.core.model", "ScreenModel")
val ClassScreenModelFactory = ClassName("cafe.adriel.voyager.hilt", "ScreenModelFactory")
val ClassScreenModelKey = ClassName("cafe.adriel.voyager.hilt", "ScreenModelKey")
val ClassScreenModelFactoryKey = ClassName("cafe.adriel.voyager.hilt", "ScreenModelFactoryKey")
