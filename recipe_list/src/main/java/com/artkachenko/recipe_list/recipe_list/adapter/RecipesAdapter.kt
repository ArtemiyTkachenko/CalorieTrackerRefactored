package com.artkachenko.recipe_list.recipe_list.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.artkachenko.core_api.base.BaseViewHolder
import com.artkachenko.core_api.network.models.RecipeEntity
import com.artkachenko.core_api.utils.debugLog
import com.artkachenko.recipe_list.databinding.IRecipeListBinding
import com.artkachenko.recipe_list.databinding.IRecipeListItemBinding
import com.artkachenko.recipe_list.databinding.IShowMoreBinding
import com.artkachenko.recipe_list.recipe_list.RecipeListActions
import com.artkachenko.recipe_list.recipe_list.ShowMoreViewHolder
import com.artkachenko.recipe_list.recipe_list.model.RecipeListItemViewData
import com.artkachenko.recipe_list.recipe_list.model.RecipeListViewData
import com.artkachenko.recipe_list.recipe_list.model.RecipeListWrapperData
import com.artkachenko.ui_utils.ImageUtils
import com.artkachenko.ui_utils.inflater
import com.artkachenko.ui_utils.loadImage
import com.artkachenko.ui_utils.setSingleClickListener
import java.util.*

private const val RECIPE_ITEM_VIEW_TYPE = 0
private const val MORE_ITEM_VIEW_TYPE = 1

class RecipeListViewHolder(
    private val binding: IRecipeListItemBinding,
    private val actions: RecipeListActions
) : BaseViewHolder<RecipeListWrapperData>(binding.root) {

    private val adapter = RecipesAdapter(actions)

    override fun bind(model: RecipeListWrapperData) {
        binding.rvRecipes.adapter = adapter
        debugLog("model is ${model.data}")
        adapter.submitList(model.data)
    }
}

class RecipesAdapter(private val actions: RecipeListActions) :
    ListAdapter<RecipeListItemViewData, BaseViewHolder<out RecipeListItemViewData>>(
        DIffUtilItemCallBack
    ) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is RecipeListItemViewData.MoreItemViewData -> MORE_ITEM_VIEW_TYPE
            is RecipeListItemViewData.RecipeItemViewData -> RECIPE_ITEM_VIEW_TYPE
            else -> error("Unsupported Item type ${getItem(position).javaClass.simpleName}")
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<out RecipeListItemViewData> {
        return when (viewType) {
            RECIPE_ITEM_VIEW_TYPE -> {
                val binding = IRecipeListBinding.inflate(parent.inflater(), parent, false)
                RecipeListItemViewHolder(binding, actions)
            }
            MORE_ITEM_VIEW_TYPE -> {
                val binding = IShowMoreBinding.inflate(parent.inflater(), parent, false)
                ShowMoreViewHolder(binding, actions)
            }
            else -> error("Unsupported Item type")
        }
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<out RecipeListItemViewData>,
        position: Int
    ) {
        getItem(position)?.let { holder.bindHolder(it) }
    }

    private companion object DIffUtilItemCallBack :
        DiffUtil.ItemCallback<RecipeListItemViewData>() {

        override fun areItemsTheSame(
            oldItem: RecipeListItemViewData,
            newItem: RecipeListItemViewData
        ): Boolean {
            return when {
                oldItem is RecipeListItemViewData.MoreItemViewData && newItem is RecipeListItemViewData.MoreItemViewData ->
                    oldItem.id == newItem.id
                oldItem is RecipeListItemViewData.RecipeItemViewData && newItem is RecipeListItemViewData.RecipeItemViewData ->
                    oldItem.id == newItem.id
                else -> false
            }
        }

        override fun areContentsTheSame(
            oldItem: RecipeListItemViewData,
            newItem: RecipeListItemViewData
        ): Boolean {
            return when {
                oldItem is RecipeListItemViewData.MoreItemViewData && newItem is RecipeListItemViewData.MoreItemViewData ->
                    oldItem == newItem
                oldItem is RecipeListItemViewData.RecipeItemViewData && newItem is RecipeListItemViewData.RecipeItemViewData ->
                    oldItem == newItem
                else -> false
            }
        }
    }
}

class RecipeListItemViewHolder(
    private val binding: IRecipeListBinding,
    private val actions: RecipeListActions
) : BaseViewHolder<RecipeListItemViewData.RecipeItemViewData>(binding.root) {
    override fun bind(model: RecipeListItemViewData.RecipeItemViewData) {
        with(binding) {
            debugLog("RecipeListItemViewHolder model is $model")
            val url = ImageUtils.buildRecipeImageUrl(model.item.id)
            recipeImage.loadImage(url)
            recipeTitle.text = model.item.title
            clickContainer.setSingleClickListener {
                recipeImage.transitionName = UUID.randomUUID().toString()
                actions.onItemClicked(model.item, recipeImage)
            }
        }
    }
}