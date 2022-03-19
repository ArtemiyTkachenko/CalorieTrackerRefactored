package com.artkachenko.core_impl.usecase

import com.artkachenko.core_api.network.models.ManualDishDetail
import com.artkachenko.core_api.network.repositories.DishesRepository
import com.artkachenko.core_api.usecases.InsertDishUseCase
import javax.inject.Inject

class InsertDishUseCaseImpl @Inject constructor(
    private val dishesRepository: DishesRepository
):  InsertDishUseCase {

    override suspend fun execute(dish: ManualDishDetail) =
        dishesRepository.insertDish(dish)
}