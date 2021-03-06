package com.artkachenko.settings

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import android.view.inputmethod.EditorInfo
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.artkachenko.core_api.base.BaseFragment
import com.artkachenko.settings.databinding.FragmentSettingsBinding
import com.artkachenko.ui_utils.ImageUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import viewBinding
import kotlin.math.hypot


@AndroidEntryPoint
class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    private var binding by viewBinding<FragmentSettingsBinding>()

    private val viewModel by activityViewModels<SettingsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSettingsBinding.bind(view)

        with(binding) {

            darkThemeSwitch.setOnClickListener {
                viewModel.setNewTheme()
            }

            desiredCaloriesText.addTextChangedListener {
                viewModel.setDesiredCalories(it.toString())
            }

            desiredCaloriesText.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    desiredCaloriesText.clearFocus()
                }
                false
            }
        }

        initObservers()
    }

    override fun onResume() {
        (activity as ImageUtils.CanHideBottomNavView).showNavigationBar(true)
        super.onResume()
    }

    private fun setTheme() {
        with(binding) {
            if (themeImageView.isVisible) {
                return
            }

            val w = container.measuredWidth
            val h = container.measuredHeight

            val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            container.draw(canvas)

            themeImageView.setImageBitmap(bitmap)
            themeImageView.isVisible = true

            val animX = darkThemeSwitch.x.toInt() + darkThemeSwitch.width / 2
            val animY = darkThemeSwitch.y.toInt() + darkThemeSwitch.height / 2

            val finalRadius = hypot(w.toFloat(), h.toFloat())

            val anim =
                ViewAnimationUtils.createCircularReveal(container, animX, animY, 0f, finalRadius)
            anim.duration = 400L
            anim.doOnEnd {
                themeImageView.setImageDrawable(null)
                themeImageView.isVisible = false
            }
            anim.start()
        }
    }

    private fun initObservers() {
        lifecycleScope.launchWhenResumed {
            viewModel.state.collect { data ->
                with(binding) {
                    darkThemeSwitch.isChecked = data.isDarkTheme
                    if (desiredCaloriesText.text.isNullOrEmpty()) desiredCaloriesText.setText(data.desiredCalories)
                    if (data.hasThemeChanged) setTheme()
                }
            }
        }
    }
}
