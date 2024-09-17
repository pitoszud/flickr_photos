package com.velocip.ybs.network.dto.photos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotosDto(
    val page: Int = 0,
    val pages: Int = 0,
    @SerialName("perpage")
    val perPage: Int = 0,
    @SerialName("photo")
    val photos: List<PhotoDto> = emptyList(),
    val total: Int = 0
)