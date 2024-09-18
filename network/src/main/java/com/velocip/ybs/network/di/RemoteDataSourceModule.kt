package com.velocip.ybs.network.di

import com.velocip.ybs.network.data_source.PhotoDefaultRemoteDataSource
import com.velocip.ybs.network.data_source.PhotoRemoteDataSource
import com.velocip.ybs.network.utils.PhotoItemBuilder
import com.velocip.ybs.network.utils.PhotoItemBuilderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {

    @Binds
    internal abstract fun bindPhotoBuilder(impl: PhotoItemBuilderImpl): PhotoItemBuilder

    @Binds
    internal abstract fun bindPhotoRemoteDataSource(impl: PhotoDefaultRemoteDataSource): PhotoRemoteDataSource
}