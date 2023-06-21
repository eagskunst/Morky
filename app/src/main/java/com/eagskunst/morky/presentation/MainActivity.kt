package com.eagskunst.morky.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.eagskunst.morky.ui.component.CharacterLazyList
import com.eagskunst.morky.ui.theme.MorkyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MorkyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    MainView(viewModel)
                }
            }
        }
        viewModel.getCharacters()
    }
}

@Composable
fun MainView(viewModel: MainViewModel = hiltViewModel()) {
    val viewState by viewModel.state.collectAsState()

    when (val state = viewState) {
        is MainViewState.Characters -> CharacterLazyList(characters = state.characters)
        is MainViewState.Error -> Text("Error: ${state.cause}")
        MainViewState.Loading -> CircularProgressIndicator()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MorkyTheme {
        MainView()
    }
}
