package com.velocip.ybs.photos.presentation.screens.photo_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.velocip.ybs.core.Screen
import com.velocip.ybs.data.repo.PhotoSearchRepo
import com.velocip.ybs.model.PhotoItemUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoDetailsViewModel @Inject constructor(
    private val photoSearchRepo: PhotoSearchRepo,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _photoId = MutableStateFlow(savedStateHandle.toRoute<Screen.PhotoDetails>().id)
    private val photoId: StateFlow<String> = _photoId.asStateFlow()

    private val _photoDetailsState = MutableStateFlow(PhotoDetailsUiState())
    val photoDetailsState: StateFlow<PhotoDetailsUiState> = _photoDetailsState.asStateFlow()


    init {
        viewModelScope.launch {
            photoId.collect { id ->
                _photoDetailsState.value = PhotoDetailsUiState(isLoading = true)
                loadPhotoDetails(id)
            }
        }
    }

    private fun loadPhotoDetails(photoId: String) {
        viewModelScope.launch {
            photoSearchRepo.getPhotoDetails(photoId).fold(
                onSuccess = { photoDetails: PhotoItemUi ->
                    _photoDetailsState.value = PhotoDetailsUiState(
                        photoItemUi = photoDetails,
                        isLoading = false
                    )
                },
                onFailure = { throwable ->
                    _photoDetailsState.value = PhotoDetailsUiState(
                        isLoading = false,
                        errorMessage = throwable.message
                    )
                }
            )
        }

    }

}

data class PhotoDetailsUiState(
    val photoItemUi: PhotoItemUi? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)