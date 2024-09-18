package com.velocip.ybs.network.data_source

import com.velocip.ybs.network.config.CacheConfig
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours

class RemoteConfigDataSource @Inject constructor() : RemoteConfigDataSourceApi {

    override suspend fun getCacheRemoteConfig(): CacheConfig {
        return CacheConfig(
            dataExpirationTime = DEFAULT_DATA_EXPIRATION_TIME
        )
    }

    companion object {
        private val DEFAULT_DATA_EXPIRATION_TIME: Duration = 24.hours
    }


}

interface RemoteConfigDataSourceApi {
    suspend fun getCacheRemoteConfig(): CacheConfig
}