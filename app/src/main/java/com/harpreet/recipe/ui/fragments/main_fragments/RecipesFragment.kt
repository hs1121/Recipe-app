package com.harpreet.recipe.ui.fragments.main_fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.harpreet.recipe.R
import com.harpreet.recipe.adapters.RecipesAdapter
import com.harpreet.recipe.databinding.FragmentRecipesBinding
import com.harpreet.recipe.util.NetworkListener
import com.harpreet.recipe.util.NetworkResult
import com.harpreet.recipe.util.observeOnce
import com.harpreet.recipe.viewModels.MainViewModel
import com.harpreet.recipe.viewModels.RecipeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RecipesFragment : Fragment(), View.OnClickListener {

    private val args by navArgs<RecipesFragmentArgs>()

    private lateinit var binding: FragmentRecipesBinding
    private val mAdapter by lazy { RecipesAdapter() }

    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipeViewModel: RecipeViewModel
    private lateinit var networkListener: NetworkListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        recipeViewModel = ViewModelProvider(requireActivity()).get(RecipeViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentRecipesBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        setupRecyclerView()
        binding.retryButton.setOnClickListener(this)
        binding.recipesFab.setOnClickListener(this)

        networkListener= NetworkListener()
        lifecycleScope.launchWhenResumed {
            networkListener.checkNetworkAvailability(requireContext()).collect {
                //Log.d("Internet = ",it.toString())
                if(it){
                    readDatabase()
                    hideNoInternetSign()
                }else{
                    loadDataFromCache()
                }
            }
        }

        return binding.root
    }

    private fun readDatabase() {
     //   Log.i("TAG_RecipesFragment", "readDatabase")
        lifecycleScope.launchWhenCreated {
            mainViewModel.readRecipe?.observeOnce(viewLifecycleOwner, { database ->
                if (database.isNotEmpty() && !args.fromBottomNevigation) {
                    val recipes = database[0].foodRecipe
                    hideShimmerEffect()
                    mAdapter.setResults(recipes)

                } else {
                    if (mainViewModel.hasInternetConnection()) {
                        requestApiData()
                    } else {
                        //Toast.makeText(requireContext(), "No Internet", Toast.LENGTH_SHORT).show()
                        loadDataFromCache()
                    }
                }
            })
        }
    }

    private fun requestApiData() {
    //    Log.i("TAG_RecipesFragment", "requestApiData")
        mainViewModel.getRecipe(recipeViewModel.applyQueries())
        mainViewModel.recipeResponse.observe(viewLifecycleOwner, { response ->
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
                    loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }



    private fun loadDataFromCache() {
     //   Log.i("TAG_RecipesFragment", "LoadFromCache")
        lifecycleScope.launch {
            mainViewModel.readRecipe?.observe(viewLifecycleOwner, { database ->
                if (database.isNotEmpty()) {
                    val recipes = database[0].foodRecipe
                    hideShimmerEffect()
                    mAdapter.setResults(recipes)
                }
                else{
                    showNoInternetSign()
                }
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_icon,menu)
        val search=menu.findItem(R.id.search_icon)
        search.setOnMenuItemClickListener {
            findNavController().navigate(R.id.action_recipesFragment_to_recipeSearchFragment)
            return@setOnMenuItemClickListener false
        }

    }


    private fun setupRecyclerView() {
        binding.recyclerView.adapter = mAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun showShimmerEffect() {
        binding.recyclerView.showShimmer()
    }

    private fun hideShimmerEffect() {
        binding.recyclerView.hideShimmer()
    }

    private fun showNoInternetSign(){
        binding.imageView.visibility = View.VISIBLE
        binding.text.visibility = View.VISIBLE
       // binding.retryButton.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
    }
    private fun hideNoInternetSign(){
        binding.imageView.visibility = View.INVISIBLE
        binding.text.visibility = View.INVISIBLE
        //binding.retryButton.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
    }

    override fun onClick(v: View?) {
        if (v == binding.retryButton) {
            readDatabase()
            Toast.makeText(requireContext(), "Retrying", Toast.LENGTH_SHORT).show()
        } else if (v == binding.recipesFab) {
            if(mainViewModel.hasInternetConnection())
            findNavController().navigate(R.id.action_recipesFragment_to_recipesBottomSheet)
            else
                Toast.makeText(requireContext(), "No Internet", Toast.LENGTH_SHORT).show()
        }
    }



}