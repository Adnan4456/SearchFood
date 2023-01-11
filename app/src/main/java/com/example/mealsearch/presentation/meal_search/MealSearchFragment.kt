package com.example.mealsearch.presentation.meal_search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.mealsearch.databinding.FragmentMealSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MealSearchFragment : Fragment() {

    private var _binding: FragmentMealSearchBinding? =null
    private val binding:FragmentMealSearchBinding
    get() = _binding!!

    private val mealSearchViewModel:MealSearchViewModel by viewModels()

//    @Inject
//    lateinit var searchAdapter:MealSearchAdapter

    lateinit var pagingAdapter: MealPagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMealSearchBinding.inflate(inflater, container,false)

        pagingAdapter = MealPagingAdapter(MealPagingAdapter.OnClickListener{
            val action = MealSearchFragmentDirections.actionMealSearchFragmentToMealDetailsFragment(
                mealId = it.idMeal)
            findNavController().navigate(action)
        })

        mealSearchViewModel.searchMealList("chicken")

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.searchMeal.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                query?.let {
                    lifecycleScope.launch {
                        repeatOnLifecycle(Lifecycle.State.STARTED){

                            mealSearchViewModel.searchMealList(it).collectLatest{ loadStates ->
                                binding.recyclerView.visibility = View.VISIBLE
                                binding.progressBar.visibility = View.GONE
                                pagingAdapter.submitData(loadStates)
                            }
                        }
                    }
                }
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return false
            }

        })
        binding.recyclerView.apply {
            adapter = pagingAdapter
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){


        /*
                mealSearchViewModel.mealSearchList.collect{

                    if (it.isLoading){
                        binding.progressBar.visibility = View.VISIBLE
                        Log.d("TAG","Loading")
                    }
                    if (it.error.isNotBlank()){
                        binding.progressBar.visibility = View.GONE
                        Log.d("TAG","Error")
                    }
                    if (it.data != null){
                        Log.d("TAG","getting data")
                        binding.progressBar.visibility = View.GONE

                        it.data.let {
//                         searchAdapter.setContentList(it.toMutableList())
                            pagingAdapter.submitData(it)

                        }
                    }
                }
                */
            }
        }
    }

}