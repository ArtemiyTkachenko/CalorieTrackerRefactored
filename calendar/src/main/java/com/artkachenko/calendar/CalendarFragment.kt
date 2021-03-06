package com.artkachenko.calendar

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.artkachenko.calendar.ui.CalendarActions
import com.artkachenko.calendar.ui.adapter.*
import com.artkachenko.calendar.databinding.FragmentCalendarBinding
import com.artkachenko.core_api.base.BaseFragment
import com.artkachenko.core_api.network.models.RecipeEntity
import com.artkachenko.core_api.utils.PrefManager
import com.artkachenko.core_api.utils.debugLog
import com.artkachenko.ui_utils.ID
import com.artkachenko.ui_utils.TRANSITION_NAME
import com.artkachenko.ui_utils.decorations.MarginItemDecoration
import com.artkachenko.ui_utils.themes.ThemeManager
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.utils.Size
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import viewBinding
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@AndroidEntryPoint
class CalendarFragment : BaseFragment(R.layout.fragment_calendar), CalendarActions {

    @Inject
    lateinit var themeManager: ThemeManager

    private val viewModel by viewModels<CalendarViewModel>()

    private var binding by viewBinding<FragmentCalendarBinding>()

    private val adapter: CalendarAdapter by lazy {
        CalendarAdapter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCalendarBinding.bind(view)

        with(binding) {
            info.adapter = adapter
            val decoration = MarginItemDecoration(requireContext(), marginLeft = 16, marginRight = 16)

            info.addItemDecoration(decoration)
        }

        viewModel.getDishes()

        lifecycleScope.launchWhenResumed {
            viewModel.state.collectLatest {
                processState(it)
            }
        }

        setupCalendar()
    }

    override fun onItemClicked(model: RecipeEntity, view: View) {
        val bundle = Bundle().apply {
            putLong(ID, model.id)
            putString(TRANSITION_NAME, view.transitionName)
        }

        val extras = FragmentNavigatorExtras(view to "recipeImage")

        findNavController().navigate(R.id.calendar_to_detail, bundle, null, extras)
    }

    override fun changeDate(day: CalendarDay) {
        val date = day.date

        binding.calendar.smoothScrollToDate(day.date.minusDays(3))

        val selectedDate = viewModel.selectableDate.value

        if (selectedDate != date) {
            selectedDate.let { binding.calendar.notifyDateChanged(it) }
            viewModel.changeDate(date)
            binding.calendar.notifyDateChanged(date)
        }
    }

    override fun getDate(): Flow<LocalDate> {
        return viewModel.selectableDate
    }

    override fun onDestroy() {
        viewModelStore.clear()
        super.onDestroy()
    }

    private fun setupCalendar() {
        val dm = DisplayMetrics()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            val display = activity?.display
            display?.getRealMetrics(dm)
        } else {
            val display = activity?.windowManager?.defaultDisplay
            display?.getMetrics(dm)
        }

        binding.calendar.apply {
            val dayWidth = dm.widthPixels / 7
            val dayHeight = (dayWidth * 1.5).toInt()
            daySize = Size(dayWidth, dayHeight)
        }

        binding.calendar.dayBinder = DayViewBinder(this, themeManager)

        val currentMonth = YearMonth.now()
        binding.calendar.setup(
            currentMonth.minusMonths(3),
            currentMonth.plusMonths(3),
            DayOfWeek.MONDAY
        )
        binding.calendar.scrollToDate(viewModel.selectableDate.value.minusDays(3))
    }

    private fun processState(state: CalendarViewModel.State) {
        when (state) {
            is CalendarViewModel.State.Data -> {
                adapter.submitList(state.data)
            }
            CalendarViewModel.State.Clear -> clearAdapters()
            CalendarViewModel.State.Visible -> binding.info.isVisible = true
            CalendarViewModel.State.Initial -> { }
            CalendarViewModel.State.FinishedLoading -> {}
        }
    }

    private fun clearAdapters() {
        binding.info.isVisible = false
        adapter.clear()
    }
}