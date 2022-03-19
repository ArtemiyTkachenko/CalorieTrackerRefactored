package com.artkachenko.recipe_list.recipe_list

import androidx.lifecycle.ViewModel
import com.artkachenko.core_api.base.ViewModelScopeProvider
import com.artkachenko.core_api.network.models.RecipeEntity
import com.artkachenko.core_api.usecases.GetRecipeListUseCase
import com.artkachenko.core_impl.network.Filters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val getRecipeListUseCase: GetRecipeListUseCase,
    private val scopeProvider: ViewModelScopeProvider
) : ViewModel(),
    ViewModelScopeProvider by scopeProvider {

    private val _recipes = MutableStateFlow<State>(State.Initial)

    private var page = 1

    val recipes: StateFlow<State>
        get() = _recipes

    fun getRecipeList() {
        scope.launch {
            val italianRecipes = getRecipeListUseCase.execute(0, *Filters.italianPreset.map { it -> it.key to it.value.map { it.value } }.toTypedArray(), Filters.fiveItemPreset)
            _recipes.emit(State.Italian(italianRecipes))
            val vegetarianRecipes = getRecipeListUseCase.execute(0, *Filters.vegetarianPreset.map { it -> it.key to it.value.map { it.value } }.toTypedArray(), Filters.fiveItemPreset)
            _recipes.emit(State.Vegetarian(vegetarianRecipes))
            val indianRecipes = getRecipeListUseCase.execute(0, *Filters.indianPreset.map { it -> it.key to it.value.map { it.value } }.toTypedArray(), Filters.fiveItemPreset)
            _recipes.emit(State.Indian(indianRecipes))
            val quickRecipes = getRecipeListUseCase.execute(0, *Filters.quickPreset.map { it -> it.key to it.value.map { it.value } }.toTypedArray(), Filters.fiveItemPreset)
            _recipes.emit(State.Quick(quickRecipes))
            delay(100)
            _recipes.emit(State.LoadingFinished)
        }
    }

    sealed class State {
        object Initial : State()
        object Loading : State()
        object FirstItemEmitted : State()
        object LoadingFinished : State()
        class Italian(val data: List<RecipeEntity>) : State()
        class Vegetarian(val data: List<RecipeEntity>) : State()
        class Indian(val data: List<RecipeEntity>) : State()
        class Quick(val data: List<RecipeEntity>) : State()
    }
}