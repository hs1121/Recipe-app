package com.harpreet.recipe.models

import com.google.gson.annotations.SerializedName

data class FoodJoke(
    @SerializedName("text")
    val joke: String?
)