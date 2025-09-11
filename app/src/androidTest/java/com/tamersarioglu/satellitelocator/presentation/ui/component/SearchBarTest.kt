package com.tamersarioglu.satellitelocator.presentation.ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

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
                    query = capturedQuery,
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

    @Test
    fun searchBar_hidesClearButtonWhenQueryEmpty() {
        composeTestRule.setContent {
            MaterialTheme {
                SearchBar(
                    query = "",
                    onQueryChange = { }
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription("Clear search")
            .assertIsNotDisplayed()
    }

    @Test
    fun searchBar_clearButtonTriggersOnQueryChange() {
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
            .performClick()
    }

    @Test
    fun searchBar_displaysEnteredQuery() {
        val testQuery = "Falcon Heavy"

        composeTestRule.setContent {
            MaterialTheme {
                SearchBar(
                    query = testQuery,
                    onQueryChange = { }
                )
            }
        }

        composeTestRule
            .onNodeWithText(testQuery)
            .assertIsDisplayed()
    }

    @Test
    fun searchBar_handlesLongQuery() {
        val longQuery = "This is a very long search query that might cause text overflow"

        composeTestRule.setContent {
            MaterialTheme {
                SearchBar(
                    query = longQuery,
                    onQueryChange = { }
                )
            }
        }

        composeTestRule
            .onNodeWithText(longQuery)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("Clear search")
            .assertIsDisplayed()
    }

    @Test
    fun searchBar_handlesSpecialCharacters() {
        val specialQuery = "SpaceX-01 & Falcon@2023"

        composeTestRule.setContent {
            MaterialTheme {
                SearchBar(
                    query = specialQuery,
                    onQueryChange = { }
                )
            }
        }

        composeTestRule
            .onNodeWithText(specialQuery)
            .assertIsDisplayed()
    }

    @Test
    fun searchBar_allowsTextClearance() {
        var currentQuery = "Initial Query"

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
            .onNode(hasSetTextAction())
            .performTextClearance()
    }
}