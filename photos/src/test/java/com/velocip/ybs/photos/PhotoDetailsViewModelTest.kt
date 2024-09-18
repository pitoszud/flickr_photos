package com.velocip.ybs.photos

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import com.velocip.ybs.data.fakes.FAKE_PHOTO_NOT_FOUND_ERROR
import com.velocip.ybs.data.fakes.FakePhotoRepository
import com.velocip.ybs.model.PhotoDetails
import com.velocip.ybs.photos.SharedTestData.uiPhotos
import com.velocip.ybs.photos.presentation.screens.photo_details.PhotoDetailsViewModel
import com.velocip.ybs.testing.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class PhotoDetailsViewModelTest {

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()


    // Subject under test
    private lateinit var photoDetailsViewModel: PhotoDetailsViewModel


    private lateinit var photoRepository: FakePhotoRepository

    @Before
    fun setupRepo() {
        photoRepository = FakePhotoRepository().apply {
            addPhotos(uiPhotos)
            addPhotoDetails(uiPhotos[0].id, PhotoDetails(title = "Photo 1 title"))
            addPhotoDetails(uiPhotos[1].id, PhotoDetails(title = "Photo 2 title"))
            addPhotoDetails(uiPhotos[2].id, PhotoDetails(title = "Photo 3 title"))
            addPhotoDetails(uiPhotos[3].id, PhotoDetails(title = "Photo 4 title"))
        }
    }

    @Test
    fun `should update state with details of photo with corresponding photo id from args`() = runTest {
        // setup viewmodel
        photoDetailsViewModel = PhotoDetailsViewModel(
            photoSearchRepo = photoRepository,
            SavedStateHandle(mapOf("id" to "photo_4"))
        )

        val expectedTitle = "Photo 4 title"
        val uiState = photoDetailsViewModel.photoDetailsState.first()
        assertThat(uiState).isNotNull()
        assertThat(uiState.isLoading).isFalse()
        assertThat(uiState.photoItemUi?.id).isEqualTo(uiPhotos[3].id)
        assertThat(uiState.photoItemUi?.details?.title).isEqualTo(expectedTitle)
    }


    @Test
    fun `should update state with error if failed fetching photo details`() = runTest {
        photoRepository.setShouldThrowError(true)

        // setup viewmodel
        photoDetailsViewModel = PhotoDetailsViewModel(
            photoSearchRepo = photoRepository,
            SavedStateHandle(mapOf("id" to "photo_4"))
        )

        val uiState = photoDetailsViewModel.photoDetailsState.first()
        assertThat(uiState).isNotNull()
        assertThat(uiState.isLoading).isFalse()
        assertThat(uiState.photoItemUi).isNull()
        assertThat(uiState.errorMessage).isEqualTo(FAKE_PHOTO_NOT_FOUND_ERROR)
    }
}