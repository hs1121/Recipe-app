package com.harpreet.recipe.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.harpreet.recipe.R
import com.harpreet.recipe.databinding.IngredientRowLayoutBinding
import com.harpreet.recipe.models.ExtendedIngredient
import com.harpreet.recipe.util.Constants.Companion.IMAGE_SIZE
import com.harpreet.recipe.util.RecipesDiffUtil

class IngredientRowAdapter: RecyclerView.Adapter<IngredientRowAdapter.MyViewHolder>() {
    
    private var list= emptyList<ExtendedIngredient>()
    
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
       val inflater=LayoutInflater.from(parent.context)
        val binding=IngredientRowLayoutBinding.inflate(inflater,parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val current= list[position]
        val binding=IngredientRowLayoutBinding.bind(holder.itemView)

        val imageUrl="https://spoonacular.com/cdn/ingredients_$IMAGE_SIZE/${current.image}"
  //      Log.d("IMAGE_TEST",imageUrl)
        binding.ingredientImageView.load(imageUrl){
            crossfade(600)
            error(R.drawable.error_image_place_holder)
        }
        binding.ingredientName.text=current.name
        binding.ingredientAmount.text=current.amount.toString()
        binding.ingredientUnit.text=current.unit
        binding.ingredientOriginal.text=current.original
        binding.ingredientConsistency.text=current.consistency
    }

    override fun getItemCount(): Int {
        return list.size
    }
    class MyViewHolder(binding:IngredientRowLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    fun setIngredients(list:List<ExtendedIngredient>){
        val recipesDiffUtil= RecipesDiffUtil(this.list,list)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        this.list=list
        diffUtilResult.dispatchUpdatesTo(this)
    }

}