package com.eagskunst.morky.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.palette.graphics.Palette
import com.eagskunst.morky.ui.component.CharactersPagerWithInfoCard
import com.eagskunst.morky.ui.theme.MorkyTheme
import com.skydoves.landscapist.components.LocalImageComponent
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.palette.PalettePlugin
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Background colors animations
            var backgroundPalette by remember { mutableStateOf<Palette?>(null) }
            val backgroundColor by animateColorAsState(
                targetValue = backgroundPalette?.lightVibrantSwatch?.rgb?.let { Color(it) }
                    ?: Color.White,
            )
            val imageComponent = rememberImageComponent {
                +PalettePlugin { palette ->
                    backgroundPalette = palette
                }
            }

            MorkyTheme {
                CompositionLocalProvider(LocalImageComponent provides imageComponent) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = backgroundColor,
                    ) {
                        MainView(viewModel, backgroundPalette)
                    }
                }
            }
        }
        viewModel.getCharacters()
    }
}

@Composable
fun MainView(viewModel: MainViewModel = hiltViewModel(), backgroundPalette: Palette? = null) {
    val viewState by viewModel.state.collectAsState()

    when (val state = viewState) {
        is MainViewState.Characters -> CharactersPagerWithInfoCard(
            characters = state.characters,
            palette = backgroundPalette,
        )

        is MainViewState.Error -> Text("Error: ${state.cause}")
        MainViewState.Loading -> CircularProgressIndicator()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MorkyTheme {
        MainView(backgroundPalette = null)
    }
}
