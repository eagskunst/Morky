package com.eagskunst.morky.domain.usecase

import com.eagskunst.morky.common.CoroutineDispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

abstract class BaseUseCase(protected val dispatchers: CoroutineDispatchers) {
    protected fun <T> Flow<T>.switchToIo() = flowOn(dispatchers.io)
    protected fun <T> Flow<T>.switchToComputation() = flowOn(dispatchers.computation)
}
