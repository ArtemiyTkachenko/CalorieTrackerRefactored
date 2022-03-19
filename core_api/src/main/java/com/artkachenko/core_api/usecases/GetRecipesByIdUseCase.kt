package com.artkachenko.core_api.usecases

import com.artkachenko.core_api.network.models.RecipeEntity

interface GetRecipesByIdUseCase {

    suspend fun execute(ids: List<Long>) : List<RecipeEntity>
}