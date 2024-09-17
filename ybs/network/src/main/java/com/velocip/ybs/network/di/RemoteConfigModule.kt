package com.velocip.ybs.network.di

import com.velocip.ybs.network.data_source.RemoteConfigDataSource
import com.velocip.ybs.network.data_source.RemoteConfigDataSourceApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteConfigModule {

    @Provides
    @Singleton
    fun provideRemoteConfig() : RemoteConfigDataSourceApi = RemoteConfigDataSource()

}