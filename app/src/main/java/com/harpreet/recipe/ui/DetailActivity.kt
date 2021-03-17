package com.harpreet.recipe.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.google.android.material.snackbar.Snackbar
import com.harpreet.recipe.R
import com.harpreet.recipe.adapters.PageAdapter
import com.harpreet.recipe.bindingAdapters.RecipeRowBinding
import com.harpreet.recipe.data.database.FavoriteRecipeEntity
import com.harpreet.recipe.databinding.ActivityDetailBinding
import com.harpreet.recipe.models.Result
import com.harpreet.recipe.ui.fragments.display_fragments.IngredientsFragment
import com.harpreet.recipe.ui.fragments.display_fragments.InstructionsFragment
import com.harpreet.recipe.ui.fragments.display_fragments.OverviewFragment
import com.harpreet.recipe.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var  binding : ActivityDetailBinding
    private lateinit var adapter:PageAdapter
    private val mainViewModel:MainViewModel by viewModels()
    private var favoriteList= emptyList<FavoriteRecipeEntity>()
    private lateinit var result:Result
    private lateinit var entity:FavoriteRecipeEntity

    private val args by navArgs<DetailActivityArgs>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding= ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
       // binding.toolbar.setTitleTextColor(getColor(R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
       setUpPageAdapter()
        binding.viewPager.adapter=adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)

       mainViewModel.readFavoriteRecipe?.observe(this,{
           this.favoriteList=it
       })

    }

    private fun setUpPageAdapter() {
        val fragments=ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        val titles=ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instructions")
           result=args.result              // safe args not working
        //result = RecipeRowBinding.parseObj!!

        val resultBundle=Bundle()
        resultBundle.putParcelable("result",result)
         adapter = PageAdapter(resultBundle,fragments,titles,supportFragmentManager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorite_menu,menu)
        val item=menu?.findItem(R.id.favorite)
        if(alreadyFavorite()){
            item?.isChecked=true
            item?.setIcon(R.drawable.ic_star)
        }
     //   Log.d("checking foe checked",item?.isChecked.toString())
        item?.setOnMenuItemClickListener {
                  if(it.isChecked){
                      it.setIcon(R.drawable.ic_star_unchecked)
                      removeFavorite()
                  }else{
                      it.setIcon(R.drawable.ic_star)
                      addFavorite()
                  }
            it.isChecked=!it.isChecked

         //   Log.d("checking foe checked",it.isChecked.toString())
            return@setOnMenuItemClickListener true
        }
        return true
    }

    private fun removeFavorite() {
        mainViewModel.deleteFavoriteRecipe(entity)
        Snackbar.make(binding.root,"Deleted ${entity.result.title} from favorite",Snackbar.LENGTH_SHORT).show()
    }

    private fun addFavorite() {
    val item=FavoriteRecipeEntity(result)
        mainViewModel.insertFavoriteRecipes(item)
        entity=item
        Snackbar.make(binding.root,"Added to favorite",Snackbar.LENGTH_SHORT).show()
    }

    private fun alreadyFavorite():Boolean {
            for(i in favoriteList){
                if(i.result.id==result.id){
                    entity=i
                    return true
                }
            }
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item)
    }
}