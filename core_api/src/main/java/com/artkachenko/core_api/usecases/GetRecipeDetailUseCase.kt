package com.artkachenko.core_api.usecases

import com.artkachenko.core_api.network.models.RecipeDetailModel

interface GetRecipeDetailUseCase {

    suspend fun execute(id: Long) : RecipeDetailModel
}