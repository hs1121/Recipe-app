package com.harpreet.recipe.ui.fragments.display_fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import com.harpreet.recipe.R
import com.harpreet.recipe.databinding.FragmentInstructionsBinding
import com.harpreet.recipe.models.Result


class InstructionsFragment : Fragment() {

    private lateinit var binding:FragmentInstructionsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentInstructionsBinding.inflate(inflater, container, false)

        val args=arguments
        val result=args?.getParcelable<Result>("result")
        val resultUrl=result?.sourceUrl!!
        binding.webView.webViewClient=WebViewClient()
        binding.webView.loadUrl(resultUrl)

        return binding.root
    }

    inner class WebViewClient : android.webkit.WebViewClient() {
        @RequiresApi(Build.VERSION_CODES.Q)
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            view.isForceDarkAllowed=true
            return false
        }
        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            binding.progressBar.visibility = View.GONE
        }
    }

}