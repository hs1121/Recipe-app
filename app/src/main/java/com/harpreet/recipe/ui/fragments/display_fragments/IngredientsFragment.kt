package com.harpreet.recipe.ui.fragments.display_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.harpreet.recipe.R
import com.harpreet.recipe.adapters.IngredientRowAdapter
import com.harpreet.recipe.databinding.FragmentIngredientsBinding
import com.harpreet.recipe.models.Result
import com.harpreet.recipe.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IngredientsFragment : Fragment() {
        private lateinit var binding:FragmentIngredientsBinding
        private var mAdapter:IngredientRowAdapter= IngredientRowAdapter()
        private  val mainViewModel: MainViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentIngredientsBinding.inflate(inflater, container, false)
        val args=arguments
        val ingredients=args?.getParcelable<Result>("result")?.extendedIngredients!!
        setUpRecyclerView()
        mAdapter.setIngredients(ingredients)

        return binding.root
    }

    private fun setUpRecyclerView() {
        binding.ingredientsRecyclerview.adapter=mAdapter
        binding.ingredientsRecyclerview.layoutManager=LinearLayoutManager(requireContext())
    }

}