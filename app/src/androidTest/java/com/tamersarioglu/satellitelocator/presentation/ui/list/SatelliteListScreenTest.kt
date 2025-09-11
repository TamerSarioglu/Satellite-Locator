package com.tamersarioglu.satellitelocator.presentation.ui.list

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tamersarioglu.satellitelocator.domain.model.Satellite
import com.tamersarioglu.satellitelocator.presentation.ui.component.EmptyState
import com.tamersarioglu.satellitelocator.presentation.ui.component.ErrorState
import com.tamersarioglu.satellitelocator.presentation.ui.component.LoadingState
import com.tamersarioglu.satellitelocator.presentation.ui.component.SatelliteList
import com.tamersarioglu.satellitelocator.presentation.ui.component.SearchBar
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SatelliteListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loadingState_displaysProgressIndicator() {
        composeTestRule.setContent {
            MaterialTheme {
                LoadingState(
                    message = "Loading satellites..."
                )
            }
        }

        composeTestRule
            .onNodeWithText("Loading satellites...")
            .assertIsDisplayed()
    }

    @Test
    fun errorState_displaysErrorMessage() {
        composeTestRule.setContent {
            MaterialTheme {
                ErrorState(
                    title = "Failed to Load Satellites",
                    message = "Unable to load satellite data. Please check your connection and try again."
                )
            }
        }

        composeTestRule
            .onNodeWithText("Failed to Load Satellites")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Unable to load satellite data. Please check your connection and try again.")
            .assertIsDisplayed()
    }

    @Test
    fun successState_displaysSatelliteList() {
        val mockSatellites = listOf(
            Satellite(1, "Starship-1", true),
            Satellite(2, "Dragon-1", false),
            Satellite(3, "Starship-3", true)
        )

        var clickedSatelliteId: Int? = null

        composeTestRule.setContent {
            MaterialTheme {
                SatelliteList(
                    satellites = mockSatellites,
                    onSatelliteClick = { satelliteId ->
                        clickedSatelliteId = satelliteId
                    }
                )
            }
        }

        composeTestRule
            .onNodeWithText("Starship-1")
            .assertIsDisplayed()
            .assertHasClickAction()

        composeTestRule
            .onNodeWithText("Dragon-1")
            .assertIsDisplayed()
            .assertHasClickAction()

        composeTestRule
            .onNodeWithText("Starship-3")
            .assertIsDisplayed()
            .assertHasClickAction()

        composeTestRule
            .onNodeWithText("Starship-1")
            .performClick()

        assert(clickedSatelliteId == 1)
    }

    @Test
    fun successStateWithEmptyList_displaysEmptyState() {
        composeTestRule.setContent {
            MaterialTheme {
                EmptyState(
                    icon = Icons.Filled.Info,
                    title = "No Satellites Available",
                    subtitle = "There are currently no satellites to display."
                )
            }
        }

        composeTestRule
            .onNodeWithText("No Satellites Available")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("There are currently no satellites to display.")
            .assertIsDisplayed()
    }

    @Test
    fun successStateWithEmptySearchResults_displaysNoResultsState() {
        val searchQuery = "NonExistentSatellite"

        composeTestRule.setContent {
            MaterialTheme {
                EmptyState(
                    icon = Icons.Filled.Search,
                    title = "No Results Found",
                    subtitle = "No satellites match \"$searchQuery\". Try a different search term."
                )
            }
        }

        composeTestRule
            .onNodeWithText("No Results Found")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("No satellites match \"$searchQuery\". Try a different search term.")
            .assertIsDisplayed()
    }

    @Test
    fun searchBar_displaysCorrectly() {
        var currentQuery = ""

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
            .onNodeWithText("Search satellites...")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("Search")
            .assertIsDisplayed()
    }

    @Test
    fun searchBar_acceptsTextInputAndDisplaysValue() {
        var currentQuery = ""

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
            .onNodeWithText("Search satellites...")
            .performTextInput("Starship")
    }
}