package com.tamersarioglu.satellitelocator.presentation.ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class SearchBarTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun searchBar_displaysAllElements() {
        composeTestRule.setContent {
            MaterialTheme {
                SearchBar(
                    query = "",
                    onQueryChange = { }
                )
            }
        }

        composeTestRule
            .onNodeWithText("Search satellites...")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("Search")
            .assertIsDisplayed()
    }

    @Test
    fun searchBar_acceptsTextInput() {
        var capturedQuery = ""

        composeTestRule.setContent {
            MaterialTheme {
                SearchBar(
                    query = "",
                    onQueryChange = { query ->
                        capturedQuery = query
                    }
                )
            }
        }

        composeTestRule
            .onNode(hasSetTextAction())
            .performTextInput("Starship")

        composeTestRule
            .onNodeWithText("Starship")
            .assertIsDisplayed()
    }

    @Test
    fun searchBar_showsClearButtonWhenQueryNotEmpty() {
        var currentQuery = "test query"

        composeTestRule.setContent {
            MaterialTheme {
                SearchBar(
                    query = currentQuery,
                    onQueryChange = { query ->
                        currentQuery = query
                    }
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription("Clear search")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("Clear search")
            .performClick()
    }
}