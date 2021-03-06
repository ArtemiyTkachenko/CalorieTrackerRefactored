package com.artkachenko.calendar

import androidx.lifecycle.ViewModel
import com.artkachenko.calendar.ui.model.CalendarViewData
import com.artkachenko.calendar.ui.model.ConvertedData
import com.artkachenko.core_api.base.ViewModelScopeProvider
import com.artkachenko.core_api.network.models.IngredientTitles
import com.artkachenko.core_api.network.models.ManualDishDetail
import com.artkachenko.core_api.usecases.GetDishesByDateUseCase
import com.artkachenko.core_api.usecases.GetRecipesByIdUseCase
import com.artkachenko.core_api.utils.PrefManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getRecipesByIdUseCase: GetRecipesByIdUseCase,
    private val getDishesByDateUseCase: GetDishesByDateUseCase,
    private val scopeProvider: ViewModelScopeProvider,
    private val prefManager: PrefManager
) : ViewModel(), ViewModelScopeProvider by scopeProvider {

    val selectableDate: StateFlow<LocalDate>
        get() = _selectableDate

    private val _selectableDate = MutableStateFlow<LocalDate>(LocalDate.now())

    val state: StateFlow<State>
        get() = _state

    private val _state = MutableStateFlow<State>(State.Initial)

    fun changeDate(date: LocalDate) {
        scope.launch {
            _selectableDate.emit(date)
            val start = date.atStartOfDay()
            val end = date.atStartOfDay().plusDays(1)
            _state.value = State.Clear
            getDishes(start, end)
        }
    }

    fun getDishes(
        start: LocalDateTime = _selectableDate.value.atStartOfDay(),
        end: LocalDateTime = _selectableDate.value.atStartOfDay().plusDays(1)
    ) {

        if (_state.value == State.FinishedLoading) return

        scope.launch {
            getDishesByDateUseCase.execute(start, end).collect { list ->
                val viewData = mutableListOf<CalendarViewData>()

                val convertedData = convertData(list)

                convertedData.let {
                    setCalorieData(it.calories, viewData)

                    setBarData(it.ingredientAmount, viewData)

                    setPieData(it.fatAverage, it.proteinAverage, it.carbAverage, viewData)

                    setUsedRecipesData(it.recipeIdList, viewData)
                }

                _state.value = State.Data(data = viewData)
            }
        }
    }

    private fun convertData(list: List<ManualDishDetail>): ConvertedData {
        val fatItems = mutableListOf<Double>()
        val proteinItems = mutableListOf<Double>()
        val carbItems = mutableListOf<Double>()
        val ingredientsAmount = mutableMapOf<String, Double>()
        var calories = 0
        var totalWeight = 0.0
        val recipeIdsList = mutableListOf<Long>()

        list.forEach { dishDetail ->
            recipeIdsList.add(dishDetail.recipeId)
            dishDetail.extendedIngredients?.forEach { ingredient ->
                totalWeight += ingredient.amount ?: 0.0

                val title = ingredient.name ?: ""

                val previousValue = ingredientsAmount[title] ?: 0.0

                val converted = ingredient.convertedAmount

                ingredientsAmount[title] =
                    previousValue.plus(ingredient.convertedAmount?.targetAmount ?: 0.0)
            }

            val breakdown = dishDetail.nutrition?.caloricBreakdown

            breakdown?.percentFat?.let { fatItems.add(it) }
            breakdown?.percentProtein?.let { proteinItems.add(it) }
            breakdown?.percentCarbs?.let { carbItems.add(it) }
            dishDetail.nutrition?.nutrients?.firstOrNull { it.title == IngredientTitles.CALORIES.title }
                .let { calories += it?.amount?.toInt() ?: 0 }
        }
        val fatAverage = fatItems.average()
        val proteinAverage = proteinItems.average()
        val carbAverage = carbItems.average()

        return ConvertedData(
            fatAverage = fatAverage,
            proteinAverage = proteinAverage,
            carbAverage = carbAverage,
            recipeIdList = recipeIdsList,
            calories = calories,
            ingredientAmount = ingredientsAmount
        )
    }

    private suspend fun setUsedRecipesData(
        recipeIdsList: MutableList<Long>,
        viewData: MutableList<CalendarViewData>
    ) {
        if (!recipeIdsList.isNullOrEmpty()) {
            val entities = getRecipesByIdUseCase.execute(recipeIdsList)
            viewData.add(CalendarViewData.UsedRecipes(recipes = entities))
        }
    }

    private fun setCalorieData(calories: Int, viewData: MutableList<CalendarViewData>) {
        viewData.add(
            CalendarViewData.Progress(
                spent = calories.toLong(),
                desiredAmount = prefManager.desiredCalories.toLong()
            )
        )
    }

    private fun setBarData(
        sources: MutableMap<String, Double>,
        viewData: MutableList<CalendarViewData>
    ) {
        if (!sources.isNullOrEmpty()) {
            viewData.add(CalendarViewData.Sources(sources = sources))
        }
    }

    private fun setPieData(
        fatAverage: Double,
        proteinAverage: Double,
        carbAverage: Double,
        viewData: MutableList<CalendarViewData>
    ) {
        if (!fatAverage.isNaN() || !proteinAverage.isNaN() || !carbAverage.isNaN()) {
            viewData.add(
                CalendarViewData.Pie(
                    fat = fatAverage.toLong(),
                    protein = proteinAverage.toLong(),
                    carbs = carbAverage.toLong()
                )
            )
        }
    }

    sealed class State {
        object Initial : State()
        object FinishedLoading : State()
        data class Data(val data: List<CalendarViewData>?) : State()
        object Visible : State()
        object Clear : State()
    }
}