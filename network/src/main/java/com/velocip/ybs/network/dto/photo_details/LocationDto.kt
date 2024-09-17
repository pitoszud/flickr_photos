package com.velocip.ybs.network.dto.photo_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(
    @SerialName("accuracy")
    val accuracy: String = "",
    @SerialName("context")
    val context: String = "",
    @SerialName("country")
    val country: CountryDto = CountryDto(),
    @SerialName("county")
    val county: CountyDto = CountyDto(),
    @SerialName("latitude")
    val latitude: String = "",
    @SerialName("locality")
    val locality: LocalityDto = LocalityDto(),
    @SerialName("longitude")
    val longitude: String = "",
    @SerialName("neighbourhood")
    val neighbourhood: NeighbourhoodDto = NeighbourhoodDto(),
    @SerialName("region")
    val region: RegionDto = RegionDto()
)