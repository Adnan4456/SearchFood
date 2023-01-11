package com.example.mealsearch.domain.use_case

import android.util.Log
import androidx.paging.PagingData
import androidx.paging.map
import com.example.mealsearch.common.Resource
import com.example.mealsearch.data.model.MealDTO
import com.example.mealsearch.data.model.toDomainMeal
import com.example.mealsearch.domain.model.Meal
import com.example.mealsearch.domain.repository.MealSearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMealSearchListUseCase
@Inject
constructor(private val  repository: MealSearchRepository){

    operator  fun invoke(s:String) = repository.getMealList(s)
        .flowOn(Dispatchers.IO)
/*
    operator fun invoke(s:String) :  Flow<Resource<PagingData<MealDTO>>> = channelFlow {
        try {

            //first emit loading state to show progress bar
            send(Resource.Loading())

            val response = repository.getMealList(s)
            response.collect{
                Log.d("TAG","emitting from use case   ${it}"  )
                send(Resource.Success(data = it))
            }


//            val list = if (response.toList().isNullOrEmpty()) emptyList<Meal>()
//                        else response.toList()

//            val list =  if (response.isNullOrEmpty()) emptyList<Meal>()
//                        else response.meals.map {
//                it.toDomainMeal() }

            //after this our loading state is ended because we loaded all data emit this data
//            emit(Resource.Success(data = list))

        }catch (e: HttpException){
            send(Resource.Error(message=e.localizedMessage ?: "Unknown error"))
        }catch (io : IOException){
            send(Resource.Error(message=io.localizedMessage ?: " Please check you internet"))
        }catch (e : Exception){
            send(Resource.Error(message=e.localizedMessage ?: "Unknown error "))
        }
    }

 */
}