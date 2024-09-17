package com.velocip.ybs.network.result

sealed class PhotoResult {
    data class Success(val photoItem: PhotoItem) : PhotoResult()
    data class Error(val message: String) : PhotoResult()
}