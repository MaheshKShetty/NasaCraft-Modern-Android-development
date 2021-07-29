package com.example.nasaapp

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import java.net.URL


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val astronomyViewModel: AstronomyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getAstronomyData()
        observeModels()
    }

    private fun getAstronomyData() {
        astronomyViewModel.getAstronomyResponse()
    }

    private fun observeModels() {

        astronomyViewModel.astronomyData.observe( this, { state ->
            when (state) {
                is State.Success -> {
                  state.data.let {
                      pgBar.visibility = View.GONE
                      tvTitle.text = it.title
                      tvExplaination.text = it.explanation
                      Picasso.get().load(it.hdurl).placeholder(R.drawable.ic_broken_image)
                          .error(R.drawable.ic_broken_image).into(ivImage)
                  }
                }
                is State.Loading -> {
                    pgBar.visibility = View.VISIBLE
                }
                is State.Error -> {

                }
            }
        })
    }

}