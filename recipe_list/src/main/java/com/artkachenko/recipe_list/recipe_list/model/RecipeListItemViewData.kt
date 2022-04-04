package com.artkachenko.recipe_list.recipe_list.model

import android.os.Parcelable
import com.artkachenko.core_api.network.models.FilterWrapper
import com.artkachenko.core_api.network.models.RecipeEntity
import kotlinx.parcelize.Parcelize

sealed class RecipeListItemViewData() {
    @Parcelize
    data class RecipeItemViewData(
        val id: Int,
        val item: RecipeEntity
    ): RecipeListItemViewData(), Parcelable

    @Parcelize
    data class MoreItemViewData(
        val id: Int,
        val filters: FilterWrapper
    ): RecipeListItemViewData(), Parcelable
}

data class RecipeListViewData(
    val wrapper: List<RecipeListWrapperData>
)

data class RecipeListWrapperData(
    val data: List<RecipeListItemViewData>
)