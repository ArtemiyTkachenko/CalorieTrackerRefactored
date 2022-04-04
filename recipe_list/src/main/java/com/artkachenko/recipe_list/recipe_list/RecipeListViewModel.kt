package com.artkachenko.recipe_list.recipe_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artkachenko.core_api.base.ViewModelScopeProvider
import com.artkachenko.core_api.network.models.RecipeEntity
import com.artkachenko.core_api.usecases.GetRecipeListUseCase
import com.artkachenko.core_impl.network.Filters
import com.artkachenko.recipe_list.recipe_list.model.RecipeListItemViewData
import com.artkachenko.recipe_list.recipe_list.model.RecipeListViewData
import com.artkachenko.recipe_list.recipe_list.model.RecipeListWrapperData
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

    init {
        getRecipeList()
    }

    private fun getRecipeList() {
        scope.launch {
            val italianPreset = getPreset {
                getRecipeListUseCase.execute(
                    0,
                    *Filters.italianPreset.map { it -> it.key to it.value.map { it.value } }
                        .toTypedArray(),
                    Filters.fiveItemPreset
                )
            }
            val vegetarianPreset = getPreset {
                getRecipeListUseCase.execute(
                    0,
                    *Filters.vegetarianPreset.map { it -> it.key to it.value.map { it.value } }
                        .toTypedArray(),
                    Filters.fiveItemPreset
                )
            }
            val indianPreset = getPreset {
                getRecipeListUseCase.execute(
                    0,
                    *Filters.indianPreset.map { it -> it.key to it.value.map { it.value } }
                        .toTypedArray(),
                    Filters.fiveItemPreset
                )
            }
            val quickPreset = getPreset {
                getRecipeListUseCase.execute(
                    0,
                    *Filters.quickPreset.map { it -> it.key to it.value.map { it.value } }
                        .toTypedArray(),
                    Filters.fiveItemPreset
                )
            }

            val wrapper = mutableListOf<RecipeListWrapperData>()

            val italian = RecipeListWrapperData(data = italianPreset.mapIndexed { index, recipeEntity ->
                RecipeListItemViewData.RecipeItemViewData(
                    id = index,
                    item = recipeEntity
                )
            })

            val vegetarian = RecipeListWrapperData(data = vegetarianPreset.mapIndexed { index, recipeEntity ->
                RecipeListItemViewData.RecipeItemViewData(
                    id = index,
                    item = recipeEntity
                )
            })

            val indian = RecipeListWrapperData(data = indianPreset.mapIndexed { index, recipeEntity ->
                RecipeListItemViewData.RecipeItemViewData(
                    id = index,
                    item = recipeEntity
                )
            })

            val quick = RecipeListWrapperData(data = quickPreset.mapIndexed { index, recipeEntity ->
                RecipeListItemViewData.RecipeItemViewData(
                    id = index,
                    item = recipeEntity
                )
            })

            wrapper.add(italian)
            wrapper.add(vegetarian)
            wrapper.add(indian)
            wrapper.add(quick)

            val data = RecipeListViewData(
                wrapper = wrapper
            )
            _recipes.emit(State.Data(data = data))
        }
    }

    private suspend fun getPreset(action: suspend () -> List<RecipeEntity>): List<RecipeEntity> {
        val result = viewModelScope.async {
            action.invoke()
        }
        return result.await()
    }

    sealed class State {
        object Initial : State()
        object Loading : State()
        object FirstItemEmitted : State()
        data class Data(val data: RecipeListViewData) : State()
    }
}