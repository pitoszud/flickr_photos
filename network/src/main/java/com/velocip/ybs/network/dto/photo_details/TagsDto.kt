package com.velocip.ybs.network.dto.photo_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TagsDto(
    @SerialName("tag")
    val tag: List<TagDto> = listOf()
)