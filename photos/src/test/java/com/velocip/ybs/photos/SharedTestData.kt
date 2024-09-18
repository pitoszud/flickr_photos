package com.velocip.ybs.photos

import com.velocip.ybs.model.PhotoDetails
import com.velocip.ybs.model.PhotoItemUi
import com.velocip.ybs.model.PhotoLocation

object SharedTestData {
    val uiPhotos = listOf(
        PhotoItemUi(
            id = "photo_1",
            userId = "usr_1",
            userIcon = "https://via.placeholder.com/150",
            photoUrl = "https://via.placeholder.com/600",
            tags = listOf("tag1, tag2"),
            details = PhotoDetails(),
            location = PhotoLocation()
        ),
        PhotoItemUi(
            id = "photo_2",
            userId = "usr_1",
            userIcon = "https://via.placeholder.com/150",
            photoUrl = "https://via.placeholder.com/600",
            tags = listOf("tag1, tag2"),
            details = PhotoDetails(),
            location = PhotoLocation()
        ),
        PhotoItemUi(
            id = "photo_3",
            userId = "usr_1",
            userIcon = "https://via.placeholder.com/150",
            photoUrl = "https://via.placeholder.com/600",
            tags = listOf("tag1, tag2"),
            details = PhotoDetails(),
            location = PhotoLocation()
        ),
        PhotoItemUi(
            id = "photo_4",
            userId = "usr_2",
            userIcon = "https://via.placeholder.com/150",
            photoUrl = "https://via.placeholder.com/600",
            tags = listOf("tag1, tag2"),
            details = PhotoDetails(),
            location = PhotoLocation()
        )
    )


    val uiPhotos2 = listOf(
        PhotoItemUi(
            id = "photo_5",
            userId = "usr_3",
            userIcon = "https://via.placeholder.com/150",
            photoUrl = "https://via.placeholder.com/600",
            tags = listOf("tag1, tag2"),
            details = PhotoDetails(),
            location = PhotoLocation()
        ),
        PhotoItemUi(
            id = "photo_6",
            userId = "usr_3",
            userIcon = "https://via.placeholder.com/150",
            photoUrl = "https://via.placeholder.com/600",
            tags = listOf("tag1, tag2"),
            details = PhotoDetails(),
            location = PhotoLocation()
        ),
        PhotoItemUi(
            id = "photo_7",
            userId = "usr_3",
            userIcon = "https://via.placeholder.com/150",
            photoUrl = "https://via.placeholder.com/600",
            tags = listOf("tag1, tag2"),
            details = PhotoDetails(),
            location = PhotoLocation()
        ),
        PhotoItemUi(
            id = "photo_8",
            userId = "usr_4",
            userIcon = "https://via.placeholder.com/150",
            photoUrl = "https://via.placeholder.com/600",
            tags = listOf("tag1, tag2"),
            details = PhotoDetails(),
            location = PhotoLocation()
        ),
        PhotoItemUi(
            id = "photo_9",
            userId = "usr_4",
            userIcon = "https://via.placeholder.com/150",
            photoUrl = "https://via.placeholder.com/600",
            tags = listOf("tag1, tag2"),
            details = PhotoDetails(),
            location = PhotoLocation()
        )
    )



}