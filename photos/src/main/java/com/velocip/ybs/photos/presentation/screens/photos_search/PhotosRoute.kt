package com.velocip.ybs.photos.presentation.screens.photos_search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.velocip.ybs.model.PhotoDetails
import com.velocip.ybs.model.PhotoItemUi
import com.velocip.ybs.model.PhotoLocation
import com.velocip.ybs.model.PhotosUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PhotosRoute(
    onReturn: () -> Unit,
    viewModel: PhotosViewModel = hiltViewModel(),
    navigateToPhotoDetails: (String) -> Unit,
    navigateToUserPhotos: (String) -> Unit
) {
    val photosUiState by viewModel.photosUiState.collectAsStateWithLifecycle()
    PhotosScreen(
        photosUiState = photosUiState,
        navigateToPhotoDetails = navigateToPhotoDetails,
        navigateToUserPhotos = navigateToUserPhotos,
        searchPhotos = { query ->
            viewModel.searchPhotos(query, true)
        }
    )


}

@Composable
fun PhotosScreen(
    photosUiState: PhotosUiState,
    navigateToPhotoDetails: (String) -> Unit,
    navigateToUserPhotos: (String) -> Unit,
    searchPhotos: (String) -> Unit
) {
    var textState by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val viewScope = rememberCoroutineScope()

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 34.dp)
    ) {
        SearchPhotoInput(
            userInput = textState,
            onValueChange = {
                textState = it
            },
            onSearchPhotos = {
                keyboardController?.hide()
                searchPhotos(textState)
                viewScope.launch {
                    delay(100)
                    textState = ""
                }
            }
        )
        Spacer(Modifier.height(34.dp))

        LazyColumn {
            items(photosUiState.photos) { photo ->
                PhotoItem(
                    onPhotoClick = { photoId ->
                        navigateToPhotoDetails(photoId)
                    },
                    onUserIconClick = { userId ->
                        navigateToUserPhotos(userId)
                    },
                    photoItem = photo
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PhotosRoutePreview() {
    PhotosScreen(
        photosUiState = PhotosUiState(
            isLoading = false,
            errorMessage = null,
            photos = listOf(
                PhotoItemUi(
                    id = "customer_id",
                    userId = "happy_ybs_customer_id",
                    userIcon = "https://randomuser.me/api/portraits",
                    photoUrl = "https://user.photo.com",
                    tags = listOf("Nature", "Landscape", "Mountain", "River"),
                    details = PhotoDetails(),
                    location = PhotoLocation()
                ),
                PhotoItemUi(
                    id = "customer_id",
                    userId = "happy_ybs_customer_id",
                    userIcon = "https://randomuser.me/api/portraits",
                    photoUrl = "https://user.photo.com",
                    tags = listOf("Nature", "Landscape", "Mountain", "River"),
                    details = PhotoDetails(),
                    location = PhotoLocation()
                )
            )
        ),
        navigateToPhotoDetails = {},
        navigateToUserPhotos = {},
        searchPhotos = {}
    )
}