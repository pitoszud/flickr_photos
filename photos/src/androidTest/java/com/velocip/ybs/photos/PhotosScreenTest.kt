package com.velocip.ybs.photos

import androidx.activity.ComponentActivity
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.velocip.ybs.model.PhotoDetails
import com.velocip.ybs.model.PhotoItemUi
import com.velocip.ybs.model.PhotoLocation
import com.velocip.ybs.model.PhotosUiState
import com.velocip.ybs.photos.presentation.screens.photos_search.PhotosScreen
import com.velocip.ybs.photos.utils.Tags.TAG_PHOTO_CARD_TAGS_ROW
import com.velocip.ybs.photos.utils.Tags.TAG_SEARCH_ICON
import com.velocip.ybs.photos.utils.Tags.TAG_SEARCH_PHOTO_INPUT
import org.junit.Rule
import org.junit.Test


class PhotosScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun searchPhoto_tapOnSearchIcon_clearQueryInput() {
        composeTestRule.setContent {
            PhotosScreen(photosUiState = photosUiState)
        }

        composeTestRule
            .onNodeWithTag(TAG_SEARCH_PHOTO_INPUT)
            .performTextInput("Yorkshire")

        composeTestRule
            .onNodeWithTag(TAG_SEARCH_PHOTO_INPUT)
            .assertTextEquals("Yorkshire")

        composeTestRule
            .onNodeWithTag(TAG_SEARCH_ICON)
            .performClick()

        composeTestRule.waitUntil(timeoutMillis = 2000) {
            composeTestRule
                .onNodeWithTag(TAG_SEARCH_PHOTO_INPUT)
                .fetchSemanticsNode().config.getOrNull(SemanticsProperties.EditableText)?.text.isNullOrEmpty()
        }

        composeTestRule
            .onNodeWithTag(TAG_SEARCH_PHOTO_INPUT)
            .assertTextEquals("")
    }


    @Test
    fun defaultQuerySearch_photosAreVisible() {
        composeTestRule.setContent {
            PhotosScreen(photosUiState = photosUiState)
        }

        uiPhotos.forEach { photo ->
            composeTestRule
                .onNodeWithTag("photo_card_${photo.id}")
                .assertExists()
        }
    }


    @Test
    fun defaultQuerySearch_tagsAreDisplayed() {
        composeTestRule.setContent {
            PhotosScreen(photosUiState = photosUiState)
        }

        uiPhotos.forEach { photo ->
            composeTestRule
                .onNodeWithTag("${TAG_PHOTO_CARD_TAGS_ROW}_${photo.id}")
                .assertExists()
        }
    }






}

val uiPhotos = listOf(
    PhotoItemUi(
        id = "photo_1",
        userId = "usr_1",
        userIcon = "https://via.placeholder.com/150",
        photoUrl = "https://via.placeholder.com/600",
        tags = listOf("tag1, tag2"),
        details = PhotoDetails(),
        location = PhotoLocation(),
        query = "Yorkshire"
    ),
    PhotoItemUi(
        id = "photo_2",
        userId = "usr_1",
        userIcon = "https://via.placeholder.com/150",
        photoUrl = "https://via.placeholder.com/600",
        tags = listOf("tag1, tag2"),
        details = PhotoDetails(),
        location = PhotoLocation(),
        query = "Yorkshire"
    ),
    PhotoItemUi(
        id = "photo_3",
        userId = "usr_1",
        userIcon = "https://via.placeholder.com/150",
        photoUrl = "https://via.placeholder.com/600",
        tags = listOf("tag1, tag2"),
        details = PhotoDetails(),
        location = PhotoLocation(),
        query = "Yorkshire"
    ),
    PhotoItemUi(
        id = "photo_4",
        userId = "usr_2",
        userIcon = "https://via.placeholder.com/150",
        photoUrl = "https://via.placeholder.com/600",
        tags = listOf("tag1, tag2"),
        details = PhotoDetails(),
        location = PhotoLocation(),
        query = "Yorkshire"
    )
)

val photosUiState = PhotosUiState(
    isLoading = false,
    errorMessage = null,
    photos = uiPhotos
)