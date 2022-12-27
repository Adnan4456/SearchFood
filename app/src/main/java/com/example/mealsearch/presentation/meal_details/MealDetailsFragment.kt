package com.example.mealsearch.presentation.meal_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle

import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController

import androidx.navigation.fragment.navArgs
import com.example.mealsearch.databinding.FragmentMealDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MealDetailsFragment : Fragment() {

    private var _binding: FragmentMealDetailsBinding? = null
    val binding: FragmentMealDetailsBinding
        get() = _binding!!

    private val viewModel: MealDetailsViewModel by viewModels()

    private val args: MealDetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMealDetailsBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        args.mealId?.let {
            viewModel.getMealDetails(it)
        }

        lifecycleScope.launchWhenCreated {
            viewModel.mealDetails.collect{
                if (it.isLoading){

                }
                if (it.error.isNotBlank()){
                    Toast.makeText(requireContext(),"${it.error}",Toast.LENGTH_LONG).show()
                }
                it.data?.let {
                    binding.mealDetails = it
                }
            }
        }

//         lifecycleScope.launch{
//             repeatOnLifecycle(Lifecycle.State.STARTED){
//                 viewModel.mealDetails.collect{
//
//                 }
//             }
//         }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}