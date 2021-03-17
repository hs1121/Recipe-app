package com.harpreet.recipe

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.harpreet.recipe.ui.MainActivity
import kotlinx.coroutines.delay

@Suppress("DEPRECATION")
class SplashScreen: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        supportActionBar?.hide()
        lifecycleScope.launchWhenResumed {
            delay(1000)
            startActivity(Intent(this@SplashScreen,MainActivity::class.java))
        }
    }
}