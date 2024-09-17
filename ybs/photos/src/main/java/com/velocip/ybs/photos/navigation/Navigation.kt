package com.velocip.ybs.photos.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.velocip.ybs.core.Screen
import com.velocip.ybs.photos.presentation.screens.photo_details.PhotoDetailsRoute
import com.velocip.ybs.photos.presentation.screens.photos.PhotosRoute
import com.velocip.ybs.photos.presentation.screens.user_photos.UserPhotosRoute


fun NavGraphBuilder.photosScreen(
    onReturn: () -> Unit,
    navigateToPhotoDetails: (String) -> Unit,
    navigateToUserPhotos: (String) -> Unit
) {
    composable<Screen.Photos> {
        PhotosRoute(
            onReturn = {
                onReturn()
            },
            navigateToPhotoDetails = { photoId ->
                navigateToPhotoDetails(photoId)
            },
            navigateToUserPhotos = { userId ->
                navigateToUserPhotos(userId)
            }
        )
    }
}


fun NavGraphBuilder.photoDetailsScreen(
    onReturn: () -> Unit
) {
    composable<Screen.PhotoDetails> { backStackEntry ->
        // Alternative way to pass arguments to the route if savedStateHandle is not used
        val photoId = backStackEntry.toRoute<Screen.PhotoDetails>().id
        PhotoDetailsRoute(
            onReturn = {
                onReturn()
            }
        )
    }
}


fun NavGraphBuilder.userPhotosScreen(
    onReturn: () -> Unit
) {
    composable<Screen.UserPhotos> { backStackEntry ->
        // Alternative way to pass arguments to the route if savedStateHandle is not used
        val userId = backStackEntry.toRoute<Screen.UserPhotos>().id
        UserPhotosRoute(
            onReturn = {
                onReturn()
            }
        )
    }
}