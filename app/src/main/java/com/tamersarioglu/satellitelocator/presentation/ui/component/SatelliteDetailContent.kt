package com.tamersarioglu.satellitelocator.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tamersarioglu.satellitelocator.domain.model.Position
import com.tamersarioglu.satellitelocator.domain.model.Satellite
import com.tamersarioglu.satellitelocator.domain.model.SatelliteDetail
import java.time.LocalDate

@Composable
fun SatelliteDetailContent(
    modifier: Modifier = Modifier,
    satellite: Satellite,
    satelliteDetail: SatelliteDetail,
    currentPosition: Position?
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SatelliteStatusIndicator(
            name = satellite.name,
            isActive = satellite.isActive
        )

        if (currentPosition != null) {
            PositionDisplay(position = currentPosition)
        }
        SatelliteDetailCard(satelliteDetail = satelliteDetail)
    }
}

@Preview
@Composable
private fun SatelliteDetailContentPreview() {
    MaterialTheme {
        SatelliteDetailContent(
            satellite = Satellite(
                id = 1,
                name = "Starship-1",
                isActive = true
            ),
            satelliteDetail = SatelliteDetail(
                id = 1,
                costPerLaunch = 7200000,
                firstFlight = LocalDate.of(2018, 2, 6),
                height = 70,
                mass = 1420000
            ),
            currentPosition = Position(
                x = 0.864328541,
                y = 0.646450359
            )
        )
    }
}