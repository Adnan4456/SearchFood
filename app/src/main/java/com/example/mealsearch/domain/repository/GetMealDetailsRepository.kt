package com.example.mealsearch.domain.repository

import com.example.mealsearch.data.model.MealsDTO

interface GetMealDetailsRepository {

    suspend fun getMealDetails(id:String):MealsDTO

}