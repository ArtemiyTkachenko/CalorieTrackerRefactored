package com.artkachenko.calendar.ui.adapter

import android.annotation.SuppressLint
import com.artkachenko.calendar.R
import com.artkachenko.calendar.databinding.IPieChartBinding
import com.artkachenko.calendar.ui.model.CalendarViewData
import com.artkachenko.core_api.base.BaseViewHolder

class PieChartHolder(private val binding: IPieChartBinding) :
    BaseViewHolder<CalendarViewData.Pie>(binding.root) {
    @SuppressLint("SetTextI18n")
    override fun bind(model: CalendarViewData.Pie) {
        val (id, fat, protein, carb) = model
        val context = binding.root.context
        with (binding) {
            pieChart.setChartData(fatRate = fat, proteinRate = protein, carbRate = carb)
            fatLegend.text = "${context.getText(R.string.fat_percentage)} $fat%"
            proteinLegend.text = "${context.getText(R.string.protein_percentage)} $protein%"
            carbsLegend.text = "${context.getText(R.string.carb_percentage)} $carb%"
        }
    }
}