package com.example.mealsearch.domain.use_case

import com.example.mealsearch.common.Resource
import com.example.mealsearch.data.model.toDomainMeal
import com.example.mealsearch.domain.model.Meal
import com.example.mealsearch.domain.repository.MealSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMealSearchListUseCase
@Inject
constructor(private val  repository: MealSearchRepository){


    operator fun invoke(s:String) : Flow<Resource<List<Meal>>> = flow {
        try {

            //first emit loading state to show progress bar
            emit(Resource.Loading())

            val response = repository.getMealList(s)
            val list =  if (response.meals.isNullOrEmpty()) emptyList<Meal>()
                        else response.meals.map {
                it.toDomainMeal() }

            //after this our loading state is ended because we loaded all data emit this data
            emit(Resource.Success(data = list))

        }catch (e: HttpException){
            emit(Resource.Error(message=e.localizedMessage ?: "Unknown error"))
        }catch (io : IOException){
            emit(Resource.Error(message=io.localizedMessage ?: " Please check you internet"))
        }catch (e : Exception){
            emit(Resource.Error(message=e.localizedMessage ?: "Unknown error "))
        }
    }
}