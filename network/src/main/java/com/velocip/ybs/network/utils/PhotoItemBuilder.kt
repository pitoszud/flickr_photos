package com.velocip.ybs.network.utils

import com.velocip.ybs.network.dto.photo_details.FlickrPhotoDetailsDto
import com.velocip.ybs.network.dto.photos.PhotoDto
import com.velocip.ybs.network.result.PhotoDetailsItem
import com.velocip.ybs.network.result.PhotoItem
import kotlinx.datetime.Clock
import javax.inject.Inject

class PhotoItemBuilderImpl @Inject constructor() : PhotoItemBuilder {

    override fun buildPhotoItem(photo: PhotoDto): PhotoItem {
        return PhotoItem(
            photoId = photo.id,
            userId = photo.owner.orEmpty(),
            userIcon = getProfilePhotoUrl(photo),
            photoUrl = buildPhotoUrl(photo),
            tags = getTags(photo.tags.orEmpty()),
            timestamp = Clock.System.now()
        )
    }

    private fun buildPhotoUrl(photo: PhotoDto): String {
        return "https://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}_m.jpg"
    }

    private fun getTags(tag: String): List<String> {
        if (tag.isEmpty()) return emptyList()
        return tag.split(" ")
    }

    private fun getProfilePhotoUrl(photo: PhotoDto): String {
        return "https://farm${photo.farm}.staticflickr.com/${photo.server}/buddyicons/${photo.owner}.jpg"
    }

    override fun buildPhotoItemDetails(photoDetails: FlickrPhotoDetailsDto): PhotoDetailsItem {
        return PhotoDetailsItem(
            id = photoDetails.photo.id,
            title = photoDetails.photo.title.content,
            description = photoDetails.photo.description.content,
            dateTaken = photoDetails.photo.dates.taken,
            views = photoDetails.photo.views.toIntOrNull() ?: 0,
            latitude = photoDetails.photo.location.latitude.toDoubleOrNull(),
            longitude = photoDetails.photo.location.longitude.toDoubleOrNull(),
            country = photoDetails.photo.location.country.content,
            takenBy = photoDetails.photo.owner.username,
        )
    }
}


interface PhotoItemBuilder {
    fun buildPhotoItem(photo: PhotoDto): PhotoItem
    fun buildPhotoItemDetails(photoDetails: FlickrPhotoDetailsDto): PhotoDetailsItem
}