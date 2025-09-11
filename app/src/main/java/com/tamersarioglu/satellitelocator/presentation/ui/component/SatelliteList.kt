package com.tamersarioglu.satellitelocator.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tamersarioglu.satellitelocator.domain.model.Satellite

@Composable
fun SatelliteList(
    modifier: Modifier = Modifier,
    satellites: List<Satellite>,
    onSatelliteClick: (Int) -> Unit,
    contentPadding: PaddingValues = PaddingValues(bottom = 16.dp)
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(
            items = satellites,
            key = { satellite -> satellite.id }
        ) { satellite ->
            SatelliteListItem(
                satellite = satellite,
                onItemClick = onSatelliteClick
            )
        }
    }
}

@Preview
@Composable
private fun SatelliteListPreview() {
    val sampleSatellites = listOf(
        Satellite(1, "Starship-1", true),
        Satellite(2, "Dragon-1", false),
        Satellite(3, "Falcon-Heavy", true),
        Satellite(4, "Crew Dragon", false),
        Satellite(5, "Starlink-1", true),
        Satellite(6, "Starlink-2", true)
    )

    SatelliteList(
        satellites = sampleSatellites,
        onSatelliteClick = {}
    )
}