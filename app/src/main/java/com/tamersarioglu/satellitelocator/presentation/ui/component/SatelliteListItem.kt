package com.tamersarioglu.satellitelocator.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tamersarioglu.satellitelocator.domain.model.Satellite

@Composable
fun SatelliteListItem(
    satellite: Satellite,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onItemClick(satellite.id) }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(
                        color = if (satellite.isActive) Color.Green else Color.Red
                    )
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = satellite.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = if (satellite.isActive) "Active" else "Passive",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        )
    }
}

@Preview
@Composable
private fun SatelliteListItemPreview() {
    MaterialTheme {
        Column {
            SatelliteListItem(
                satellite = Satellite(1, "Starship-1", true),
                onItemClick = {}
            )
            SatelliteListItem(
                satellite = Satellite(2, "Dragon-1", false),
                onItemClick = {}
            )
        }
    }
}