package com.harpreet.recipe.data

import com.harpreet.recipe.data.network.FoodRecipesApi
import com.harpreet.recipe.models.FoodJoke
import com.harpreet.recipe.models.FoodRecipe
import com.harpreet.recipe.util.Constants
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi
) {

    suspend fun getRecipes(queries: Map<String,String>):Response<FoodRecipe>{
        return foodRecipesApi.getRecipes(queries)
    }
//    suspend fun searchRecipes(searchQuery: Map<String,String>):Response<FoodRecipe>{
//        return foodRecipesApi.searchRecipes(searchQuery)
//    }
    suspend fun foodJoke():Response<FoodJoke>{
        return foodRecipesApi.getJoke(Constants.API_KEY)
    }
}