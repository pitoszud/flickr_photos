package com.velocip.ybs.photos

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import com.velocip.ybs.data.fakes.FAKE_GET_PHOTOS_ERROR
import com.velocip.ybs.data.fakes.FakePhotoRepository
import com.velocip.ybs.model.PhotoDetails
import com.velocip.ybs.photos.SharedTestData.uiPhotos
import com.velocip.ybs.photos.presentation.screens.user_photos.UserPhotosViewModel
import com.velocip.ybs.testing.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class UserPhotosViewModelTest {

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    // Subject under test
    private lateinit var userPhotosViewModel: UserPhotosViewModel

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
    fun `should update state with user photos for the user id specified in args`() = runTest {
        // setup viewmodel
        val userId = "usr_1"
        userPhotosViewModel = UserPhotosViewModel(
            photoSearchRepo = photoRepository,
            SavedStateHandle(mapOf("id" to userId))
        )

        val uiState = userPhotosViewModel.photosUiState.first()
        assertThat(uiState).isNotNull()
        assertThat(uiState.isLoading).isFalse()
        assertThat(uiState.photos.size).isEqualTo(3)
        assertThat(uiState.photos[0].userId).isEqualTo(userId)
        assertThat(uiState.photos[1].userId).isEqualTo(userId)
        assertThat(uiState.photos[2].userId).isEqualTo(userId)

        assertThat(uiState.photos[0].id).isEqualTo(uiPhotos[0].id)
        assertThat(uiState.photos[1].id).isEqualTo(uiPhotos[1].id)
        assertThat(uiState.photos[2].id).isEqualTo(uiPhotos[2].id)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should update state with error if failed fetching user photos`() = runTest {
        photoRepository.setShouldThrowError(true)

        // setup viewmodel
        val userId = "usr_1"
        userPhotosViewModel = UserPhotosViewModel(
            photoSearchRepo = photoRepository,
            SavedStateHandle(mapOf("id" to userId))
        )
        advanceUntilIdle()

        val uiState = userPhotosViewModel.photosUiState.first()
        assertThat(uiState).isNotNull()
        assertThat(uiState.isLoading).isFalse()
        assertThat(uiState.photos).isEmpty()
        assertThat(uiState.errorMessage).isEqualTo(FAKE_GET_PHOTOS_ERROR)
    }


}