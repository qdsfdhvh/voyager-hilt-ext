package com.seiko.voyager.hilt.ext.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MaterialTheme {
        Navigator(HomeScreen)
      }
    }
  }
}

object HomeScreen : Screen {
  private fun readResolve(): Any = HomeScreen

  @Composable
  override fun Content() {
    val navigator = LocalNavigator.currentOrThrow
    val screenModel = getScreenModel<HomeScreenModel>()
    Scaffold { innerPadding ->
      Column(
        Modifier
          .padding(innerPadding)
          .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
      ) {
        Text(screenModel.greet(), modifier = Modifier.padding(16.dp))
        Button(onClick = { navigator.push(DetailScreen(Random.nextInt())) }) {
          Text("Detail")
        }
      }
    }
  }
}

data class DetailScreen(val id: Int) : Screen {
  @Composable
  override fun Content() {
    val navigator = LocalNavigator.currentOrThrow
    val screenModel = getScreenModel<DetailScreenModel, DetailScreenModel.Factory> { factory ->
      factory.create(id)
    }
    Scaffold { innerPadding ->
      Column(
        Modifier
          .padding(innerPadding)
          .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
      ) {
        Text(screenModel.greet(), modifier = Modifier.padding(16.dp))
        TextButton(onClick = { navigator.pop() }) {
          Text("Back")
        }
      }
    }
  }
}
