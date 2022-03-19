package com.artkachenko.core_api.usecases

import com.artkachenko.core_api.network.models.ManualDishDetail
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface GetDishesByDateUseCase {

    suspend fun execute(start: LocalDateTime, end: LocalDateTime) : Flow<List<ManualDishDetail>>
}