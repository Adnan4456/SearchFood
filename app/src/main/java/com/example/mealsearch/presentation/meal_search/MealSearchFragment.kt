package com.example.mealsearch.presentation.meal_search

import android.animation.AnimatorListenerAdapter
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mealsearch.R
import com.example.mealsearch.common.SpacesItemDecoration
import com.example.mealsearch.databinding.FragmentMealSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.doOnPreDraw
import androidx.transition.TransitionInflater
import com.google.android.material.transition.MaterialElevationScale


@AndroidEntryPoint
class MealSearchFragment : Fragment() {

    private var _binding: FragmentMealSearchBinding? =null
    private val binding:FragmentMealSearchBinding
    get() = _binding!!


    private val mealSearchViewModel:MealSearchViewModel by viewModels()

    lateinit var pagingAdapter: MealPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMealSearchBinding.inflate(inflater, container,false)

//        sharedElementReturnTransition =
//            context?.let { TransitionInflater.from(it).inflateTransition(android.R.transition.move) }

        pagingAdapter = MealPagingAdapter(MealPagingAdapter.OnClickListener{meal , image ,title ->

            val extras = FragmentNavigatorExtras(

            image to "imageView",
            title to "title")

            val action = MealSearchFragmentDirections
                .actionMealSearchFragmentToMealDetailsFragment(
                mealId = meal.idMeal)
            findNavController().navigate(action,extras)

//            exitTransition = MaterialElevationScale(false).apply {
//                duration = 200
//            }
//            reenterTransition = MaterialElevationScale(true).apply {
//                duration = 200
//            }

        })


        mealSearchViewModel.searchQuery.value?.let{
            searchMeal(it)
        }

        binding.recyclerView.apply {
            hasFixedSize()
            addItemDecoration(SpacesItemDecoration())
            adapter = pagingAdapter
            postponeEnterTransition()
            viewTreeObserver
                .addOnPreDrawListener {
                    startPostponedEnterTransition()
                    true
                }
        }

        binding.layoutChanger.setOnClickListener{
            createMenu()
        }

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // When user hits back button transition takes backward
        postponeEnterTransition()
        binding.recyclerView.doOnPreDraw {
            startPostponedEnterTransition()
        }
        binding.searchMeal.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                query?.let {
                  searchMeal(query)
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return false
            }

        })
        binding.recyclerView.apply {
            adapter = pagingAdapter
        }
    }

    private fun searchMeal(query: String){

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mealSearchViewModel.searchMealList(query).collectLatest {
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    pagingAdapter.submitData(it)
                }
            }
        }
    }

    private fun createMenu(){
        var popupMenu = PopupMenu(requireContext() , binding.layoutChanger)
        popupMenu.menuInflater.inflate(R.menu.mymenu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener(object :PopupMenu.OnMenuItemClickListener{
            override fun onMenuItemClick(item: MenuItem?): Boolean {

                when(item?.itemId){
                    R.id.linear ->{
                        (binding.recyclerView.layoutManager as StaggeredGridLayoutManager).spanCount = 1
                        binding.recyclerView.adapter?.apply {
                            notifyDataSetChanged()
                        }
                    }
                    R.id.GridLayout ->{
                        (binding.recyclerView.layoutManager as StaggeredGridLayoutManager).spanCount = 2
                        binding.recyclerView.adapter?.apply {
                            notifyDataSetChanged()
                        }
                    }
                }

                return true
            }

        })
        popupMenu.show()
    }

    /*
    fun circleReveal(viewID: Int, posFromRight: Int, containsOverflow: Boolean, isShow: Boolean) {
//        val myView: View = findViewById(viewID)
//        val myView = binding.customSearchtoolbar.searchtoolbar

        var width = myView.width
        if (posFromRight > 0) width -= posFromRight * resources.getDimensionPixelSize( androidx.appcompat.R.dimen.abc_action_button_min_width_material) - resources.getDimensionPixelSize(
            androidx.appcompat.R.dimen.abc_action_button_min_width_material
        ) / 2
        if (containsOverflow) width -= resources.getDimensionPixelSize( androidx.appcompat.R.dimen.abc_action_button_min_width_overflow_material)
        val cx = width
        val cy = myView.height / 2
        val anim: Animator
        anim =
            if (isShow) ViewAnimationUtils.createCircularReveal(
                myView,
                cx,
                cy,
                0f,
                width.toFloat()
            ) else ViewAnimationUtils.createCircularReveal(myView, cx, cy, width.toFloat(), 0f)
        anim.setDuration(220L)

        // make the view invisible when the animation is done
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                if (!isShow) {
                    super.onAnimationEnd(animation)
                    myView.visibility = View.INVISIBLE
                }
            }
        })

        // make the view visible and start the animation
        if (isShow) myView.visibility = View.VISIBLE

        // start the animation
        anim.start()
    }
     */
}