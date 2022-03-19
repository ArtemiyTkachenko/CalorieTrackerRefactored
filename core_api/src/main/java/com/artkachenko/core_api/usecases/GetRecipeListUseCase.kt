package com.artkachenko.core_api.usecases

import com.artkachenko.core_api.network.models.RecipeEntity

interface GetRecipeListUseCase {

    suspend fun execute(offset: Int, vararg filters: Pair<String, List<String>>): List<RecipeEntity>
}