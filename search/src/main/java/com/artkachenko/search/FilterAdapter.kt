package com.artkachenko.search

import android.view.ViewGroup
import com.artkachenko.core_api.base.BaseAdapter
import com.artkachenko.core_api.base.BaseViewHolder
import com.artkachenko.core_api.network.models.FilterItemWrapper
import com.artkachenko.core_api.network.models.FilterWrapper
import com.artkachenko.core_api.network.models.FilterPair
import com.artkachenko.core_impl.network.Filters
import com.artkachenko.search.databinding.IFilterGroupBinding
import com.artkachenko.ui_utils.buildChip
import com.artkachenko.ui_utils.inflater
import java.util.*

class FilterAdapter(private val actions: RecipeFilterActions) : BaseAdapter<FilterWrapper>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<FilterWrapper> {
        val binding = IFilterGroupBinding.inflate(parent.inflater(), parent, false)
        return FilterViewHolder(binding, actions)
    }

    fun setFilters(argPresets: FilterWrapper?) {

        val cuisineFilters = FilterWrapper(Filters.cuisineFilters)
        val dietFilters = FilterWrapper(Filters.dietFilters)
        val intolerancesFilters = FilterWrapper(Filters.intolerancesFilters)

        argPresets?.filters?.forEach {
            when (val key = it.key) {
                cuisineFilters.extractFirstKey() -> updateFilter(
                    cuisineFilters.filters[key],
                    it.value
                )
                dietFilters.extractFirstKey() -> updateFilter(
                    dietFilters.filters[key],
                    it.value
                )
                intolerancesFilters.extractFirstKey() -> updateFilter(
                    intolerancesFilters.filters[key],
                    it.value
                )
            }
        }

        setInitial(
            listOf(
                cuisineFilters,
                dietFilters,
                intolerancesFilters
            )
        )
    }

    private fun updateFilter(
        originalFilters: MutableList<FilterItemWrapper>?,
        presetValue: MutableList<FilterItemWrapper>
    ) {
        presetValue.forEach { presetFilter ->
            originalFilters?.forEach { originalFilter ->
                val sameKey = presetFilter == originalFilter
                if (sameKey) {
                    originalFilter.isChecked = presetFilter.isChecked
                }
            }
        }
    }
}

class FilterViewHolder(
    private val binding: IFilterGroupBinding,
    private val actions: RecipeFilterActions
) : BaseViewHolder<FilterWrapper>(binding.root) {

    override fun bind(model: FilterWrapper) {
        with(binding) {
            model.filters.forEach { filter ->
                filterTitle.text = filter.key.capitalize(Locale.getDefault())
                filter.value.forEach { filterValue ->
                    buildChip(
                        itemView.context,
                        filters,
                        isChecked = filterValue.isChecked,
                        canClose = false,
                        filterValue = FilterPair(filter.key to filterValue),
                        checkCallback = { entry, isChecked ->
                            entry?.let {
                                actions.filterChecked(
                                    entry,
                                    isChecked
                                )
                            }
                        })
                }
            }
        }
    }
}