package com.velocip.ybs.photos

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.velocip.ybs.photos.presentation.screens.user_photos.UserPhotosScreen
import com.velocip.ybs.photos.utils.Tags.TAG_USER_PHOTO
import org.junit.Rule
import org.junit.Test

class UserPhotosScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun photoDetails_userPhotoDetailsAreVisible() {
        composeTestRule.setContent {
            UserPhotosScreen(
                photos = uiPhotos,
                loading = false,
            )
        }

        uiPhotos.forEach { photo ->
            composeTestRule
                .onNodeWithTag("${TAG_USER_PHOTO}_${photo.id}")
                .assertExists()
        }
    }
}