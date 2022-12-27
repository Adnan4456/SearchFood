package com.example.mealsearch.data.repository

import com.example.mealsearch.data.model.MealsDTO
import com.example.mealsearch.data.remote.MealSearchAPI
import com.example.mealsearch.domain.repository.MealSearchRepository

class GetMealListImpl(
    private val mealSearchAPI: MealSearchAPI
): MealSearchRepository {

    override suspend fun getMealList(s: String): MealsDTO {
    return  mealSearchAPI.getMealList(s)
    }

}