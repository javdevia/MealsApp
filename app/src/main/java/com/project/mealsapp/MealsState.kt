package com.project.mealsapp

import retrofit2.Response

sealed class MealsState {
    data object Loading:MealsState()
    data class Error(val error:String):MealsState()
    data class Success(val mealsData: Response<MealsResponse>):MealsState()
}