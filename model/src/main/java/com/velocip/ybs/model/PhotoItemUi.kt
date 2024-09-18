package com.velocip.ybs.model

/**
* UI Data class for photo item
* */
data class PhotoItemUi(
    val id: String,
    val userId: String,
    val userIcon: String,
    val photoUrl: String,
    val tags: List<String>,
    val details: PhotoDetails,
    val location: PhotoLocation,
    val query: String
)