package com.velocip.ybs.network.result

/**
* Streamlined DTO for photo details
* */
data class PhotoDetailsItem(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val dateTaken: String = "",
    val views: Int = 0,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val country: String = "",
    val takenBy: String = ""
)