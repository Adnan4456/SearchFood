package com.example.mealsearch.presentation.meal_search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mealsearch.common.Resource
import com.example.mealsearch.data.model.MealDTO
import com.example.mealsearch.domain.use_case.GetMealSearchListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MealSearchViewModel
 @Inject
 constructor(private val getMealSearchListUseCase: GetMealSearchListUseCase)
    : ViewModel() {

    private val _mealSearchList = MutableStateFlow(MealSearchState())
    val mealSearchList:StateFlow<MealSearchState> = _mealSearchList



    fun searchMealList(s:String): Flow<PagingData<MealDTO>> {

        return getMealSearchListUseCase.invoke(s)
//            .flowOn(Dispatchers.IO)
            .cachedIn(viewModelScope)
/*
        getMealSearchListUseCase.invoke(s).onEach {


            when(it){
                is Resource.Loading ->{
                _mealSearchList.value = MealSearchState(isLoading = true)

                }
                is Resource.Success ->{
                    Log.d("TAG","collecting in viewModel scope $it")
                    _mealSearchList.value = MealSearchState(data = it.data)
                }
                is Resource.Error ->{
                    _mealSearchList.value = MealSearchState(error = it.message ?: "")
                }
            }
        }.flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
            */
    }
}