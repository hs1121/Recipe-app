package com.harpreet.recipe.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.harpreet.recipe.models.FoodRecipe
import com.harpreet.recipe.util.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipeEntity (var foodRecipe: FoodRecipe){
    @PrimaryKey(autoGenerate = false)
        var id:Int=0
}