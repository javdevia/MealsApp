package com.project.mealsapp.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.project.mealsapp.MealsData
import com.project.mealsapp.databinding.ItemMealBinding

class MealsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemMealBinding.bind(view)

    fun render(mealsResponse: MealsData) {
        binding.tvMeal.text = mealsResponse.name
        binding.ivMeal.load(mealsResponse.image)
    }
}