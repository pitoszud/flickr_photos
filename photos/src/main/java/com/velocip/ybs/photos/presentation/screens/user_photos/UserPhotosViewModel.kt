package com.velocip.ybs.photos.presentation.screens.user_photos

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.velocip.ybs.core.Screen
import com.velocip.ybs.data.repo.PhotoSearchRepo
import com.velocip.ybs.model.PhotoItemUi
import com.velocip.ybs.model.PhotosUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserPhotosViewModel @Inject constructor(
    private val photoSearchRepo: PhotoSearchRepo,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    private val _errorMessage = MutableStateFlow<String?>(null)

    private val _userId = MutableStateFlow(savedStateHandle.toRoute<Screen.UserPhotos>().id)
    private val userId: StateFlow<String> = _userId.asStateFlow()


    private val photos: MutableStateFlow<List<PhotoItemUi>> = MutableStateFlow(emptyList())

    val photosUiState: StateFlow<PhotosUiState> =
        combine(_isLoading, _errorMessage, photos) { isLoading, errorMessage, photos ->
            PhotosUiState(
                isLoading = isLoading,
                errorMessage = errorMessage,
                photos = photos
            )
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            PhotosUiState(false, null, emptyList())
        )


    init {
        viewModelScope.launch {
            userId.collect { id ->
                loadUserPhotos(id)
            }
        }
    }


    private fun loadUserPhotos(id: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                photoSearchRepo.getUserPhotos(id).fold(
                    onSuccess = { photoDetails ->
                        _isLoading.value = false
                        photos.value = photoDetails
                    },
                    onFailure = { e ->
                        _isLoading.value = false
                        _errorMessage.value = e.message
                    }
                )
            } catch (e: Exception) {
                _isLoading.value = false
                _errorMessage.value = e.message
            }
        }
    }
}