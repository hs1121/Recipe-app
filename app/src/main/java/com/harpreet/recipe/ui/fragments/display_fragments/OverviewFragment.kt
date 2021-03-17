package com.harpreet.recipe.ui.fragments.display_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import coil.load
import com.harpreet.recipe.R
import com.harpreet.recipe.databinding.FragmentOverviewBinding
import org.jsoup.Jsoup

class OverviewFragment : Fragment() {

    private lateinit var view:FragmentOverviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view= FragmentOverviewBinding.inflate(inflater, container, false)

        val args = arguments
        val myBundle = args?.getParcelable<com.harpreet.recipe.models.Result>("result")

        view.mainImageView.load(myBundle?.image)
        view.titleTextView.text = myBundle?.title
        view.likesTextView.text = myBundle?.aggregateLikes.toString()
        view.timeTextView.text = myBundle?.readyInMinutes.toString()
        myBundle?.summary.let {
            val summary = Jsoup.parse(it).text()
            view.summaryTextView.text = summary
        }

        if(myBundle?.vegetarian == true){
            view.vegetarianImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.vegetarianTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if(myBundle?.vegan == true){
            view.veganImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.veganTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if(myBundle?.glutenFree == true){
            view.glutenFreeImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.glutenFreeTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if(myBundle?.dairyFree == true){
            view.dairyFreeImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.dairyFreeTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if(myBundle?.veryHealthy == true){
            view.healthyImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.healthyTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if(myBundle?.cheap == true){
            view.cheapImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.cheapTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }
        return view.root
    }

}