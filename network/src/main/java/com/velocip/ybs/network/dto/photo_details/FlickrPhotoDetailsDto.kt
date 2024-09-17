package com.velocip.ybs.network.dto.photo_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FlickrPhotoDetailsDto(
    @SerialName("photo")
    val photo: PhotoDetailsDto = PhotoDetailsDto(),
    @SerialName("stat")
    val stat: String = ""
)