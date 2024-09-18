package com.velocip.ybs.network.dto.photo_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotesDto(
    @SerialName("note")
    val note: List<String?>? = emptyList()
)