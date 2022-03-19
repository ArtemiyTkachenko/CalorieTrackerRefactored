package com.artkachenko.core_impl.usecase

import com.artkachenko.core_api.network.models.RecipeEntity
import com.artkachenko.core_api.network.repositories.RecipeRepository
import com.artkachenko.core_api.usecases.GetRecipesByIdUseCase
import javax.inject.Inject

class GetRecipesByIdUseCaseImpl @Inject constructor(
    private val recipeRepository: RecipeRepository
): GetRecipesByIdUseCase {

    override suspend fun execute(ids: List<Long>): List<RecipeEntity> =
        recipeRepository.getRecipesById(ids)
}