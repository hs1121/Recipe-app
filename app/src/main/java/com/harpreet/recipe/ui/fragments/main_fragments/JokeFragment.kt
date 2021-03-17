package com.harpreet.recipe.ui.fragments.main_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.harpreet.recipe.R
import com.harpreet.recipe.databinding.FragmentJokeBinding
import com.harpreet.recipe.viewModels.MainViewModel

class JokeFragment : Fragment() {

    private lateinit var binding:FragmentJokeBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mainViewModel=ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        binding= FragmentJokeBinding.inflate(inflater, container, false)

       mainViewModel.getJoke()
        mainViewModel.foodJokeLiveData?.observe(viewLifecycleOwner,{
            binding.jokeText.text=it.joke
        })

        return binding.root
    }
}