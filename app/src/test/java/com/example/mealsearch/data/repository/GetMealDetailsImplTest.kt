package com.example.mealsearch.data.repository

import com.example.mealsearch.data.model.MealDTO
import com.example.mealsearch.data.model.MealsDTO
import com.example.mealsearch.data.remote.MealSearchAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class GetMealDetailsImplTest{

    private var mealObj   = MealDTO(
        "dateModified",
        "idMeal",
        "strArea",
        "strCategory" ,
        "strCreativeCommonsConfirmed",
        "strDrinkAlternate",
        "strImageSource",
        "strIngredient1",
        "strIngredient10",
        "strIngredient11",
        "strIngredient12",
        "strIngredient13",
        "strIngredient14",
        "strIngredient15",
        "strIngredient16",
        "strIngredient17",
        "strIngredient18",
        "strIngredient19",
        "strIngredient2",
        "strIngredient20",
        "strIngredient3",
        "strIngredient4",
        "strIngredient5",
        "strIngredient6",
        "strIngredient7",
        "strIngredient8",
        "strIngredient9",
        "strInstructions",
        "strMeal",
        "strMealThumb",
        "strMeasure1",
        "strMeasure10",
        "strMeasure11",
        "strMeasure12",
        "strMeasure13",
        "strMeasure14",
        "strMeasure15",
        "strMeasure16",
        "strMeasure17: String",
        " strMeasure18: String",
        " strMeasure19: String",
        "strMeasure2",
        "strMeasure20",
        "strMeasure3: String",
        "strMeasure4: String",
        "strMeasure5: String",
        "strMeasure6: String",
        "strMeasure7: String",
        "strMeasure8: String",
        "strMeasure9: String",
        "strSource: String",
        "strTags: String",
        "strYoutube: String"
    )
    private val list = listOf(
        mealObj
    )

    private var mealsDTO = MealsDTO(list)

    private var emptyMealsDTO = MealsDTO(emptyList())

    @Mock
    lateinit var mealSearchAPI: MealSearchAPI

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun testGetMealList() = runTest{

        Mockito.`when`(mealSearchAPI.getMealDetails("chicken"))
            .thenReturn(mealsDTO)

        val result = mealSearchAPI.getMealDetails("chicken")

        testDispatcher.scheduler.advanceUntilIdle()

//            Now compare the size of list that is 1
        assertEquals(1 , result.meals.size)

        //Check that MealId
        assertEquals("idMeal" , result.meals.get(0).idMeal)
    }
    @Test
    fun testGetEmptyMealList() = runTest{

        Mockito.`when`(mealSearchAPI.getMealDetails("chicken"))
            .thenReturn(emptyMealsDTO)

        val result = mealSearchAPI.getMealDetails("chicken")
//           Now compare the size of list that is 0.because i am mocking empty list
        assertEquals(0 , result.meals.size)
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
//        mainThreadSurrogate.close()
    }

}