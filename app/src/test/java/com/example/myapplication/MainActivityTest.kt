package com.example.myapplication

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Before

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule

    private lateinit var activity: MainActivity;
    private lateinit var activityScenario: ActivityScenario<MainActivity>;



    @Before
    fun setup() {
        activityScenario = ActivityScenario.launch(MainActivity::class.java)
        activityScenario.onActivity { mainActivity ->
            activity = mainActivity
        }

    }

    @Test
    fun testViewNotNull() {
        assertNotNull(activity);
        assertNotNull(activity.actionBar);
        assertNotNull(activity.assets)
    }
}
