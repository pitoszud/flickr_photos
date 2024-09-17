package com.velocip.ybs.core

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable data object Photos : Screen()
    @Serializable data class PhotoDetails(val id: String) : Screen()
    @Serializable data class UserPhotos(val id: String) : Screen()
}