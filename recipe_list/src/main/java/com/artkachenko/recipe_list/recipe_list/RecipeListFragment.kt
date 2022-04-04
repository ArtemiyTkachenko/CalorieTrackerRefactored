package com.artkachenko.recipe_list.recipe_list

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.artkachenko.core_api.base.BaseFragment
import com.artkachenko.core_api.network.models.FilterWrapper
import com.artkachenko.core_api.network.models.RecipeEntity
import com.artkachenko.core_api.utils.debugLog
import com.artkachenko.recipe_list.R
import com.artkachenko.recipe_list.databinding.FragmentRecipeListBinding
import com.artkachenko.recipe_list.recipe_list.adapter.RecipeListAdapter
import com.artkachenko.ui_utils.*
import com.artkachenko.ui_utils.themes.ThemeManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import viewBinding
import javax.inject.Inject

@AndroidEntryPoint
class RecipeListFragment : BaseFragment(R.layout.fragment_recipe_list), RecipeListActions {

    @Inject
    lateinit var themeManager: ThemeManager

    private val viewModel by viewModels<RecipeListViewModel>()

    private var binding by viewBinding<FragmentRecipeListBinding>()

    private val adapter = RecipeListAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRecipeListBinding.bind(view)
        lifecycleScope.launch {
            viewModel.recipes.collect {
                debugLog("from fragment, results are $it")
                processState(it)
            }
        }

        binding.search.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
            .setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    v.clearFocus()
                    navigateToSearch(null)
                }
            }

        binding.searchViewContainer.setSingleClickListener {
            navigateToSearch(null)
        }
    }

    override fun onItemClicked(model: RecipeEntity, view: View) {
        val bundle = Bundle().apply {
            putLong(ID, model.id)
            putString(TRANSITION_NAME, view.transitionName)
        }

        val extras = FragmentNavigatorExtras(view to view.transitionName)

        findNavController().navigate(R.id.recipe_to_detail, bundle, null, extras)
    }

    override fun navigateToSearch(filters: FilterWrapper?) {
        val bundle = Bundle().apply {
            putParcelable(PRESETS, filters)
        }
        val extras = FragmentNavigatorExtras(binding.searchViewContainer to "searchViewContainer")

        findNavController().navigate(R.id.recipe_to_search, bundle, null, extras)
    }

    private fun processState(state: RecipeListViewModel.State) {
        when (state) {
            RecipeListViewModel.State.FirstItemEmitted -> {
            }
            is RecipeListViewModel.State.Data -> adapter.setInitial(state.data.wrapper)

            RecipeListViewModel.State.Initial -> {}
            RecipeListViewModel.State.Loading -> {
            }
        }

    }

    private fun generatePlaceholderList(): List<RecipeEntity> {
        return List(5) { index: Int -> RecipeEntity() }
    }
}