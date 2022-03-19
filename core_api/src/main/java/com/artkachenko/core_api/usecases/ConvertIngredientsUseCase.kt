package com.artkachenko.core_api.usecases

import com.artkachenko.core_api.network.models.ConvertedAmount

interface ConvertIngredientsUseCase {

    suspend fun execute(vararg filters: Pair<String, List<String>>) : ConvertedAmount
}