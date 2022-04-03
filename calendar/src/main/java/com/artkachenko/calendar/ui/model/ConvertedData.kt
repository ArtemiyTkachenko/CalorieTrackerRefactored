package com.artkachenko.calendar.ui.model

data class ConvertedData(
    val fatAverage: Double,
    val proteinAverage: Double,
    val carbAverage: Double,
    val recipeIdList: MutableList<Long>,
    val calories: Int,
    val ingredientAmount: MutableMap<String, Double>
)