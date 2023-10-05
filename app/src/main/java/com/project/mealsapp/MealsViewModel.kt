package com.project.mealsapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MealsViewModel : ViewModel() {

    private var _state = MutableStateFlow<MealsState>(MealsState.Loading)
    val state: StateFlow<MealsState> = _state
    private lateinit var retrofit: Retrofit


    fun findMeals(query: String) {
        viewModelScope.launch {
            _state.value = MealsState.Loading

            val result = withContext(Dispatchers.IO) {
                val mealsInfo = getMealsInfo(query)
                mealsInfo
            }
            when (result) {
                is Response<MealsResponse> -> {
                    if (!result.body()?.info.isNullOrEmpty()) {
                        _state.value = MealsState.Success(result)
                    } else {
                        _state.value =
                            MealsState.Error("No se encontraron comidas con ese ingrediente")
                    }
                }

                else -> {
                    _state.value = MealsState.Error("Error en la solicitud")
                }
            }
        }
    }

    private suspend fun getMealsInfo(query: String): Response<MealsResponse> {
        retrofit = getRetrofit()
        return retrofit
            .create(MealsService::class.java)
            .getMeals(query)
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
}