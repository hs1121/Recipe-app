package com.harpreet.recipe.data.database

import androidx.room.*
import com.harpreet.recipe.models.Result
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteRecipesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteRecipe(favoriteRecipeEntityEntity: FavoriteRecipeEntity)

    @Query("SELECT * FROM FAVORITE_FOOD_RECIPES ORDER BY id ASC")
    fun getFavoriteRecipes(): Flow<List<FavoriteRecipeEntity>>

    @Delete
     suspend fun deleteFavoriteRecipe(result:FavoriteRecipeEntity)

}