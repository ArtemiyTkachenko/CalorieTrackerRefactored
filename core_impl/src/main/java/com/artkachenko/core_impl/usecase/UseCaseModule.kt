package com.artkachenko.core_impl.usecase

import com.artkachenko.core_api.usecases.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class UseCaseModule {

    @Binds
    abstract fun bindConvertIngredientsUseCase(
        convertIngredientsUseCaseImpl: ConvertIngredientsUseCaseImpl
    ): ConvertIngredientsUseCase

    @Binds
    abstract fun bindGetRecipeDetailUseCase(
        getRecipeDetailUseCaseImpl: GetRecipeDetailUseCaseImpl
    ): GetRecipeDetailUseCase

    @Binds
    abstract fun bindGetRecipeListUseCase(
        getRecipeListUseCaseImpl: GetRecipeListUseCaseImpl
    ): GetRecipeListUseCase

    @Binds
    abstract fun bindGetRecipesByIdUseCase(
        getRecipesByIdUseCaseImpl: GetRecipesByIdUseCaseImpl
    ): GetRecipesByIdUseCase

    @Binds
    abstract fun bindParseIngredientsUseCase(
        parseIngredientsUseCaseImpl: ParseIngredientsUseCaseImpl
    ): ParseIngredientsUseCase

    @Binds
    abstract fun bindGetDishesByDateUseCase(
        getDishesByDateUseCaseImpl: GetDishesByDateUseCaseImpl
    ): GetDishesByDateUseCase

    @Binds
    abstract fun bindInsertDishUseCase(
        insertDishUseCaseImpl: InsertDishUseCaseImpl
    ): InsertDishUseCase
}