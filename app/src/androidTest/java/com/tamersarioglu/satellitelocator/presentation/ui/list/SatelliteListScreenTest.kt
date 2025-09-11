package com.tamersarioglu.satellitelocator.presentation.ui.list

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
import com.tamersarioglu.satellitelocator.presentation.ui.component.LoadingState
import com.tamersarioglu.satellitelocator.presentation.ui.component.SatelliteList
import com.tamersarioglu.satellitelocator.presentation.ui.component.SearchBar
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class SatelliteListScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        hiltRule.inject()
    }

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
    fun searchBar_filtersResults() {
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

        composeTestRule
            .onNodeWithText("Search satellites...")
            .performTextInput("Starship")
    }
}