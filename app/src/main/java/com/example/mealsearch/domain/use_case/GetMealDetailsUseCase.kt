package com.example.mealsearch.domain.use_case

import com.example.mealsearch.common.Resource
import com.example.mealsearch.data.model.toDomainMealDetails
import com.example.mealsearch.domain.model.Meal
import com.example.mealsearch.domain.model.MealDetails
import com.example.mealsearch.domain.repository.GetMealDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMealDetailsUseCase
@Inject
constructor(private val repository: GetMealDetailsRepository)
{

    operator fun invoke(id:String) : Flow<Resource<MealDetails>> = flow{

        try {
            //first emit loading state to show progress bar
            emit(Resource.Loading())

            val response = repository.getMealDetails(id).meals[0].toDomainMealDetails()

            //Now emit data to UI
            emit(Resource.Success(data = response))


        }catch (e: HttpException){
            emit(Resource.Error(message=e.localizedMessage ?: "Unknown error"))
        }catch (io : IOException){
            emit(Resource.Error(message=io.localizedMessage ?: " Please check you internet connection"))
        }catch (e : Exception){
            emit(Resource.Error(message=e.localizedMessage ?: "Unknown error "))
        }
    }

}