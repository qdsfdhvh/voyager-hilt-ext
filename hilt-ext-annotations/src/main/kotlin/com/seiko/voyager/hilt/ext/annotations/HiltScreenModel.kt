package com.seiko.voyager.hilt.ext.annotations

import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class HiltScreenModel(
  val assistedFactory: KClass<*> = Unit::class,
)
