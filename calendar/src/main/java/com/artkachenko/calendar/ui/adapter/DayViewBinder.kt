package com.artkachenko.calendar.ui.adapter

import android.view.View
import com.artkachenko.calendar.ui.CalendarActions
import com.artkachenko.ui_utils.themes.ThemeManager
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.DayBinder
import kotlinx.coroutines.InternalCoroutinesApi

class DayViewBinder(
    private val bindings: CalendarActions,
    private val themeManager: ThemeManager
) :
    DayBinder<DayViewContainer> {
    override fun create(view: View) = DayViewContainer(view, bindings, themeManager)

    @InternalCoroutinesApi
    override fun bind(container: DayViewContainer, day: CalendarDay) = container.bind(day)
}