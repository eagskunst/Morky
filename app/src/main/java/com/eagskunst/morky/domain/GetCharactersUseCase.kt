package com.eagskunst.morky.domain

import com.eagskunst.morky.common.CoroutineDispatchers
import com.eagskunst.morky.domain.entity.CharacterEntity
import com.eagskunst.morky.domain.repository.CharacterRepository
import com.eagskunst.morky.domain.usecase.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    dispatchers: CoroutineDispatchers,
    private val repository: CharacterRepository,
) :
    BaseUseCase(dispatchers) {

    suspend fun execute(page: Int = 1): Flow<List<CharacterEntity>> {
        return repository.getCharacters(page)
            .switchToIo()
    }
}
