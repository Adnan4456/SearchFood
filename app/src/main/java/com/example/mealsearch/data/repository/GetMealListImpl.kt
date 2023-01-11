package com.example.mealsearch.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mealsearch.data.model.MealDTO
import com.example.mealsearch.data.model.MealsDTO
import com.example.mealsearch.data.remote.MealSearchAPI
import com.example.mealsearch.domain.model.Meal
import com.example.mealsearch.domain.repository.MealSearchRepository
import kotlinx.coroutines.flow.Flow

class GetMealListImpl(
    private val mealSearchAPI: MealSearchAPI
): MealSearchRepository {

    override  fun getMealList(s: String): Flow<PagingData<MealDTO>> {

        return Pager(
                pagingSourceFactory = {MealPagingSource(mealSearchAPI, s)},
            config = PagingConfig(pageSize = 20, maxSize = 100)
        ).flow
    }

//    override suspend fun  getMealList(s: String): MealsDTO {
//    return  mealSearchAPI.getMealList(s)
//    }

}