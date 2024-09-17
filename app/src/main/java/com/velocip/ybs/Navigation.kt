package com.velocip.ybs

import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.velocip.ybs.core.Screen
import com.velocip.ybs.photos.navigation.photoDetailsScreen
import com.velocip.ybs.photos.navigation.photosScreen
import com.velocip.ybs.photos.navigation.userPhotosScreen

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val context = LocalContext.current as ComponentActivity

    BackHandler {
        context.finish()
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Photos
    ) {
        photosScreen(
            onReturn = context::finish,
            navigateToPhotoDetails = { photoId ->
                navController.navigate(route = Screen.PhotoDetails(id = photoId))
            },
            navigateToUserPhotos = { userId ->
                navController.navigate(route = Screen.UserPhotos(id = userId))
            }
        )

        photoDetailsScreen(
            onReturn = navController::popBackStack
        )

        userPhotosScreen(
            onReturn = navController::popBackStack
        )
    }
}