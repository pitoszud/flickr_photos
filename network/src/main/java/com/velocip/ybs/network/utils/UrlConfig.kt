package com.velocip.ybs.network.utils

class UrlConfig : UrlConfigInterface {


    override fun getBaseUrl(): String {
        return "https://api.flickr.com/services/rest/"
    }

    override fun getMethod(apiMethod: ApiMethod): String {
        return when (apiMethod) {
            ApiMethod.PHOTOS_SEARCH -> "flickr.photos.search"
            ApiMethod.PHOTOS_INFO -> "flickr.photos.getInfo"
            ApiMethod.PEOPLE_PHOTOS -> "flickr.people.getPhotos"
        }
    }
}

/**
* Network-call url configuration.
* */
interface UrlConfigInterface {
    fun getBaseUrl(): String
    fun getMethod(apiMethod: ApiMethod): String
}

enum class ApiMethod {
    PHOTOS_SEARCH,
    PHOTOS_INFO,
    PEOPLE_PHOTOS
}