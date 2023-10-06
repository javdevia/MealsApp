package com.project.mealsapp

import com.google.gson.annotations.SerializedName

data class MealsResponse(
    @SerializedName("meals") val info: List<MealsData>,
)

data class MealsData(
    @SerializedName("strMeal") val name: String,
    @SerializedName("strMealThumb") val image: String,
    @SerializedName("idMeal") val id: String,
    var favorite: Boolean = false,
)