package com.velocip.ybs.network.dto.photo_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DatesDto(
    @SerialName("lastupdate")
    val lastupdate: String = "",
    @SerialName("posted")
    val posted: String = "",
    @SerialName("taken")
    val taken: String = "",
    @SerialName("takengranularity")
    val takengranularity: Int = 0,
    @SerialName("takenunknown")
    val takenunknown: String = ""
)