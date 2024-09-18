package com.velocip.ybs.photos.presentation.screens.user_photos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.velocip.ybs.model.PhotoItemUi
import com.velocip.ybs.photos.R
import com.velocip.ybs.photos.presentation.components.ContentLoading
import com.velocip.ybs.photos.presentation.components.PhotosTopAppBar

@Composable
fun UserPhotosRoute(
    onReturn: () -> Unit,
    viewModel: UserPhotosViewModel = hiltViewModel()
) {
    val userPhotosState by viewModel.photosUiState.collectAsStateWithLifecycle()

    UserPhotosScreen(
        onReturn = onReturn,
        photos = userPhotosState.photos,
        loading = userPhotosState.isLoading
    )
}


@Composable
fun UserPhotosScreen(
    onReturn: () -> Unit,
    photos: List<PhotoItemUi>,
    loading: Boolean
) {
    Column(modifier = Modifier.fillMaxSize()) {
        PhotosTopAppBar(
            title = stringResource(R.string.user_photos),
            onReturn = onReturn
        )
        if (loading) {
            ContentLoading()
        } else {
            LazyColumn {
                items(photos) { photo ->
                    UserPhotoItem(photoItem = photo)
                }
            }
        }
    }
}