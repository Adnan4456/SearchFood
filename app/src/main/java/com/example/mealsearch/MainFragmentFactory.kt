package com.example.mealsearch

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.mealsearch.presentation.meal_details.MealDetailsFragment
import com.example.mealsearch.presentation.meal_search.MealSearchFragment
import javax.inject.Inject

class MainFragmentFactory
@Inject
constructor(
    //we need to pass all dependency that fragments needs
) :FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when(className){
            MealSearchFragment::class.java.name ->{
                MealSearchFragment()
            }
            MealDetailsFragment::class.java.name ->{
                MealDetailsFragment()
            }
            else -> super.instantiate(classLoader, className)
        }
    }



}