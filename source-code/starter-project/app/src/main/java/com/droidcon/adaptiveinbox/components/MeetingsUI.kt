package com.droidcon.adaptiveinbox.components

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.navigation.NavigableSupportingPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberSupportingPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.window.layout.DisplayFeature
import com.droidcon.adaptiveinbox.R
import com.droidcon.adaptiveinbox.model.Carousel
import com.droidcon.adaptiveinbox.model.Meeting
import com.droidcon.adaptiveinbox.model.PaneType
import com.droidcon.adaptiveinbox.model.meetingsData
import com.droidcon.adaptiveinbox.utils.toDate
import com.droidcon.adaptiveinbox.utils.toFormattedString
import com.google.accompanist.adaptive.TwoPane
import com.google.accompanist.adaptive.VerticalTwoPaneStrategy

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun MeetingsUI(
    modifier: Modifier = Modifier,
    meetings: List<Meeting>,
    carousel: List<Carousel>,
    paneType: PaneType,
    displayFeatures: List<DisplayFeature>
) {
    @Composable
    fun MeetingsTutorialCarouselUI(
        modifier: Modifier = Modifier,
    ) {
        MeetingsTutorialCarousel(
            modifier = modifier,
            carousel = carousel
        )
    }

    @Composable
    fun MeetingsListUI(
        modifier: Modifier = Modifier,
    ) {
        MeetingsList(
            modifier = modifier,
            meetings = meetings,
        )
    }

    when(paneType) {
        PaneType.SINGLE_PANE -> {
            val scrollState = rememberScrollState()
            Column(
                modifier = modifier
                    .verticalScroll(scrollState)
            ) {
                MeetingsListUI(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                MeetingsTutorialCarouselUI(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }
        }
        PaneType.DUAL_PANE -> {
            TwoPane(
                modifier = modifier,
                first = {
                    MeetingsListUI(
                        modifier = Modifier
                            .fillMaxSize()
                    )
                },
                second = {
                    MeetingsTutorialCarouselUI(
                        modifier = Modifier
                            .fillMaxSize(),
                    )
                },
                strategy = VerticalTwoPaneStrategy(
                    splitFraction = 0.5f
                ),
                displayFeatures = displayFeatures
            )
        }
        PaneType.ADAPTIVE_PANE -> {
            val navigator = rememberSupportingPaneScaffoldNavigator()

            NavigableSupportingPaneScaffold(
                modifier = modifier,
                navigator = navigator,
                mainPane = {
                    AnimatedPane(
                        enterTransition = slideInHorizontally() + fadeIn(),
                        exitTransition = slideOutHorizontally() + fadeOut()
                    ) {
                        MeetingsListUI(
                            modifier = Modifier
                                .fillMaxSize(),
                        )
                    }
                },
                supportingPane = {
                    AnimatedPane(
                        enterTransition = slideInHorizontally() + fadeIn(),
                        exitTransition = slideOutHorizontally() + fadeOut()
                    ) {
                        MeetingsTutorialCarouselUI(
                            modifier = Modifier
                                .fillMaxSize(),
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun MeetingsTutorialCarousel(
    modifier: Modifier = Modifier,
    carousel: List<Carousel>
) {
    Column(
        modifier = modifier
    ) {
        val pagerState = rememberPagerState {
            carousel.size
        }
        val settledPage = pagerState.settledPage
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            state = pagerState
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                MeetingsCarouselItem(
                    modifier = Modifier
                        .padding(
                            16.dp
                        )
                        .aspectRatio(1f)
                        .clipToBounds()
                        .align(Alignment.Center),
                    carouselItem = carousel[page]
                )
            }
        }

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement
                .spacedBy(
                    space = 8.dp,
                    alignment = Alignment.CenterHorizontally
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            itemsIndexed(carousel) { index, data ->
                val color = if (index == settledPage) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
                val selectedSize = if (index == settledPage) {
                    14.dp
                } else {
                    10.dp
                }
                Canvas(
                    modifier = Modifier
                        .size(selectedSize)
                ) {
                    drawCircle(
                        color = color
                    )
                }
            }
        }
    }
}

@Composable
fun MeetingsCarouselItem(
    modifier: Modifier = Modifier,
    carouselItem: Carousel
) {
    LottieAnimator(
        modifier = modifier,
        rawResId = carouselItem.carouselLottieResId,
    )
}

@Composable
fun MeetingsList(
    modifier: Modifier = Modifier,
    meetings: List<Meeting>,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp
        )
    ) {
        BackSupportedToolbarWithText(
            modifier = Modifier
                .fillMaxWidth(),
            title = stringResource(R.string.upcoming_meetings),
            hasBackButton = false,
            onBackPressed = {},
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(
                space = 12.dp,
                alignment = Alignment.CenterVertically
            ),
            contentPadding = PaddingValues(
                horizontal = 16.dp
            )
        ) {
            items(meetings) { meeting ->
                MeetingsListItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    meeting = meeting
                )
            }
        }
    }
}

@Composable
fun MeetingsListItem(
    modifier: Modifier = Modifier,
    meeting: Meeting
) {
    Column(
        modifier = modifier
    ) {
        Card(
            modifier = modifier,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .weight(1f)
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(
                        space = 6.dp
                    )
                ) {
                    Text(
                        text = meeting.meetingsTitle.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = meeting.meetingTimeMillis.toDate().toFormattedString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                OutlinedButton(
                    onClick = {},
                    modifier = Modifier
                        .wrapContentSize(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceBright
                    )
                ) {
                    Text(
                        text = stringResource(R.string.join),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun MeetingsListItemPreview() {
    MeetingsListItem(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        meeting = meetingsData.meetings.first()
    )
}

@Preview
@Composable
private fun MeetingsListPreview() {
    MeetingsList(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        meetings = meetingsData.meetings,
    )
}

@Preview
@Composable
private fun MeetingsCarouselItemPreview() {
    MeetingsCarouselItem(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        carouselItem = meetingsData.carousel.first()
    )
}

@Preview
@Composable
private fun MeetingsUIPreview() {
    MeetingsUI(
        modifier = Modifier
            .fillMaxSize(),
        carousel = meetingsData.carousel,
        meetings = meetingsData.meetings,
        paneType = PaneType.SINGLE_PANE,
        displayFeatures = emptyList()
    )
}