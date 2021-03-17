package com.harpreet.recipe.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [RecipeEntity::class,FavoriteRecipeEntity::class],version = 1,exportSchema = false)
@TypeConverters(RecipesTypeConverter::class)
abstract class RecipesDatabase: RoomDatabase() {

    abstract fun favoriteRecipesDao():FavoriteRecipesDao
    abstract fun recipesDao(): RecipesDao
}