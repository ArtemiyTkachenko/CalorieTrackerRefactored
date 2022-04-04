package com.artkachenko.recipe_list.recipe_list

import com.artkachenko.core_api.base.BaseViewHolder
import com.artkachenko.core_api.utils.debugLog
import com.artkachenko.recipe_list.databinding.IShowMoreBinding
import com.artkachenko.recipe_list.recipe_list.model.RecipeListItemViewData
import com.artkachenko.ui_utils.setSingleClickListener

class ShowMoreViewHolder(
    private val binding: IShowMoreBinding,
    private val actions: RecipeListActions
) : BaseViewHolder<RecipeListItemViewData.MoreItemViewData>(binding.root) {

    override fun bind(model: RecipeListItemViewData.MoreItemViewData) {
        debugLog("ShowMoreViewHolder model is $model")
        binding.root.setSingleClickListener {
            val checkedFilters = model.copy()
            checkedFilters.filters.filters.forEach { it ->
                it.value.forEach {
                    it.isChecked = true
                }
            }
            actions.navigateToSearch(checkedFilters.filters)
        }
    }
}