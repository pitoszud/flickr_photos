package com.velocip.ybs.network.dto.photo_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TagDto(
    @SerialName("author")
    val author: String = "",
    @SerialName("authorname")
    val authorname: String = "",
    @SerialName("_content")
    val content: String = "",
    @SerialName("id")
    val id: String = "",
    @SerialName("raw")
    val raw: String = ""
)