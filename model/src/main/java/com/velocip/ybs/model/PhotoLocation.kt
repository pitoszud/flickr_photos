package com.velocip.ybs.model


/**
* UI Data class that represents the location of a photo.
 *
 * @property country the country where the photo was taken e.g "UK"
 * @property latitude the latitude where the photo was taken
 * @property longitude the longitude where the photo was taken
* */
data class PhotoLocation(
    val country: String = "",
    val latitude: Double? = null,
    val longitude: Double? = null
)