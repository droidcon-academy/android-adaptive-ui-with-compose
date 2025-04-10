package com.droidcon.adaptiveinbox.components

import androidx.annotation.RawRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.droidcon.adaptiveinbox.R
import com.droidcon.adaptiveinbox.utils.toHslColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackSupportedToolbarWithText(
    modifier: Modifier = Modifier,
    title: String? = null,
    subtitle: String? = null,
    hasBackButton: Boolean = false,
    onBackPressed: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        windowInsets = WindowInsets(0, 0, 0, 0),
        title = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                if (!title.isNullOrEmpty()) {
                    Text(
                        text = title.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                if (!subtitle.isNullOrEmpty()) {
                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = subtitle.toString(),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
        },
        navigationIcon = {
            if (hasBackButton) {
                FilledIconButton(
                    onClick = onBackPressed,
                    modifier = Modifier.padding(8.dp),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.inverseOnSurface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }
    )
}

@Composable
fun ProfilePicture(
    modifier: Modifier,
    url: String? = null,
    name: String? = null,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    textColor: Color = Color.White
) {
    if (!url.isNullOrEmpty()) {
        AsyncImage(
            modifier = modifier
                .clip(CircleShape),
            model = url,
            contentDescription = "Image",
        )
    } else {
        val nameInitial by remember(name) {
            mutableStateOf(
                name?.take(1)
                    ?.uppercase() ?: ""
            )
        }
        val color = remember(nameInitial) {
            Color(nameInitial.toHslColor())
        }

        Box(
            modifier = modifier
                .clip(CircleShape)
                .background(
                    color = color
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = nameInitial,
                style = textStyle,
                color = textColor
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchSupportedToolbar(
    modifier: Modifier = Modifier,
    placeholder: String,
    onSearchQueryChanged: (String) -> Unit,
) {
    var query by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val onExpandedChange: (Boolean) -> Unit = {
        expanded = it
    }
    DockedSearchBar(
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        modifier = modifier,
        content = {},
        inputField = {
            SearchBarDefaults.InputField(
                modifier = Modifier.fillMaxWidth(),
                query = query,
                onQueryChange = {
                    query = it
                    onSearchQueryChanged(query)
                },
                onSearch = { expanded = false },
                expanded = expanded,
                onExpandedChange = onExpandedChange,
                placeholder = { Text(text = placeholder) },
                leadingIcon = {
                    if (expanded) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .clickable {
                                    expanded = false
                                    query = ""
                                },
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            modifier = Modifier.padding(start = 16.dp),
                        )
                    }
                },
                trailingIcon = {
                    ProfilePicture(
                        modifier = Modifier
                            .padding(12.dp)
                            .size(32.dp),
                        name = stringResource(R.string.droidcon),
                    )
                },
            )
        },
    )
}

@Composable
fun LottieAnimator(
    modifier: Modifier = Modifier,
    @RawRes rawResId: Int
) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(rawResId)
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
    )
    LottieAnimation(
        modifier = modifier,
        composition = composition,
        progress = { progress },
    )
}

@Preview
@Composable
private fun SearchSupportedToolbarPreview() {
    SearchSupportedToolbar(
        modifier = Modifier
            .fillMaxWidth(),
        placeholder = "Search",
        onSearchQueryChanged = {
        }
    )
}

@Preview
@Composable
private fun BackSupportedToolbarWithTextWithBackPreview() {
    BackSupportedToolbarWithText(
        modifier = Modifier
            .fillMaxWidth(),
        title = "Hurrayy!!!!!",
        subtitle = "It's party",
        hasBackButton = true,
        onBackPressed = {},
    )
}

@Preview
@Composable
private fun BackSupportedToolbarWithTextPreview() {
    BackSupportedToolbarWithText(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        title = "Hurrayy!!!!!",
        subtitle = "It's party",
        hasBackButton = false,
        onBackPressed = {},
    )
}

@Preview
@Composable
private fun ProfilePicturePreview() {
    ProfilePicture(
        modifier = Modifier
            .size(40.dp),
        name = "Droidcon",
        textStyle = MaterialTheme.typography.bodyLarge,
        textColor = Color.White
    )
}