package com.artkachenko.recipe_list.recipe_list.adapter

import android.view.ViewGroup
import com.artkachenko.core_api.base.BaseAdapter
import com.artkachenko.core_api.base.BaseViewHolder
import com.artkachenko.recipe_list.databinding.IRecipeListItemBinding
import com.artkachenko.recipe_list.recipe_list.RecipeListActions
import com.artkachenko.recipe_list.recipe_list.model.RecipeListViewData
import com.artkachenko.recipe_list.recipe_list.model.RecipeListWrapperData
import com.artkachenko.ui_utils.inflater

class RecipeListAdapter(private val actions: RecipeListActions): BaseAdapter<RecipeListWrapperData>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<RecipeListWrapperData> {
        val binding = IRecipeListItemBinding.inflate(parent.inflater(), parent, false)
        return RecipeListViewHolder(binding, actions)
    }
}