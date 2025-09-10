package com.tamersarioglu.satellitelocator.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tamersarioglu.satellitelocator.domain.model.SatelliteDetail
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun SatelliteDetailCard(
    satelliteDetail: SatelliteDetail,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DetailRow(
                label = "Height/Length",
                value = "${satelliteDetail.height}m"
            )

            DetailRow(
                label = "Mass",
                value = "${NumberFormat.getNumberInstance().format(satelliteDetail.mass)}kg"
            )

            DetailRow(
                label = "Cost",
                value = NumberFormat.getCurrencyInstance(Locale.US)
                    .format(satelliteDetail.costPerLaunch)
            )

            DetailRow(
                label = "First Flight",
                value = satelliteDetail.firstFlight.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            )
        }
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Normal
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
private fun SatelliteDetailCardPreview() {
    MaterialTheme {
        SatelliteDetailCard(
            satelliteDetail = SatelliteDetail(
                id = 1,
                costPerLaunch = 7200000,
                firstFlight = LocalDate.of(2021, 12, 1),
                height = 118,
                mass = 1167000
            ),
            modifier = Modifier.padding(16.dp)
        )
    }
}