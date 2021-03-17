package com.harpreet.recipe.ui.fragments.main_fragments

import android.app.Activity
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.harpreet.recipe.R
import com.harpreet.recipe.adapters.RecipesAdapter
import com.harpreet.recipe.databinding.FragmentRecipeSearchBinding
import com.harpreet.recipe.util.NetworkResult
import com.harpreet.recipe.viewModels.MainViewModel
import com.harpreet.recipe.viewModels.RecipeViewModel

class RecipeSearchFragment : Fragment(),SearchView.OnQueryTextListener{

    private lateinit var binding:FragmentRecipeSearchBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipeViewModel:RecipeViewModel
    private lateinit var mAdapter:RecipesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mainViewModel=ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        recipeViewModel = ViewModelProvider(requireActivity()).get(RecipeViewModel::class.java)
        binding= FragmentRecipeSearchBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        mAdapter= RecipesAdapter()
        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {
        binding.searchRecyclerView.adapter=mAdapter
        binding.searchRecyclerView.layoutManager=LinearLayoutManager(requireContext())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.recipes_search,menu)
        val search=menu.findItem(R.id.recipes_search)
        val searchView=search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled=true
        searchView?.setIconifiedByDefault(false)
        searchView?.setOnQueryTextListener(this)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==android.R.id.home)
            findNavController().navigate(R.id.action_recipeSearchFragment_to_recipesFragment)
        return super.onOptionsItemSelected(item)
    }
    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query!=null)
            searchApiData(query)

        return true
    }


    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }


    private fun searchApiData(searchQuery: String) {
        if(mainViewModel.hasInternetConnection()) {
            mainViewModel.searchRecipe(recipeViewModel.applySearchQueries(searchQuery))
            mainViewModel.searchRecipeResponse.observe(viewLifecycleOwner, { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        hideShimmerEffect()
                        response.data?.let { mAdapter.setResults(it) }
                    }
                    is NetworkResult.Loading -> {
                        showShimmerEffect()
                    }
                    is NetworkResult.Error -> {
                        hideShimmerEffect()
                        Toast.makeText(
                            requireContext(),
                            response.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
        }
        else{
            Toast.makeText(requireContext(), "No Internet", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showShimmerEffect() {
        binding.searchRecyclerView.showShimmer()
    }

    private fun hideShimmerEffect() {
        binding.searchRecyclerView.hideShimmer()
    }

    private fun Activity.hideSoftKeyboard() {
        currentFocus?.let {
            val inputMethodManager = ContextCompat.getSystemService(this, InputMethodManager::class.java)!!
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
    override fun onPause() {
        super.onPause()
            activity?.hideSoftKeyboard()
    }

}