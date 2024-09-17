package com.velocip.ybs.network.dto.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotosDto(
    val count: CountDto,
    @SerialName("firstdate")
    val firstDate: FirstdateDto,
    @SerialName("firstdatetaken")
    val firstDateTaken: FirstdatetakenDto
)