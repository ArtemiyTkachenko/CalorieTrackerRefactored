package com.artkachenko.core_impl.usecase

import com.artkachenko.core_api.network.models.ConvertedAmount
import com.artkachenko.core_api.network.repositories.RecipeRepository
import com.artkachenko.core_api.usecases.ConvertIngredientsUseCase
import javax.inject.Inject

class ConvertIngredientsUseCaseImpl @Inject constructor(
    private val recipeRepository: RecipeRepository
): ConvertIngredientsUseCase {

    override suspend fun execute(vararg filters: Pair<String, List<String>>): ConvertedAmount =
        recipeRepository.convertIngredients(*filters)
}