package com.seiko.voyager.hilt.ext.demo

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.hilt.ScreenModelFactory
import com.seiko.voyager.hilt.ext.annotations.HiltScreenModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import javax.inject.Inject

@HiltScreenModel
class HomeScreenModel @Inject constructor() : ScreenModel {
  fun greet(): String {
    return "From HomeScreenModel!"
  }
}

@HiltScreenModel(DetailScreenModel.Factory::class)
class DetailScreenModel @AssistedInject constructor(@Assisted val id: Int) : ScreenModel {
  fun greet(): String {
    return "ID: $id from DetailScreenModel!"
  }

  @AssistedFactory
  interface Factory : ScreenModelFactory {
    fun create(id: Int): DetailScreenModel
  }
}
