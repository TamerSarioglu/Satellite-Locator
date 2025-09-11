package com.tamersarioglu.satellitelocator.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SatelliteStatusIndicator(
    modifier: Modifier = Modifier,
    name: String,
    isActive: Boolean,
) {
    val statusColor = if (isActive) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.error
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .clip(CircleShape)
                .background(color = statusColor)
        )

        Text(
            text = name,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
private fun SatelliteStatusIndicatorPreview() {
    MaterialTheme {
        SatelliteStatusIndicator(
            name = "Starship-1",
            isActive = true
        )
    }
}

@Preview
@Composable
private fun SatelliteStatusIndicatorInactivePreview() {
    MaterialTheme {
        SatelliteStatusIndicator(
            name = "Starship-1",
            isActive = false
        )
    }
}