package com.harpreet.recipe.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.harpreet.recipe.databinding.RecipeRowLayoutBinding
import com.harpreet.recipe.models.FoodRecipe
import com.harpreet.recipe.models.Result
import com.harpreet.recipe.util.RecipesDiffUtil

class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.MyViewHolder>() {

    private var results= emptyList<Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val binding = RecipeRowLayoutBinding.inflate(inflater,parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val current = results.get(position)
        holder.bind(current)
    }

    override fun getItemCount(): Int {
       return results.size
    }

    class MyViewHolder( private val binding: RecipeRowLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(result:Result){
            binding.result=result
            binding.executePendingBindings()
        }
    }

    fun setResults(recipe: FoodRecipe){
        val recipesDiffUtil= RecipesDiffUtil(this.results,recipe.results)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        this.results=recipe.results
        diffUtilResult.dispatchUpdatesTo(this)
    }
    fun setResults(results: List<Result>){
        val recipesDiffUtil= RecipesDiffUtil(this.results,results)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        this.results=results
        diffUtilResult.dispatchUpdatesTo(this)
    }

}