package com.harpreet.recipe.data

import com.harpreet.recipe.data.database.FavoriteRecipeEntity
import com.harpreet.recipe.data.database.FavoriteRecipesDao
import com.harpreet.recipe.data.database.RecipeEntity
import com.harpreet.recipe.data.database.RecipesDao
import com.harpreet.recipe.models.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private var recipesDao: RecipesDao,private val favoriteRecipesDao: FavoriteRecipesDao){

    fun readRecipesDatabase():Flow<List<RecipeEntity>>{
        return recipesDao.getRecipes()
    }
    suspend fun insertRecipes(recipeEntity: RecipeEntity){
                recipesDao.insertRecipes(recipeEntity)
    }
    suspend fun insertFavoriteRecipe(favoriteRecipeEntity: FavoriteRecipeEntity){
        favoriteRecipesDao.insertFavoriteRecipe(favoriteRecipeEntity)
    }
    fun readFavoriteRecipes():Flow<List<FavoriteRecipeEntity>>{
        return  favoriteRecipesDao.getFavoriteRecipes()
    }
    suspend fun deleteFavoriteRecipe(result:FavoriteRecipeEntity){
        favoriteRecipesDao.deleteFavoriteRecipe(result)
    }
}