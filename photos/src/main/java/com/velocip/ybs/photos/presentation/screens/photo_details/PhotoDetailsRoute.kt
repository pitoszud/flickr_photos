package com.velocip.ybs.photos.presentation.screens.photo_details

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.velocip.ybs.core.utils.AppConstants.SAMPLE_TEXT
import com.velocip.ybs.photos.R
import com.velocip.ybs.photos.presentation.components.ContentLoading
import com.velocip.ybs.photos.presentation.components.PhotosTopAppBar

@Composable
fun PhotoDetailsRoute(
    onReturn: () -> Unit,
    viewModel: PhotoDetailsViewModel = hiltViewModel()
) {

    val uiState by viewModel.photoDetailsState.collectAsStateWithLifecycle()

    BackHandler {
        onReturn()
    }

    PhotoDetailsScreen(
        onReturn = onReturn,
        title = uiState.photoItemUi?.details?.title.orEmpty(),
        dateTaken = uiState.photoItemUi?.details?.dateTaken.orEmpty(),
        description = uiState.photoItemUi?.details?.description.orEmpty(),
        views = uiState.photoItemUi?.details?.views,
        country = uiState.photoItemUi?.details?.country.orEmpty(),
        takenBy = uiState.photoItemUi?.details?.takenBy.orEmpty(),
        photoUrl = uiState.photoItemUi?.photoUrl.orEmpty(),
        isLoading = uiState.isLoading
    )
}


@Composable
fun PhotoDetailsScreen(
    onReturn: () -> Unit,
    photoUrl: String,
    title: String,
    dateTaken: String,
    description: String,
    views: Int?,
    country: String,
    takenBy: String,
    isLoading: Boolean,
    preview: Boolean = false
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(MaterialTheme.colorScheme.background)
    ) {

        PhotosTopAppBar(
            title = title,
            onReturn = onReturn
        )

        if (isLoading) {
            ContentLoading()
        } else {
            val imageModifier = Modifier.fillMaxWidth().padding(12.dp)
            if (preview) {
                Image(
                    modifier = imageModifier,
                    painter = painterResource(id = com.velocip.ybs.core.R.drawable.sample_image),
                    contentDescription = stringResource(id = R.string.search_result_photo)
                )
            } else {
                AsyncImage(
                    modifier = imageModifier,
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(photoUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = stringResource(id = R.string.photo_owner_icon),
                    contentScale = ContentScale.FillWidth
                )
            }

            DetailsContent(
                description = description,
                dateTaken = dateTaken,
                views = views,
                country = country,
                takenBy = takenBy
            )
        }
    }
}


@Composable
fun DetailsContent(
    description: String,
    dateTaken: String,
    views: Int?,
    country: String,
    takenBy: String
) {
    Column(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(28.dp))
        Text(
            text = "Date Taken: $dateTaken",
            style = MaterialTheme.typography.bodyLarge
        )
        views?.let {
            Text(
                text = "Views: $it",
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Country: $country",
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "Taken By: $takenBy",
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = "Location: $takenBy",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}


@Composable
@Preview(showBackground = true)
fun PhotoDetailsScreenPreview() {
    PhotoDetailsScreen(
        onReturn = {},
        photoUrl = "https://photos.com/6/3b_b.jpg",
        title = "This is a longer photo title. This app bar supports max lines 2",
        dateTaken = "2022-02-22",
        description = SAMPLE_TEXT,
        views = 100,
        country = "UK",
        takenBy = "Happy ybs customer",
        isLoading = false,
        preview = true
    )
}