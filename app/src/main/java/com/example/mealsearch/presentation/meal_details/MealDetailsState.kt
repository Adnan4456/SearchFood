package com.example.mealsearch.presentation.meal_details

import com.example.mealsearch.domain.model.MealDetails

data class MealDetailsState(
    val isLoading: Boolean = false,
    val data: MealDetails? = null,
    val error: String = ""
) {
}