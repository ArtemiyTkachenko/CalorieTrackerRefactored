package com.artkachenko.settings

import android.os.Parcelable
import com.artkachenko.ui_utils.themes.Theme
import kotlinx.parcelize.Parcelize

@Parcelize
data class SettingsViewData(
    val isDarkTheme: Boolean,
    val desiredCalories: String,
    val theme: Theme
): Parcelable