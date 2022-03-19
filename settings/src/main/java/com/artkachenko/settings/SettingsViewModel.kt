package com.artkachenko.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artkachenko.core_api.utils.PrefManager
import com.artkachenko.core_api.utils.debugLog
import com.artkachenko.ui_utils.themes.Theme
import com.artkachenko.ui_utils.themes.ThemeManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val themeManager: ThemeManager,
    private val prefManager: PrefManager
) : ViewModel() {

    private val _state: MutableStateFlow<SettingsViewData> = MutableStateFlow(
        SettingsViewData(
            isDarkTheme = prefManager.isDarkTheme,
            desiredCalories = prefManager.desiredCalories.toString(),
            theme = themeManager.theme,
            hasThemeChanged = false
        )
    )

    val state: StateFlow<SettingsViewData> get() = _state

    fun setDesiredCalories(calories: String?) {
        val parsedNumber = calories?.toIntOrNull()
        parsedNumber?.let { prefManager.desiredCalories = it }
    }

    fun setNewTheme() {
        val newTheme = when (themeManager.theme) {
            Theme.DARK -> Theme.LIGHT
            Theme.LIGHT -> Theme.DARK
        }

        val isDarkTheme = newTheme == Theme.DARK

        themeManager.theme = newTheme
        prefManager.isDarkTheme = isDarkTheme

        viewModelScope.launch {
            val newValue = _state.value.copy(
                theme = newTheme,
                isDarkTheme = isDarkTheme,
                hasThemeChanged = true
            )

            _state.value = newValue
        }
    }
}