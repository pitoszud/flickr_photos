package com.velocip.ybs.network.result

import kotlinx.datetime.Instant

/**
* Streamlined DTO representation of a photo item.
* */
data class PhotoItem(
    val photoId: String,
    val userId: String,
    val userIcon: String,
    val photoUrl: String,
    val tags: List<String>,
    val timestamp: Instant
)