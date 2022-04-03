package com.artkachenko.calendar.ui.model

import com.artkachenko.core_api.network.models.RecipeEntity

sealed class CalendarViewData {

    data class Progress(
        val id: Int = 0,
        val desiredAmount: Long,
        val spent: Long
    ): CalendarViewData()

    data class Pie(
        val id: Int = 1,
        val fat: Long,
        val protein: Long,
        val carbs: Long
    ): CalendarViewData()

    data class Sources(
        val id: Int = 2,
        val sources: Map<String, Double>
    ): CalendarViewData()

    data class UsedRecipes(
        val id: Int = 3,
        val recipes: List<RecipeEntity>
    ): CalendarViewData()
}