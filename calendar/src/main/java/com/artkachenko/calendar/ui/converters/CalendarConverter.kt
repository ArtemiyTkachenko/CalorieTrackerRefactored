package com.artkachenko.calendar.ui.converters

import com.artkachenko.calendar.ui.model.CalendarViewData
import com.artkachenko.core_api.network.models.ManualDishDetail
import javax.inject.Inject

interface CalendarConverter {

    fun convert(data: List<ManualDishDetail>): List<CalendarViewData>
}

class CalendarConverterImpl @Inject constructor(): CalendarConverter {

    override fun convert(data: List<ManualDishDetail>): List<CalendarViewData> {
        TODO("Not yet implemented")
    }
}