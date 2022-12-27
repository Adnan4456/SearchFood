package com.example.mealsearch.hilt

import com.example.mealsearch.common.Constants
import com.example.mealsearch.data.remote.MealSearchAPI
import com.example.mealsearch.data.repository.GetMealDetailsImpl
import com.example.mealsearch.data.repository.GetMealListImpl
import com.example.mealsearch.domain.repository.GetMealDetailsRepository
import com.example.mealsearch.domain.repository.MealSearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    @Singleton
    fun provideMealSearchAPI():MealSearchAPI{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MealSearchAPI::class.java)
    }

    @Provides
    fun provideMealSearchRepository(mealSearchAPI: MealSearchAPI)
    :MealSearchRepository{
        return GetMealListImpl(mealSearchAPI = mealSearchAPI)
    }

    @Provides
    fun provideMealDetailsRepository(mealSearchAPI: MealSearchAPI)
    :GetMealDetailsRepository{
        return  GetMealDetailsImpl(mealSearchAPI)
    }

}