package com.velocip.ybs.model

/**
* UI Data class that represents the details of a photo.
* */
data class PhotoDetails(
    val title: String = "",
    val description: String = "",
    val dateTaken: String = "",
    val views: Int = 0,
    val country: String = "",
    val takenBy: String = ""
)