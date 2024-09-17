package com.velocip.ybs.core.utils

import kotlinx.datetime.Instant

class SystemClock : Clock {
    override fun nowInstant(): Instant = kotlinx.datetime.Clock.System.now()
}

interface Clock {
    fun nowInstant(): Instant
}