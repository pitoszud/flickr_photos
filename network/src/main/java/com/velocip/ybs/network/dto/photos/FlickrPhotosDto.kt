package com.velocip.ybs.network.dto.photos

import kotlinx.serialization.Serializable

@Serializable
data class FlickrPhotosDto(
    val photos: PhotosDto = PhotosDto(),
    val stat: String
)