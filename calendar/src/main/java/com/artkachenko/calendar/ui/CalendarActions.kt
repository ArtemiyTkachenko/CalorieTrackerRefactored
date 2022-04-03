package com.artkachenko.calendar.ui

import com.artkachenko.core_api.base.ViewHolderActions
import com.artkachenko.core_api.network.models.RecipeEntity
import com.kizitonwose.calendarview.model.CalendarDay
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface CalendarActions: ViewHolderActions<RecipeEntity> {
    fun changeDate(day: CalendarDay)

    fun getDate() : Flow<LocalDate>
}