package com.project.mealsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.mealsapp.MealsData
import com.project.mealsapp.R

class MealsAdapter(
    private var mealsList: List<MealsData> = emptyList(),
    private val onItemSelected: (MealsData) -> Unit = {},
) :
    RecyclerView.Adapter<MealsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsViewHolder {
        return MealsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_meal, parent, false)
        )}

    override fun getItemCount() = mealsList.size

    override fun onBindViewHolder(holder: MealsViewHolder, position: Int) {
        val actualMeal = mealsList[position]
        holder.render(actualMeal)
        holder.binding.layoutMeal.setOnClickListener {
            actualMeal.favorite = !actualMeal.favorite
            notifyDataSetChanged()
        }

        holder.binding.layoutMeal.setOnClickListener {
            onItemSelected(actualMeal)
        }
    }

    fun updateList(list: List<MealsData>) {
        mealsList = list
        notifyDataSetChanged()
    }
}