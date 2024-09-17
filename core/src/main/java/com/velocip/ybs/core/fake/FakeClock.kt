package com.velocip.ybs.core.fake

import com.velocip.ybs.core.utils.Clock
import kotlinx.datetime.Instant
import kotlin.time.Duration

class FakeClock(private var fakeTime: Instant) : Clock {

    override fun nowInstant(): Instant = fakeTime

    fun advanceTimeBy(duration: Duration) {
        fakeTime += duration
    }
}