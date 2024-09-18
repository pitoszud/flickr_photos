package com.velocip.ybs.photos

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.velocip.ybs.photos.presentation.screens.photo_details.PhotoDetailsScreen
import com.velocip.ybs.photos.utils.Tags.TAG_PHOTO_DETAILS_COUNTRY
import com.velocip.ybs.photos.utils.Tags.TAG_PHOTO_DETAILS_DATE_TAKEN
import com.velocip.ybs.photos.utils.Tags.TAG_PHOTO_DETAILS_DESCRIPTION
import com.velocip.ybs.photos.utils.Tags.TAG_PHOTO_DETAILS_TAKEN_BY
import com.velocip.ybs.photos.utils.Tags.TAG_PHOTO_DETAILS_VIEWS
import org.junit.Rule
import org.junit.Test

class PhotoDetailsScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun photoDetails_userPhotoDetailsAreVisible() {
        val title = "Photo Title"
        val dateTaken = "2021-09-01"
        val description = "Photo Description"
        val views = 100
        val takenBy = "Happy YBS user"
        val country = "UK"
        composeTestRule.setContent {
            PhotoDetailsScreen(
                photoUrl = "https://www.example.com/photo.jpg",
                title = title,
                dateTaken = dateTaken,
                description = description,
                views = views,
                country = country,
                takenBy = takenBy,
                isLoading = false,
            )
        }

        composeTestRule
            .onNodeWithTag(TAG_PHOTO_DETAILS_DESCRIPTION)
            .assertExists()
            .assertTextEquals(description)

        composeTestRule
            .onNodeWithTag(TAG_PHOTO_DETAILS_DATE_TAKEN)
            .assertExists()
            .assertTextEquals("Date Taken: $dateTaken")

        composeTestRule
            .onNodeWithTag(TAG_PHOTO_DETAILS_VIEWS)
            .assertExists()
            .assertTextEquals("Views: $views")

        composeTestRule
            .onNodeWithTag(TAG_PHOTO_DETAILS_COUNTRY)
            .assertExists()
            .assertTextEquals("Country: $country")

        composeTestRule
            .onNodeWithTag(TAG_PHOTO_DETAILS_TAKEN_BY)
            .assertExists()
            .assertTextEquals("Taken By: $takenBy")


    }
}