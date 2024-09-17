package com.velocip.ybs.network.dto.photo_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GiftDto(
    @SerialName("eligible_durations")
    val eligibleDurations: List<String> = listOf(),
    @SerialName("gift_eligible")
    val giftEligible: Boolean = false,
    @SerialName("new_flow")
    val newFlow: Boolean = false
)