package com.artkachenko.core_impl.usecase

import com.artkachenko.core_api.network.models.RecipeEntity
import com.artkachenko.core_api.network.repositories.RecipeRepository
import com.artkachenko.core_api.usecases.GetRecipeListUseCase
import javax.inject.Inject

class GetRecipeListUseCaseImpl @Inject constructor(
    private val recipeRepository: RecipeRepository
) : GetRecipeListUseCase {

    override suspend fun execute(
        offset: Int,
        vararg filters: Pair<String, List<String>>
    ): List<RecipeEntity> = recipeRepository.getRecipeList(offset, *filters)
}