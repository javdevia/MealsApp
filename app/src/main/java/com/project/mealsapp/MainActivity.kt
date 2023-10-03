package com.project.mealsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.mealsapp.databinding.ActivityMainBinding
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getRetrofit()
        initUI()
    }

    private fun initUI() {

//            .getMeal("chicken_breast")
//           .meals
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}