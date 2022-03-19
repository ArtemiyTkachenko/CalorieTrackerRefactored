package com.artkachenko.core_impl.usecase

import com.artkachenko.core_api.network.models.Ingredient
import com.artkachenko.core_api.network.repositories.RecipeRepository
import com.artkachenko.core_api.usecases.ParseIngredientsUseCase
import javax.inject.Inject

class ParseIngredientsUseCaseImpl @Inject constructor(
    private val recipeRepository: RecipeRepository
): ParseIngredientsUseCase {

    override suspend fun execute(ingredients: List<String>): List<Ingredient> =
        recipeRepository.parseIngredients(ingredients)
}