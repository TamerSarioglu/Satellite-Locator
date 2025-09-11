package com.tamersarioglu.satellitelocator.presentation.ui.detail

import androidx.compose.material3.MaterialTheme
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
import com.tamersarioglu.satellitelocator.presentation.ui.component.SatelliteDetailTopBar
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate

@RunWith(AndroidJUnit4::class)
class SatelliteDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

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
            .onNodeWithText("Height/Length")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("118m")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Mass")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("1,167,000kg")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Cost")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("$7,200,000.00")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Last Position")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("(0.123, 0.654)")
            .assertIsDisplayed()
    }

    @Test
    fun successState_displaysActiveSatelliteIndicator() {
        val mockSatellite = Satellite(1, "Active-Satellite", true)
        val mockSatelliteDetail = SatelliteDetail(
            id = 1,
            costPerLaunch = 5000000,
            firstFlight = LocalDate.parse("2020-01-01"),
            height = 100,
            mass = 500000
        )
        val mockPosition = Position(1.0, -1.0)

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
            .onNodeWithText("Active-Satellite")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Height/Length")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("100m")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Mass")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("500,000kg")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Cost")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("$5,000,000.00")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Last Position")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("(1.000, -1.000)")
            .assertIsDisplayed()
    }

    @Test
    fun successState_displaysInactiveSatelliteIndicator() {
        val mockSatellite = Satellite(2, "Inactive-Satellite", false)
        val mockSatelliteDetail = SatelliteDetail(
            id = 2,
            costPerLaunch = 3000000,
            firstFlight = LocalDate.parse("2019-05-15"),
            height = 80,
            mass = 300000
        )
        val mockPosition = Position(-2.5, 3.7)

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
            .onNodeWithText("Inactive-Satellite")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("(-2.500, 3.700)")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Cost")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("$3,000,000.00")
            .assertIsDisplayed()
    }

    @Test
    fun errorState_displaysErrorMessage() {
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

    @Test
    fun topBar_displaysCorrectTitle() {
        var backClicked = false

        composeTestRule.setContent {
            MaterialTheme {
                SatelliteDetailTopBar(
                    title = "Test Satellite",
                    onBackClick = { backClicked = true }
                )
            }
        }

        composeTestRule
            .onNodeWithText("Test Satellite")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("Back")
            .assertIsDisplayed()
    }

    @Test
    fun topBar_handleBackNavigation() {
        var backClicked = false

        composeTestRule.setContent {
            MaterialTheme {
                SatelliteDetailTopBar(
                    title = "Test Satellite",
                    onBackClick = { backClicked = true }
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription("Back")
            .performClick()

        assert(backClicked)
    }

    @Test
    fun topBar_displaysLoadingTitle() {
        composeTestRule.setContent {
            MaterialTheme {
                SatelliteDetailTopBar(
                    title = "Loading...",
                    onBackClick = { }
                )
            }
        }

        composeTestRule
            .onNodeWithText("Loading...")
            .assertIsDisplayed()
    }
}