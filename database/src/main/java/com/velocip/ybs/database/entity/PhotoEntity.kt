package com.velocip.ybs.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant

/**
 * Entity class for the photo table.
 * Combines both query photos and photo details.
 *
* */
@Entity(tableName = "query_photos")
data class PhotoEntity(
    @PrimaryKey
    val photoId: String,
    val photoDetailsId: String,
    val userId: String,
    val userIcon: String,
    val photoUrl: String,
    val tags: List<String>,
    val userQuery: String,
    val title: String,
    val description: String,
    val dateTaken: String,
    val views: Int = 0,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val country: String = "",
    val takenBy: String,
    val timestamp: Instant
)