package com.harpreet.recipe.ui.fragments.main_fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.harpreet.recipe.R
import com.harpreet.recipe.adapters.RecipesAdapter
import com.harpreet.recipe.databinding.FragmentFavoriteBinding
import com.harpreet.recipe.models.Result
import com.harpreet.recipe.viewModels.MainViewModel
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment() {

    private lateinit var binding:FragmentFavoriteBinding
    private  val mAdapter=RecipesAdapter()
    private lateinit var mainViewModel:MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mainViewModel=ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        binding= FragmentFavoriteBinding.inflate(inflater, container, false)


        mainViewModel.readFavoriteRecipe?.observe(viewLifecycleOwner,{
            val listOfResults=ArrayList<Result>()
            lifecycleScope.launch {
                for (i in it)
                    listOfResults.add(i.result)
                mAdapter.setResults(listOfResults)
                setUpRecyclerView()
            }

        })

        return binding.root
    }

    private fun setUpRecyclerView() {
        binding.favoriteRecyclerView.layoutManager=LinearLayoutManager(requireContext())
        binding.favoriteRecyclerView.adapter=mAdapter
    }


}