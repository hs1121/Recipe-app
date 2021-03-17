package com.harpreet.recipe.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.harpreet.recipe.models.FoodRecipe
import com.harpreet.recipe.models.Result
import java.util.*

class RecipesTypeConverter {
        val gson =Gson()

    @TypeConverter
    fun foodRecipeToString(foodRecipe: FoodRecipe):String{
        return gson.toJson(foodRecipe)
    }
    @TypeConverter
    fun stringToFoodRecipe(string: String):FoodRecipe{
        val listType= object : TypeToken<FoodRecipe>() {}.type
        return gson.fromJson(string,listType)
    }
    @TypeConverter
    fun resultToString(result:Result):String{
        return gson.toJson(result)
    }

    @TypeConverter
    fun stringToResult(string: String):Result{
        val listType= object : TypeToken<Result>() {}.type
        return gson.fromJson(string,listType)
    }

}