package com.example.mealsearch.presentation.meal_search

import com.example.mealsearch.domain.model.Meal

data class MealSearchState(
    val data : List<Meal>? = null,
    val error:String  = "",
    val isLoading :Boolean = false
) {
}