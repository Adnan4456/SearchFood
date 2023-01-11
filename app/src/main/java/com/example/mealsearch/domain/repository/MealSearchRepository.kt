package com.example.mealsearch.domain.repository

import androidx.paging.PagingData
import com.example.mealsearch.data.model.MealDTO
import kotlinx.coroutines.flow.Flow

interface MealSearchRepository {

     fun getMealList(s:String): Flow<PagingData<MealDTO>>

}