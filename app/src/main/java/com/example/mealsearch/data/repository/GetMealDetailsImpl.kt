package com.example.mealsearch.data.repository

import com.example.mealsearch.data.model.MealsDTO
import com.example.mealsearch.data.remote.MealSearchAPI
import com.example.mealsearch.domain.repository.GetMealDetailsRepository

class GetMealDetailsImpl(
    private val mealSearchAPI: MealSearchAPI
) : GetMealDetailsRepository {

    override suspend fun getMealDetails(id: String): MealsDTO {
        return mealSearchAPI.getMealDetails(id)
    }
}