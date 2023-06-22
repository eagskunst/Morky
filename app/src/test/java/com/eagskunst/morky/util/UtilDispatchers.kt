package com.eagskunst.morky.util

import com.eagskunst.morky.common.CoroutineDispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestDispatcher

object UtilDispatchers {

    fun createDefaultSchedulerAndDispatcher() = TestCoroutineScheduler().let {
        Pair(StandardTestDispatcher(it), it)
    }

    fun createTestCoroutineDispatchers(): Triple<CoroutineDispatchers, TestDispatcher, TestCoroutineScheduler> {
        val (testDispatcher, scheduler) = createDefaultSchedulerAndDispatcher()
        val dispatchers = CoroutineDispatchers(
            io = testDispatcher,
            computation = testDispatcher,
            main = testDispatcher,
        )
        return Triple(dispatchers, testDispatcher, scheduler)
    }
}
