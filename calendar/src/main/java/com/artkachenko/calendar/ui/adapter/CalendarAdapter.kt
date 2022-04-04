package com.artkachenko.calendar.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.artkachenko.calendar.databinding.IBarItemBinding
import com.artkachenko.calendar.databinding.ICalorieProgressBinding
import com.artkachenko.calendar.databinding.IPieChartBinding
import com.artkachenko.calendar.databinding.IUsedReceiptsHeaderBinding
import com.artkachenko.calendar.ui.CalendarActions
import com.artkachenko.calendar.ui.model.CalendarViewData
import com.artkachenko.core_api.base.BaseViewHolder
import com.artkachenko.ui_utils.inflater

private const val PIE_VIEW_TYPE = 1
private const val PROGRESS_VIEW_TYPE = 2
private const val SOURCES_VIEW_TYPE = 3
private const val USED_RECIPES_VIEW_TYPE = 4

class CalendarAdapter(private val actions: CalendarActions) : ListAdapter<CalendarViewData, BaseViewHolder<out CalendarViewData>>(
    DiffUtilCallback
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<out CalendarViewData> {
        return when (viewType) {
            PIE_VIEW_TYPE -> {
                val binding = IPieChartBinding.inflate(parent.inflater(), parent, false)
                PieChartHolder(binding)
            }
            PROGRESS_VIEW_TYPE -> {
                val binding = ICalorieProgressBinding.inflate(parent.inflater(), parent, false)
                ProgressChartViewHolder(binding)
            }
            SOURCES_VIEW_TYPE -> {
                val binding = IBarItemBinding.inflate(parent.inflater(), parent, false)
                SourcesViewHolder(binding)
            }
            USED_RECIPES_VIEW_TYPE -> {
                val binding = IUsedReceiptsHeaderBinding.inflate(parent.inflater(), parent, false)
                UsedRecipeHeaderViewHolder(binding, actions)
            }
            else -> error("Unsupported item type")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<out CalendarViewData>, position: Int) {
        getItem(position)?.let { holder.bindHolder(it) }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is CalendarViewData.Pie -> PIE_VIEW_TYPE
            is CalendarViewData.Progress -> PROGRESS_VIEW_TYPE
            is CalendarViewData.Sources -> SOURCES_VIEW_TYPE
            is CalendarViewData.UsedRecipes -> USED_RECIPES_VIEW_TYPE
        }
    }

    private companion object DiffUtilCallback : DiffUtil.ItemCallback<CalendarViewData>() {

        override fun areItemsTheSame(
            oldItem: CalendarViewData,
            newItem: CalendarViewData
        ): Boolean {
            return when {
                oldItem is CalendarViewData.Pie && newItem is CalendarViewData.Pie ->
                    oldItem.id == newItem.id
                oldItem is CalendarViewData.Progress && newItem is CalendarViewData.Progress ->
                    oldItem.id == newItem.id
                oldItem is CalendarViewData.Sources && newItem is CalendarViewData.Sources ->
                    oldItem.id == newItem.id
                else -> false
            }
        }

        override fun areContentsTheSame(
            oldItem: CalendarViewData,
            newItem: CalendarViewData
        ): Boolean {
            return when {
                oldItem is CalendarViewData.Pie && newItem is CalendarViewData.Pie ->
                    oldItem.fat == newItem.fat &&
                        oldItem.carbs == newItem.carbs &&
                        oldItem.protein == newItem.protein
                oldItem is CalendarViewData.Progress && newItem is CalendarViewData.Progress ->
                    oldItem.desiredAmount == newItem.desiredAmount && oldItem.spent == newItem.spent
                oldItem is CalendarViewData.Sources && newItem is CalendarViewData.Sources ->
                    oldItem.sources == newItem.sources
                else -> false
            }
        }
    }

    fun clear() {
    }
}