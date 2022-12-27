package com.example.mealsearch.presentation.meal_search

import android.os.Bundle
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
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MealSearchFragment : Fragment() {

    private var _binding: FragmentMealSearchBinding? =null
    private val binding:FragmentMealSearchBinding
    get() = _binding!!

    private val mealSearchViewModel:MealSearchViewModel by viewModels()

    @Inject
    lateinit var searchAdapter:MealSearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mealSearchViewModel.searchMealList("chicken")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMealSearchBinding.inflate(inflater, container,false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.searchMeal.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                query?.let {
                    mealSearchViewModel.searchMealList(it)
                }
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return false
            }

        })
        binding.recyclerView.apply {
            adapter = searchAdapter
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                mealSearchViewModel.mealSearchList.collect{

                    if (it.isLoading){
                        binding.progressBar.visibility = View.VISIBLE

                    }
                    if (it.error.isNotBlank()){
                        binding.progressBar.visibility = View.GONE
                    }
                    if (it.data != null){
                        binding.progressBar.visibility = View.GONE

                        it.data.let {
                         searchAdapter.setContentList(it.toMutableList())
                     }
                    }
                }
            }
        }



        //click item listener
        searchAdapter.itemClickListener {
            val action = MealSearchFragmentDirections.actionMealSearchFragmentToMealDetailsFragment(
                mealId = it.mealId)
            findNavController().navigate(action)
        }
    }

}