package com.artkachenko.core_api.usecases

import com.artkachenko.core_api.network.models.ManualDishDetail
import kotlinx.coroutines.flow.Flow

interface GetDishesUseCase {

    suspend fun execute(): Flow<List<ManualDishDetail>>
}