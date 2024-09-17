package com.velocip.ybs.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.velocip.ybs.database.dao.PhotoDao
import com.velocip.ybs.database.entity.PhotoEntity
import com.velocip.ybs.database.utils.DatabaseConverter


@Database(
    entities = [PhotoEntity::class],
    version = 1,
    exportSchema = true,
    autoMigrations = []
)

@TypeConverters(DatabaseConverter::class)
abstract class PhotoDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao
}