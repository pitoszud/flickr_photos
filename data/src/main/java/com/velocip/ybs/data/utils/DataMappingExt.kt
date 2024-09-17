package com.velocip.ybs.data.utils

import com.velocip.ybs.database.entity.PhotoEntity
import com.velocip.ybs.model.PhotoDetails
import com.velocip.ybs.model.PhotoItemUi
import com.velocip.ybs.model.PhotoLocation
import com.velocip.ybs.network.result.PhotoItem


fun PhotoEntity.toPhotoUi() = PhotoItemUi(
   id = this.photoId,
    userId = this.userId,
    userIcon = this.userIcon,
    photoUrl = this.photoUrl,
    details = PhotoDetails(
        title = this.title,
        description = this.description,
        dateTaken = this.dateTaken,
        views = this.views,
        country = this.country,
        takenBy = this.takenBy
    ),
    location = PhotoLocation(
        country = this.country,
        latitude = this.latitude,
        longitude = this.longitude
    ),
    tags = this.tags,
)

fun PhotoItem.toPhotoEntity(userQuery: String): PhotoEntity {
    return PhotoEntity(
        photoId = this.photoId,
        userId = this.userId,
        userIcon = this.userIcon,
        photoUrl = this.photoUrl,
        tags = this.tags,
        userQuery = userQuery,
        photoDetailsId = "",
        title = "", // fetched from photo details
        description = "", // fetched from photo details
        dateTaken = "", // fetched from photo details
        views = 0, // fetched from photo details
        country = "", // fetched from photo details
        takenBy = "", // fetched from photo details
        latitude = null, // fetched from photo location
        longitude = null, // fetched from photo location
        timestamp = this.timestamp
    )
}


fun PhotoItem.toPhotoItemUi(): PhotoItemUi {
    return PhotoItemUi(
        id = this.photoId,
        userId = this.userId,
        userIcon = this.userIcon,
        photoUrl = this.photoUrl,
        details = PhotoDetails(
            title = "",
            description = "",
            dateTaken = "",
            views = 0,
            country = "",
            takenBy = ""
        ),
        location = PhotoLocation(
            country = "",
            latitude = 0.0,
            longitude = 0.0
        ),
        tags = this.tags,
    )
}