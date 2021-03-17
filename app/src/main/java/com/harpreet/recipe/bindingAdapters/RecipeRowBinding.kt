package com.harpreet.recipe.bindingAdapters

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.harpreet.recipe.R
import com.harpreet.recipe.models.Result
import com.harpreet.recipe.ui.fragments.main_fragments.FavoriteFragmentDirections
import com.harpreet.recipe.ui.fragments.main_fragments.RecipeSearchFragment
import com.harpreet.recipe.ui.fragments.main_fragments.RecipeSearchFragmentDirections
import com.harpreet.recipe.ui.fragments.main_fragments.RecipesFragmentDirections
import org.jsoup.Jsoup


class RecipeRowBinding {
    companion object {
//    @JvmStatic
  //  var parseObj:Result?=null

    @BindingAdapter("onRecipeClickListener")
    @JvmStatic
    fun onRecipeClickListener(recipesRowLayout: ConstraintLayout, result: Result){
        recipesRowLayout.setOnClickListener{

                // safeArgs isnt working properly
                //val action= RecipesFragmentDirections.actionRecipesFragmentToDetailActivity(result)
                try {
                    recipesRowLayout.findNavController().navigate(
                        RecipesFragmentDirections.actionRecipesFragmentToDetailActivity(result))
                } catch (e:Exception) {
                    try {
                        recipesRowLayout.findNavController().navigate(
                            FavoriteFragmentDirections.actionFavoriteFragmentToDetailActivity(result)
                        )
                    }
                    catch (e:java.lang.Exception) {
                        recipesRowLayout.findNavController().navigate(
                            RecipeSearchFragmentDirections.actionRecipeSearchFragmentToDetailActivity(result))
                    }
                }



        }
    }

        @BindingAdapter("setImage")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, url: String) {
            imageView.load(url) {
                crossfade(600)
                error(R.drawable.error_image_place_holder)
            }
        }

        @BindingAdapter("setNumberOfLikes")
        @JvmStatic
        fun setNumberOfLikes(textView: TextView, count: Int) {
            textView.text = count.toString()
        }

        @BindingAdapter("setCookingTime")
        @JvmStatic
        fun setCookingTime(textView: TextView, count: Int) {
            textView.text = count.toString()
        }

        @BindingAdapter("applyVeganName")
        @JvmStatic
        fun isVegan(view: View, vegan: Boolean) {
            var color = R.color.mediumGray
            if (vegan)
                color = R.color.green

            when (view) {
                is TextView -> view.setTextColor(
                    ContextCompat.getColor(view.context, color)
                )
                is ImageView -> view.setColorFilter(
                    ContextCompat.getColor(
                        view.context, color
                    )
                )
            }

        }
        @BindingAdapter("setText")
        @JvmStatic
        fun setText(view: TextView, string: String) {
            val text=Jsoup.parse(string).text()
            view.text=text
        }
    }
}