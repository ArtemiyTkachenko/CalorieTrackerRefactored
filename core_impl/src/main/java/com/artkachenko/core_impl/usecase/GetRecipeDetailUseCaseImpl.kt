package com.artkachenko.core_impl.usecase

import com.artkachenko.core_api.network.models.RecipeDetailModel
import com.artkachenko.core_api.network.repositories.RecipeRepository
import com.artkachenko.core_api.usecases.GetRecipeDetailUseCase
import javax.inject.Inject

class GetRecipeDetailUseCaseImpl @Inject constructor(
    private val recipeRepository: RecipeRepository
) : GetRecipeDetailUseCase {

    override suspend fun execute(id: Long): RecipeDetailModel =
        recipeRepository.getRecipeDetail(id)
}