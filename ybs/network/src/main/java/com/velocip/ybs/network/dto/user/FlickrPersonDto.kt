package com.velocip.ybs.network.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class FlickrPersonDto(
    val person: PersonDto,
    val stat: String
)