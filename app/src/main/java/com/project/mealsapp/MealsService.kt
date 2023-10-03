package com.project.mealsapp

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealsService {
    @GET("filter.php")
    suspend fun getMeals(@Query("i") ingredient:String): Response<MealsResponse>
}