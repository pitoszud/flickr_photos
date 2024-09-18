package com.velocip.ybs.photos

import com.google.common.truth.Truth.assertThat
import com.velocip.ybs.data.fakes.FAKE_GET_PHOTOS_ERROR
import com.velocip.ybs.data.fakes.FakePhotoRepository
import com.velocip.ybs.model.PhotoDetails
import com.velocip.ybs.photos.SharedTestData.uiPhotos
import com.velocip.ybs.photos.SharedTestData.uiPhotos2
import com.velocip.ybs.photos.presentation.screens.photos_search.PhotosViewModel
import com.velocip.ybs.testing.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PhotosViewModelTest {

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    // Subject under test
    private lateinit var photosViewModel: PhotosViewModel

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
    fun `first load should search photos with a default query`() = runTest {
        //Dispatchers.setMain(StandardTestDispatcher())

        var isLoading: Boolean? = true
        val job = launch {
            photosViewModel.photosUiState.collect {
                isLoading = it.isLoading
            }
        }

        // setup viewmodel
        photosViewModel = PhotosViewModel(
            photoRepo = photoRepository
        )

        assertThat(isLoading).isTrue()
        advanceUntilIdle()

        val uiState = photosViewModel.photosUiState.first()
        assertThat(uiState).isNotNull()
        assertThat(uiState.isLoading).isFalse()
        assertThat(uiState.errorMessage).isNull()
        assertThat(uiState.photos.size).isEqualTo(4)
        assertThat(uiState.photos).isEqualTo(uiPhotos)

        assertThat(isLoading).isFalse()
        job.cancel()
    }


    @Test
    fun `user search should update state with photos from query`() = runTest {
        //Dispatchers.setMain(StandardTestDispatcher())

        var isLoading: Boolean? = true
        val job = launch {
            photosViewModel.photosUiState.collect {
                isLoading = it.isLoading
            }
        }

        // setup viewmodel
        photosViewModel = PhotosViewModel(
            photoRepo = photoRepository
        )

        assertThat(isLoading).isTrue()
        advanceUntilIdle()

        var uiState = photosViewModel.photosUiState.first()
        assertThat(uiState).isNotNull()
        assertThat(uiState.photos.size).isEqualTo(4)
        assertThat(uiState.photos).isEqualTo(uiPhotos)

        assertThat(isLoading).isFalse()

        photoRepository.addPhotos(uiPhotos2, true)

        photosViewModel.searchPhotos("query")

        uiState = photosViewModel.photosUiState.first()
        assertThat(uiState).isNotNull()
        assertThat(uiState.photos.size).isEqualTo(5)

        job.cancel()
    }


    @Test
    fun `search photos should update error state if failed searching photos`() = runTest {
        //Dispatchers.setMain(StandardTestDispatcher())

        var isLoading: Boolean? = true
        val job = launch {
            photosViewModel.photosUiState.collect {
                isLoading = it.isLoading
            }
        }

        photoRepository.setShouldThrowError(true)

        // setup viewmodel
        photosViewModel = PhotosViewModel(
            photoRepo = photoRepository
        )

        assertThat(isLoading).isTrue()
        advanceUntilIdle()

        photosViewModel.searchPhotos("query")

        val uiState = photosViewModel.photosUiState.first()
        assertThat(uiState).isNotNull()
        assertThat(uiState.isLoading).isFalse()
        assertThat(uiState.errorMessage).isEqualTo(FAKE_GET_PHOTOS_ERROR)
        assertThat(uiState.photos.size).isEqualTo(0)

        assertThat(isLoading).isFalse()
        job.cancel()
    }
}