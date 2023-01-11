package com.example.mealsearch.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mealsearch.data.model.MealDTO
import com.example.mealsearch.data.model.MealsDTO
import com.example.mealsearch.data.remote.MealSearchAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MealPagingSource
 @Inject constructor(
private val api: MealSearchAPI,
private val search:String
): PagingSource<Int, MealDTO>()
{

    private val DEFAULT_PAGE_INDEX= 1

    private lateinit var response : MealsDTO

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MealDTO> {
        val page = params.key ?: DEFAULT_PAGE_INDEX

        return try {
            withContext(Dispatchers.IO){
                response = api.getMealList(search , page, params.loadSize)
            }


            LoadResult.Page(
                data = response.meals,
                prevKey = if(page == DEFAULT_PAGE_INDEX) null else page-1,
                nextKey = if(response.meals.isEmpty()) null else page+1
            )
        } catch (exception: IOException){
            LoadResult.Error(exception)
        } catch (exception: HttpException){
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MealDTO>): Int? {

        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}