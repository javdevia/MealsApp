package com.project.mealsapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.project.mealsapp.adapter.MealsAdapter
import com.project.mealsapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mealsAdapter: MealsAdapter
    private lateinit var retrofit: Retrofit
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        retrofit = getRetrofit()
        initUI()
    }

    private fun initUI() {

        binding.svMeal.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchMeals(query.orEmpty())
                return false
            }

            override fun onQueryTextChange(newText: String?) = false
        })

        mealsAdapter = MealsAdapter()
        binding.rvMeals.setHasFixedSize(true)
        binding.rvMeals.layoutManager = GridLayoutManager(this, 2)
        binding.rvMeals.adapter = mealsAdapter
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun searchMeals(query: String) {

        binding.progressBar.isVisible = true
        CoroutineScope(Dispatchers.IO).launch {
            val myResponse = retrofit.create(MealsService::class.java).getMeals(query)
            val response = myResponse.body()
            if (response != null) {
                runOnUiThread {
                    mealsAdapter.updateList(response.info)
                    binding.progressBar.isVisible = false
                }
            }
        }
    }
}