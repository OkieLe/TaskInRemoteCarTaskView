package io.github.ole.taskintaskview

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollCompletelyTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasCategories
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.isInternal
import androidx.test.espresso.intent.rule.IntentsRule
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MinusOneTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MinusOne::class.java)
    @get:Rule
    val intentsRule = IntentsRule()

    @Test
    fun contentDisplayed() {
        onView(withText("Start Settings".uppercase())).check(matches(isDisplayed()))
        onView(withText("Start Another".uppercase())).check(matches(isDisplayed()))
        onView(withText(R.string.sample_p1)).check(matches(isDisplayed()))
        onView(withText(R.string.sample_p2)).check(matches(isDisplayed()))
        onView(withText(R.string.sample_p3)).perform(scrollCompletelyTo())
        onView(withText(R.string.sample_p3)).check(matches(isDisplayed()))
        onView(withText(R.string.sample_p4)).perform(scrollCompletelyTo())
        onView(withText(R.string.sample_p4)).check(matches(isDisplayed()))
        onView(withText(R.string.sample_p5)).perform(scrollCompletelyTo())
        onView(withText(R.string.sample_p5)).check(matches(isDisplayed()))
    }

    @Test
    fun startExternalWorks() {
        Intents.intending(not(isInternal())).respondWith(
            Instrumentation.ActivityResult(Activity.RESULT_OK, Intent())
        )
        onView(withId(R.id.start_external)).perform(click())
        Intents.intended(
            allOf(
                hasAction(Intent.ACTION_MAIN),
                hasCategories(setOf(Intent.CATEGORY_LAUNCHER)),
                hasComponent("com.android.settings.Settings")
            )
        )
    }

    @Test
    fun startAnotherWorks() {
        onView(withId(R.id.start_another)).perform(click())
        Intents.intended(
            allOf(
                hasAction(Intent.ACTION_MAIN),
                hasCategories(setOf(Intent.CATEGORY_DEFAULT)),
                hasComponent("io.github.ole.taskintaskview.MinusTwo")
            )
        )
        onView(withId(R.id.back)).check(matches(isDisplayed()))
    }
}
