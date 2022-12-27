package com.example.mealsearch.domain.repository

import com.example.mealsearch.data.model.MealsDTO

interface MealSearchRepository {

    suspend fun getMealList(s:String):MealsDTO

}