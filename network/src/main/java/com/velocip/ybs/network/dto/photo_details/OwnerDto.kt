package com.velocip.ybs.network.dto.photo_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OwnerDto(
    @SerialName("gift")
    val gift: GiftDto = GiftDto(),
    @SerialName("iconfarm")
    val iconfarm: Int = 0,
    @SerialName("iconserver")
    val iconserver: String = "",
    @SerialName("location")
    val location: String = "",
    @SerialName("nsid")
    val nsid: String = "",
    @SerialName("path_alias")
    val pathAlias: String = "",
    @SerialName("realname")
    val realname: String = "",
    @SerialName("username")
    val username: String = ""
)