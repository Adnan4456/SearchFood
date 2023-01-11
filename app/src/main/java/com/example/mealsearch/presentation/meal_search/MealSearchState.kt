package com.example.mealsearch.presentation.meal_search

import androidx.paging.PagingData
import com.example.mealsearch.data.model.MealDTO
import com.example.mealsearch.domain.model.Meal

data class MealSearchState(
    val data: PagingData<MealDTO>? = null,
    val error:String  = "",
    val isLoading:Boolean = false
) {
}