package com.harpreet.recipe.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.harpreet.recipe.models.Result
import com.harpreet.recipe.util.Constants.Companion.FAVORITE_FOOD_RECIPES

@Entity(tableName = FAVORITE_FOOD_RECIPES)
class FavoriteRecipeEntity(var result:Result) {
    @PrimaryKey(autoGenerate = true)
     var id=0
}