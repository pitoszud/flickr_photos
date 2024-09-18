package com.velocip.ybs.network.fakes

import com.velocip.ybs.network.config.CacheConfig
import com.velocip.ybs.network.data_source.RemoteConfigDataSourceApi
import kotlin.time.Duration

class FakeRemoteConfigDataSource : RemoteConfigDataSourceApi {

    private var expirationTime: Duration = Duration.ZERO

    override suspend fun getCacheRemoteConfig(): CacheConfig {
        return CacheConfig(expirationTime)
    }

    fun setupExpirationTime(expirationTime: Duration) {
        this.expirationTime = expirationTime
    }
}