package com.artkachenko.core_impl.usecase

import com.artkachenko.core_api.network.models.ManualDishDetail
import com.artkachenko.core_api.network.repositories.DishesRepository
import com.artkachenko.core_api.usecases.GetDishesByDateUseCase
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import javax.inject.Inject

class GetDishesByDateUseCaseImpl @Inject constructor(
    private val dishesRepository: DishesRepository
): GetDishesByDateUseCase {

    override suspend fun execute(
        start: LocalDateTime,
        end: LocalDateTime
    ): Flow<List<ManualDishDetail>> = dishesRepository.getDishesByDate(start, end)
}