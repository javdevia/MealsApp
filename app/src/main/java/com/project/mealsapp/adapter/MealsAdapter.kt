package com.project.mealsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.mealsapp.MealsData
import com.project.mealsapp.R

class MealsAdapter(private var mealsList: List<MealsData> = emptyList()) :
    RecyclerView.Adapter<MealsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsViewHolder {
        return MealsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_meal, parent)
        )
    }

    override fun getItemCount() = mealsList.size

    override fun onBindViewHolder(holder: MealsViewHolder, position: Int) {
        holder.render(mealsList[position])
    }

    fun updateList(list: List<MealsData>) {
        mealsList = list
        notifyDataSetChanged()
    }

}