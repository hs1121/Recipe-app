package com.harpreet.recipe.ui.fragments.main_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.harpreet.recipe.R
import com.harpreet.recipe.databinding.RecipesBottomSheetBinding
import com.harpreet.recipe.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.harpreet.recipe.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.harpreet.recipe.viewModels.RecipeViewModel

class RecipesBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding:RecipesBottomSheetBinding
    private lateinit var recipeViewModel:RecipeViewModel

    private var mealTypeChip=DEFAULT_MEAL_TYPE
    private var mealTypeChipId=0
    private var dietTypeChip= DEFAULT_DIET_TYPE
    private var dietTypeChipId=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipeViewModel=ViewModelProvider(requireActivity()).get(RecipeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         binding= RecipesBottomSheetBinding.inflate(inflater, container, false)

        recipeViewModel.readMealAndDietType.asLiveData().observe(viewLifecycleOwner,{
            mealTypeChip=it.selectedMealType
            dietTypeChip=it.selectedDietType
            updateChip(it.selectedMealTypeId,binding.mealTypeChipGroup)
            updateChip(it.selectedDietTypeId,binding.dietTypeChipGroup)
        })

        binding.mealTypeChipGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip=group.findViewById<Chip>(checkedId)
            val mealType=chip.text.toString()
            mealTypeChip=mealType
            mealTypeChipId=checkedId
        }
        binding.dietTypeChipGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip=group.findViewById<Chip>(checkedId)
            val dietType=chip.text.toString()
            dietTypeChip=dietType
            dietTypeChipId=checkedId
        }
        binding.applyBtn.setOnClickListener{
            recipeViewModel.saveMealAndDietTypes(mealTypeChip,mealTypeChipId,dietTypeChip,dietTypeChipId)
            val action =
                RecipesBottomSheetDirections.actionRecipesBottomSheetToRecipesFragment(true)
            findNavController().navigate(action)
        }
        return binding.root
    }

    private fun updateChip(id: Int, chipGroup: ChipGroup) {
                if(id!=0){
                    try{
                        chipGroup.findViewById<Chip>(id).isChecked=true
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                }
    }
    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme

    }
}