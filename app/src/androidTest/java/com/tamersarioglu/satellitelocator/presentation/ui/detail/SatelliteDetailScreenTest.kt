package com.tamersarioglu.satellitelocator.presentation.ui.detail

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tamersarioglu.satellitelocator.domain.model.Position
import com.tamersarioglu.satellitelocator.domain.model.Satellite
import com.tamersarioglu.satellitelocator.domain.model.SatelliteDetail
import com.tamersarioglu.satellitelocator.presentation.ui.component.ErrorState
import com.tamersarioglu.satellitelocator.presentation.ui.component.LoadingState
import com.tamersarioglu.satellitelocator.presentation.ui.component.SatelliteDetailContent
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class SatelliteDetailScreenTest {

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
                    message = "Loading satellite details..."
                )
            }
        }

        composeTestRule
            .onNodeWithText("Loading satellite details...")
            .assertIsDisplayed()
    }

    @Test
    fun successState_displaysSatelliteDetails() {
        val mockSatellite = Satellite(1, "Starship-1", true)
        val mockSatelliteDetail = SatelliteDetail(
            id = 1,
            costPerLaunch = 7200000,
            firstFlight = LocalDate.parse("2021-12-01"),
            height = 118,
            mass = 1167000
        )
        val mockPosition = Position(0.123456, 0.654321)

        composeTestRule.setContent {
            MaterialTheme {
                SatelliteDetailContent(
                    satellite = mockSatellite,
                    satelliteDetail = mockSatelliteDetail,
                    currentPosition = mockPosition
                )
            }
        }

        composeTestRule
            .onNodeWithText("Starship-1")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Height/Mass")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("118/1167000")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Cost")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("7200000")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Last Position")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("(0.123456,0.654321)")
            .assertIsDisplayed()
    }

    @Test
    fun errorState_displaysErrorMessage() {
        var onRetryClicked = false

        composeTestRule.setContent {
            MaterialTheme {
                ErrorState(
                    title = "Failed to Load",
                    message = "Failed to load satellite details. Please try again."
                )
            }
        }

        composeTestRule
            .onNodeWithText("Failed to Load")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Failed to load satellite details. Please try again.")
            .assertIsDisplayed()
    }
}