package com.velocip.ybs.model

/**
* State of the photos screen(s) held in ViewModels
 * @param photos list of photos with user's icon and tags
 *
* */
data class PhotosUiState(
    val isLoading: Boolean,
    val errorMessage: String?,
    val photos: List<PhotoItemUi>
)