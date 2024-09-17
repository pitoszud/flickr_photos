package com.velocip.ybs.network.dto.photo_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoDetailsDto(
    @SerialName("comments")
    val comments: CommentsDto = CommentsDto(),
    @SerialName("dates")
    val dates: DatesDto = DatesDto(),
    @SerialName("dateuploaded")
    val dateuploaded: String = "",
    @SerialName("description")
    val description: DescriptionDto = DescriptionDto(),
    @SerialName("editability")
    val editability: EditabilityDto = EditabilityDto(),
    @SerialName("farm")
    val farm: Int = 0,
    @SerialName("geoperms")
    val geoperms: GeopermsDto = GeopermsDto(),
    @SerialName("id")
    val id: String = "",
    @SerialName("isfavorite")
    val isfavorite: Int = 0,
    @SerialName("license")
    val license: String = "",
    @SerialName("location")
    val location: LocationDto = LocationDto(),
    @SerialName("media")
    val media: String = "",
    @SerialName("notes")
    val notes: NotesDto = NotesDto(),
    @SerialName("originalformat")
    val originalformat: String = "",
    @SerialName("originalsecret")
    val originalsecret: String = "",
    @SerialName("owner")
    val owner: OwnerDto = OwnerDto(),
    @SerialName("people")
    val people: PeopleDto = PeopleDto(),
    @SerialName("publiceditability")
    val publiceditability: PubliceditabilityDto = PubliceditabilityDto(),
    @SerialName("rotation")
    val rotation: Int = 0,
    @SerialName("safety_level")
    val safetyLevel: String = "",
    @SerialName("secret")
    val secret: String = "",
    @SerialName("server")
    val server: String = "",
    @SerialName("tags")
    val tags: TagsDto = TagsDto(),
    @SerialName("title")
    val title: TitleDto = TitleDto(),
    @SerialName("urls")
    val urls: UrlsDto = UrlsDto(),
    @SerialName("usage")
    val usage: UsageDto = UsageDto(),
    @SerialName("views")
    val views: String = "",
    @SerialName("visibility")
    val visibility: VisibilityDto = VisibilityDto()
)