package com.harpreet.recipe.viewModels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.viewModelScope
import android.util.Log
import androidx.lifecycle.*
import com.harpreet.recipe.data.Repository
import com.harpreet.recipe.data.database.FavoriteRecipeEntity
import com.harpreet.recipe.data.database.RecipeEntity
import com.harpreet.recipe.models.FoodJoke
import com.harpreet.recipe.models.FoodRecipe
import com.harpreet.recipe.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Response
import javax.inject.Inject
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
    , application: Application
): AndroidViewModel(application) {
    /**ROOM DATABASE*/
    val readRecipe: LiveData<List<RecipeEntity>>? = repository.local.readRecipesDatabase().asLiveData()
    val readFavoriteRecipe: LiveData<List<FavoriteRecipeEntity>>? = repository.local.readFavoriteRecipes().asLiveData()
    val foodJokeLiveData:MutableLiveData<FoodJoke>?= MutableLiveData<FoodJoke>()

    private fun insertRecipes(recipeEntity: RecipeEntity){
     //   Log.i("TAG_MainViewModel","insertRecipesFun")
        viewModelScope.launch {
            repository.local.insertRecipes(recipeEntity)
        }
    }
    fun deleteFavoriteRecipe(result:FavoriteRecipeEntity){
        viewModelScope.launch {
            repository.local.deleteFavoriteRecipe(result)
        }
    }
     fun insertFavoriteRecipes(favoriteRecipeEntity: FavoriteRecipeEntity){
   //     Log.i("TAG_MainViewModel","insertFavoriteRecipesFun")
        viewModelScope.launch {
            repository.local.insertFavoriteRecipe(favoriteRecipeEntity)
        }
    }

    /** RETROFIT*/
    var recipeResponse :MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()
    var searchRecipeResponse :MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()

     fun getRecipe(queries: Map<String,String>) = viewModelScope.launch { getRecipeSafeCall(queries) }
    fun searchRecipe(searchQuery: Map<String,String>) = viewModelScope.launch { searchRecipeSafeCall(searchQuery) }
    fun getJoke() = viewModelScope.launch { foodJokeLiveData?.value=safeFragmentJokeCall() }



    private suspend fun safeFragmentJokeCall():FoodJoke {
        var joke=FoodJoke("Cannot Load Joke")
        if(hasInternetConnection()) {
            val response=repository.remote.foodJoke()
         if(response.isSuccessful){
              joke=response.body()!!
           }
        }
        return joke
    }


    private suspend fun getRecipeSafeCall(queries: Map<String, String>){
        if(hasInternetConnection()) {
            recipeResponse.value = NetworkResult.Loading()
            try {
                val response = repository.remote.getRecipes(queries)
                recipeResponse.value = handleFoodRecipeResponse(response)
                val foodRecipe= recipeResponse.value!!.data
                if(foodRecipe!=null){
                   val recipeEntity=RecipeEntity(foodRecipe)
        //            Log.i("TAG_MainViewModel","insertRecipecall")
                    insertRecipes(recipeEntity)
                }
            }
            catch (e: Exception){
                recipeResponse.value=NetworkResult.Error("Recipes Not Found")
            }

        }
        else{
            recipeResponse.value=NetworkResult.Error("No Internet Connection")
        }
    }

    private suspend fun searchRecipeSafeCall(searchQuery: Map<String, String>) {
        if(hasInternetConnection()) {
        searchRecipeResponse.value = NetworkResult.Loading()
        try {
            val response = repository.remote.getRecipes(searchQuery)
            searchRecipeResponse.value = handleFoodRecipeResponse(response)
        }
        catch (e: Exception){
            recipeResponse.value=NetworkResult.Error("Recipes Not Found")
        }
    }
    else{
        recipeResponse.value=NetworkResult.Error("No Internet Connection")
    }
    }

    private fun handleFoodRecipeResponse(response: Response<FoodRecipe>): NetworkResult<FoodRecipe>? {
   //     Log.i("TAG_MainViewModel","handelFoodRecipeResponse")
        return when{
            response.message().contains("timeout")
            -> NetworkResult.Error("Timeout")
            response.code()== 402
            -> NetworkResult.Error("Api key Limited")
            response.body()!!.results.isNullOrEmpty()
            -> NetworkResult.Error("No Recipe found")

            response.isSuccessful->{
                val foodRecipe =response.body()!!
                NetworkResult.Success(foodRecipe)
            }
            else -> NetworkResult.Error(response.message().toString())
        }
    }

     fun hasInternetConnection(): Boolean {

        val connectivityManager =getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork =connectivityManager.activeNetwork?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false

        }
    }
}