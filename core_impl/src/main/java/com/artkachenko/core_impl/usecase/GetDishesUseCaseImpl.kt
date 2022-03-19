package com.artkachenko.core_impl.usecase

import com.artkachenko.core_api.network.models.ManualDishDetail
import com.artkachenko.core_api.network.repositories.DishesRepository
import com.artkachenko.core_api.usecases.GetDishesUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDishesUseCaseImpl @Inject constructor(
    private val dishesRepository: DishesRepository
): GetDishesUseCase {

    override suspend fun execute(): Flow<List<ManualDishDetail>> =
        dishesRepository.getDishes()
}