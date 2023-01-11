package com.example.mealsearch.presentation.meal_details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealsearch.common.Resource
import com.example.mealsearch.domain.use_case.GetMealDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MealDetailsViewModel
@Inject
constructor(
    private val mealDetailsUseCase: GetMealDetailsUseCase)
    :ViewModel() {

    private val _mealDetails = MutableStateFlow<MealDetailsState>(MealDetailsState())
    val mealDetails: StateFlow<MealDetailsState> = _mealDetails


    fun getMealDetails(id: String) {
        viewModelScope.launch {

            mealDetailsUseCase(id).collectLatest {
                when(it){

                    is Resource.Loading -> {
                        _mealDetails.value = MealDetailsState(isLoading = true)
                    }
                    is Resource.Error -> {
                        _mealDetails.value = MealDetailsState(error = it.message ?: "")
                    }
                    is Resource.Success -> {

                        _mealDetails.value = MealDetailsState(data = it.data)
                    }
                }
            }
        }

        mealDetailsUseCase(id).onEach {
            when (it) {
                is Resource.Loading -> {
                    _mealDetails.value = MealDetailsState(isLoading = true)
                }
                is Resource.Error -> {
                    _mealDetails.value = MealDetailsState(error = it.message ?: "")
                }
                is Resource.Success -> {

                    Log.d("viewModel"," collecting in viewModel ${it.data}")
                    _mealDetails.value = MealDetailsState(data = it.data)
                }
            }
        }.flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }
}