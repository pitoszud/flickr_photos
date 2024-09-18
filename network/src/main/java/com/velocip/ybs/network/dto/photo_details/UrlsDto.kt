package com.velocip.ybs.network.dto.photo_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UrlsDto(
    @SerialName("url")
    val url: List<UrlDto> = listOf()
)