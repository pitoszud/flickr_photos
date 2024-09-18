package com.velocip.ybs.network.config

import kotlin.time.Duration

/**
* Holds the configuration for the cache
 * @param dataExpirationTime the time that the data will be considered valid
 *
 *
* */
data class CacheConfig(
    val dataExpirationTime: Duration
)