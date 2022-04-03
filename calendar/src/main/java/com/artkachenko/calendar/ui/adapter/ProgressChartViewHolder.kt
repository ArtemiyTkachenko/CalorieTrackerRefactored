package com.artkachenko.calendar.ui.adapter

import android.view.ViewGroup
import com.artkachenko.calendar.databinding.ICalorieProgressBinding
import com.artkachenko.calendar.ui.model.CalendarViewData
import com.artkachenko.core_api.base.BaseAdapter
import com.artkachenko.core_api.base.BaseViewHolder
import com.artkachenko.ui_utils.inflater

class ProgressChartViewHolder(private val binding: ICalorieProgressBinding) : BaseViewHolder<CalendarViewData.Progress>(binding.root) {

    override fun bind(model: CalendarViewData.Progress) {
        with (binding) {
            val (id, desiredAmount, spent) = model
            binding.progressChart.setData(spent, desiredAmount)
            binding.calorieBase.text = desiredAmount.toString()
            binding.caloriesSpent.text = "$spent"
            binding.caloriesLeft.text = "${desiredAmount - spent}"
        }
    }
}