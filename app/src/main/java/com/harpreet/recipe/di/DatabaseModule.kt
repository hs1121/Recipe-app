package com.harpreet.recipe.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.harpreet.recipe.data.database.FavoriteRecipesDao
import com.harpreet.recipe.data.database.RecipesDao
import com.harpreet.recipe.data.database.RecipesDatabase
import com.harpreet.recipe.util.Constants.Companion.RECIPES_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context):RecipesDatabase{
        return Room.databaseBuilder(context,RecipesDatabase::class.java,RECIPES_DATABASE).build()
    }
    @Singleton
    @Provides
    fun provideDao(database: RecipesDatabase):RecipesDao{
        return database.recipesDao()
    }
    @Singleton
    @Provides
    fun provideFavoriteRecipesDao(database: RecipesDatabase):FavoriteRecipesDao{
        return database.favoriteRecipesDao()
    }




}