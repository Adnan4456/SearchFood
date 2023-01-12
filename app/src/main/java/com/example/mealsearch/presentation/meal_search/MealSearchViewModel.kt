package com.example.mealsearch.presentation.meal_search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    init {
//         searchMealList("chicken")
    }
    private val _mealSearchList = MutableStateFlow(MealSearchState())
    val mealSearchList:StateFlow<MealSearchState> = _mealSearchList

    private val _searchQuery = MutableLiveData("chicken") // default value
    val searchQuery : LiveData<String> = _searchQuery


    fun searchMealList(query:String): Flow<PagingData<MealDTO>> {

        _searchQuery.value = query

        return getMealSearchListUseCase.invoke(query)
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