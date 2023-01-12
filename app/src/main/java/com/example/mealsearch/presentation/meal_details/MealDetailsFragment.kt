package com.example.mealsearch.presentation.meal_details


import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope

import androidx.navigation.fragment.findNavController

import androidx.navigation.fragment.navArgs
import com.example.mealsearch.R
import com.example.mealsearch.databinding.FragmentMealDetailsBinding
import com.google.android.material.animation.AnimationUtils
import com.google.android.material.transition.MaterialContainerTransform
import dagger.hilt.android.AndroidEntryPoint

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

    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMealDetailsBinding.inflate(inflater, container, false)

//        sharedElementEnterTransition =
//            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
//        postponeEnterTransition(100, TimeUnit.MILLISECONDS)
        val transformation = MaterialContainerTransform()
        transformation.interpolator = AnimationUtils.LINEAR_INTERPOLATOR
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = 400
//            scrimColor = Color.TRANSPARENT
        }

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        args.mealId?.let {
            viewModel.getMealDetails(it)
        }


//        Log.d("MainActivity"," args to bundle meal tranisition ${ args.toBundle().getString("image")}")
//        binding.mainImage.transitionName = args.toBundle().getString("image")

//        binding.title.transitionName = args.toBundle().getString("title")
//        Log.d("MainActivity"," args to bundle title tranisition ${ args.toBundle().getString("title")}")

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

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}