package com.velocip.ybs.database.di

import android.content.Context
import androidx.room.Room
import com.velocip.ybs.database.dao.PhotoDao
import com.velocip.ybs.database.db.PhotoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesPhotoDatabase(
        @ApplicationContext context: Context,
    ): PhotoDatabase = Room.databaseBuilder(
        context,
        PhotoDatabase::class.java,
        "photo_db"
    ).build()

    @Provides
    fun providesPhotoDao(
        photoDatabase: PhotoDatabase,
    ): PhotoDao = photoDatabase.photoDao()
}