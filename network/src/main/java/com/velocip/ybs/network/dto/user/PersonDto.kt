package com.velocip.ybs.network.dto.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonDto(
    val description: DescriptionDto,
    val expire: String,
    @SerialName("has_adfree")
    val hasAdFree: Int,
    @SerialName("has_free_educational_resources")
    val hasFreeEducationalResources: Int,
    @SerialName("has_free_standard_shipping")
    val hasFreeStandardShipping: Int,
    @SerialName("has_stats")
    val hasStats: Int,
    @SerialName("iconfarm")
    val iconFarm: Int,
    @SerialName("iconserver")
    val iconServer: String,
    val id: String,
    @SerialName("is_deleted")
    val isDeleted: Int,
    @SerialName("ispro")
    val isPro: Int,
    @SerialName("mobileurl")
    val mobileUrl: MobileurlDto,
    @SerialName("nsid")
    val nsId: String,
    @SerialName("path_alias")
    val pathAlias: String? = null,
    val photos: PhotosDto,
    @SerialName("photosurl")
    val photosUrl: PhotosurlDto,
    @SerialName("pro_badge")
    val proBadge: String,
    @SerialName("profileurl")
    val profileUrl: ProfileurlDto,
    val username: UsernameDto
)