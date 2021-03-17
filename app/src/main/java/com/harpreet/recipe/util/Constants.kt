package com.harpreet.recipe.util

class Constants {
    companion object{
        const val BASE_URL="https://api.spoonacular.com"
        // TODO : add api key
        const val API_KEY="Api key from spoonacular"        error statement // please insert api key before proceeding

        // API Query Keys
        const val QUERY_SEARCH="query"
        const val QUERY_NUMBER = "number"
        const val QUERY_API_KEY = "apiKey"
        const val QUERY_TYPE = "type"
        const val QUERY_DIET = "diet"
        const val QUERY_ADD_RECIPE_INFORMATION = "addRecipeInformation"
        const val QUERY_FILL_INGREDIENTS = "fillIngredients"
        const val IMAGE_URL="https://api.spoonacular.com/cdn/ingredients_"
        const val IMAGE_SIZE="100x100/"
        // Room
        const val RECIPES_TABLE = "recipes_table"
        const val RECIPES_DATABASE="recipes_database"
        const val FAVORITE_FOOD_RECIPES="favorite_food_recipes"

        // Bottom sheets and preferences

        const val DEFAULT_RECIPES_NUMBER="50"
        const val DEFAULT_MEAL_TYPE="main course"
        const val DEFAULT_DIET_TYPE="gluten free"

        const val PREFERENCE_NAME="foodyPreference"
        const val PREFERENCE_MEAL_TYPE="mealType"
        const val PREFERENCE_MEAL_TYPE_ID="mealTypeId"
        const val PREFERENCE_DIET_TYPE="dietType"
        const val PREFERENCE_DIET_TYPE_ID="dietTypeId"
    }
}