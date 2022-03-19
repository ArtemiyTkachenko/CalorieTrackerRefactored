package com.artkachenko.core_api.usecases

import com.artkachenko.core_api.network.models.ManualDishDetail

interface InsertDishUseCase {

    suspend fun execute(dish: ManualDishDetail)
}