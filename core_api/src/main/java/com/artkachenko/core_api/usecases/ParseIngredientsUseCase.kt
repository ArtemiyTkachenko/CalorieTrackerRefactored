package com.artkachenko.core_api.usecases

import com.artkachenko.core_api.network.models.Ingredient

interface ParseIngredientsUseCase {

    suspend fun execute(ingredients: List<String>): List<Ingredient>
}