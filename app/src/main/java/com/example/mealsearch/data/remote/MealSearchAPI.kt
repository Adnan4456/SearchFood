package com.example.mealsearch.data.remote

import com.example.mealsearch.data.model.MealsDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface MealSearchAPI {

    //search meals
    @GET("api/json/v1/1/search.php")
    suspend fun getMealList(
        @Query("s") s:String,
        @Query("page") page:Int,
        @Query("limit") limit: Int
    ):MealsDTO

    //detail of meal
    @GET("api/json/v1/1/lookup.php")
    suspend fun getMealDetails(@Query("i") s:String):MealsDTO
}