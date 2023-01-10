package com.example.mealsearch

import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MainActivityTest{

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp(){
        scenario = launch(MainActivity::class.java)
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun recyclerView_isVisibile(){

        onView(ViewMatchers.withId(R.id.progress_bar))
            .check(ViewAssertions.matches(isDisplayed()))

        // Attempt to scroll to an item that contains the special text.
//        onView(ViewMatchers.withId(R.id.recycler_view))

//            // scrollTo will fail the test if no item matches.
//            .perform(
//                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2 ,
//                    ViewActions.click()
//                )
//            )
    }
}