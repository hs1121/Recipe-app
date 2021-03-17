package com.harpreet.recipe.data.network

import com.harpreet.recipe.models.FoodJoke
import com.harpreet.recipe.models.FoodRecipe
import com.harpreet.recipe.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface FoodRecipesApi {
    @GET("/recipes/complexSearch")
    suspend fun getRecipes(
        @QueryMap queries :Map<String,String>
    ): Response<FoodRecipe>
//
//    @GET("/recipes/complexSearch")
//    suspend fun searchRecipes(
//        @QueryMap searchQuery :Map<String,String>
//    ): Response<FoodRecipe>

    @GET("/food/jokes/random")
    suspend fun getJoke(@Query(Constants.QUERY_API_KEY) apiKey:String) :Response<FoodJoke>
}