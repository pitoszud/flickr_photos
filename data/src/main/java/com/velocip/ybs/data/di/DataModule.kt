package com.velocip.ybs.data.di

import com.velocip.ybs.data.repo.PhotoSearchRepo
import com.velocip.ybs.data.repo.PhotoSearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun bindPhotoRepository(photoRepository: PhotoSearchRepository): PhotoSearchRepo
}