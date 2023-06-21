package com.eagskunst.morky.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.eagskunst.morky.R
import com.eagskunst.morky.domain.entity.CharacterEntity
import com.eagskunst.morky.domain.entity.CharacterStatus
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.fresco.FrescoImage
import kotlin.math.absoluteValue

@Composable
fun CharacterImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    @DrawableRes preview: Int = 0,
) {
    FrescoImage(
        imageUrl = imageUrl,
        modifier = modifier,
        imageOptions = ImageOptions(
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
        ),
        previewPlaceholder = preview,
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CharacterInfoCard(
    character: CharacterEntity,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White,
) {
    Card(
        modifier = modifier.fillMaxWidth()
            .padding(30.dp)
            .background(backgroundColor),
    ) {
        AnimatedContent(targetState = character) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
                    .padding(20.dp),
            ) {
                val commonAlign = TextAlign.Center
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = commonAlign,
                )
                Text(
                    text = "${character.gender} - ${character.status.name}",
                    textAlign = commonAlign,
                )
                Text(
                    text = character.species,
                    textAlign = commonAlign,
                )
                Text(
                    text = "Last seen on ${character.location}",
                    textAlign = commonAlign,
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CharactersPager(
    characters: List<CharacterEntity>,
    modifier: Modifier = Modifier,
    pagerState: PagerState = rememberPagerState(),
) {
    HorizontalPager(
        pageCount = characters.size,
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 48.dp),
        modifier = modifier.fillMaxWidth(),
    ) { page ->
        val preview = if (characters[page].imageUrl.isEmpty()) {
            R.drawable.ic_launcher_foreground
        } else {
            0
        }
        Card(
            Modifier
                .graphicsLayer {
                    val pageOffset = (
                        (pagerState.currentPage - page) + pagerState
                            .currentPageOffsetFraction
                        ).absoluteValue

                    lerp(
                        start = 0.85f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f),
                    ).also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }

                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f),
                    )
                }
                .fillMaxWidth()
                .aspectRatio(1f),
        ) {
            Box {
                CharacterImage(
                    imageUrl = characters[page].imageUrl,
                    modifier = Modifier.fillMaxSize(),
                    preview = preview,
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CharactersPagerWithInfoCard(
    characters: List<CharacterEntity>,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState()
    var currentPage by remember { mutableStateOf(0) }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            currentPage = page
        }
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CharactersPager(characters = characters, pagerState = pagerState)
        CharacterInfoCard(character = characters[currentPage])
    }
}

@Preview
@Composable
fun CharacterPreview() {
    CharacterImage(imageUrl = "", preview = R.drawable.ic_launcher_background)
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun CharactersPagerPreview() {
    val characters = (0..3).map { i ->
        CharacterEntity(
            id = i,
            name = "Test$i",
            imageUrl = "",
            status = CharacterStatus.UNKNOWN,
            species = "Human",
            gender = "Male",
            location = "Earth",
        )
    }
    CharactersPager(characters = characters)
}

@Preview
@Composable
fun CharacterInfoCardPreview() {
    val character = CharacterEntity(
        id = 0,
        name = "Rick Sanchez",
        imageUrl = "",
        status = CharacterStatus.ALIVE,
        species = "Human",
        gender = "Male",
        location = "Earth (Replacement Dimension)",
    )
    CharacterInfoCard(character = character)
}
