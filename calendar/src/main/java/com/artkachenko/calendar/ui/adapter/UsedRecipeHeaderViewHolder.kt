package com.artkachenko.calendar.ui.adapter

import android.view.ViewGroup
import com.artkachenko.calendar.ui.CalendarActions
import com.artkachenko.calendar.databinding.ISearchRecipeBinding
import com.artkachenko.calendar.databinding.IUsedReceiptsHeaderBinding
import com.artkachenko.calendar.ui.model.CalendarViewData
import com.artkachenko.core_api.base.BaseAdapter
import com.artkachenko.core_api.base.BaseViewHolder
import com.artkachenko.core_api.network.models.RecipeEntity
import com.artkachenko.ui_utils.*
import java.util.*

class UsedRecipeHeaderViewHolder(
    private val binding: IUsedReceiptsHeaderBinding,
    private val actions: CalendarActions
) : BaseViewHolder<CalendarViewData.UsedRecipes>(binding.root) {
    override fun bind(model: CalendarViewData.UsedRecipes) {
        with(binding) {
            val recipesAdapter = UsedRecipesItemAdapter(actions)
            usedRecipes.adapter = recipesAdapter
            recipesAdapter.setInitial(model.recipes)
        }
    }
}


class UsedRecipesItemAdapter(private val actions: CalendarActions) :
    BaseAdapter<RecipeEntity>(actions) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<RecipeEntity> {
        val binding = ISearchRecipeBinding.inflate(parent.inflater(), parent, false)
        return RecipeListViewHolder(binding, actions)
    }
}

class RecipeListViewHolder(
    private val binding: ISearchRecipeBinding,
    private val actions: CalendarActions
) : BaseViewHolder<RecipeEntity>(binding.root, actions) {
    override fun bind(model: RecipeEntity) {
        with(binding) {
            val context = itemView.context
            val url = ImageUtils.buildRecipeImageUrl(model.id)
            recipeImage.loadCircleImage(url)
            recipeTitle.text = model.title

            recipeHealthScore.text =
                buildSpan(model.healthScore?.toInt(), context, R.string.health_score)

            recipeScore.text = buildSpan(model.spoonacularScore?.toInt(), context, R.string.score)

            recipeCookingTime.text =
                String.format(context.getString(R.string.cooking_time), "${model.readyInMinutes}")

            clickContainer.setSingleClickListener {
                recipeImage.transitionName = UUID.randomUUID().toString()
                actions.onItemClicked(model, recipeImage)
            }
        }
    }
}