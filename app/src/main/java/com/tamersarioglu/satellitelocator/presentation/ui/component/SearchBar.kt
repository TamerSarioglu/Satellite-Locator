package com.tamersarioglu.satellitelocator.presentation.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
) {
    var localQuery by remember { mutableStateOf(query) }

    LaunchedEffect(localQuery) {
        delay(300)
        if (localQuery != query) {
            onQueryChange(localQuery)
        }
    }

    LaunchedEffect(query) {
        if (query != localQuery) {
            localQuery = query
        }
    }

    OutlinedTextField(
        value = localQuery,
        onValueChange = { localQuery = it },
        modifier = modifier.fillMaxWidth(),
        placeholder = {
            Text(
                text = "Search satellites...",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        trailingIcon = {
            if (localQuery.isNotEmpty()) {
                IconButton(
                    onClick = {
                        localQuery = ""
                        onQueryChange("")
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear search",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        },
        shape = RoundedCornerShape(16.dp),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.3f),
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.1f),
            cursorColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Preview
@Composable
private fun SearchBarPreview() {
    MaterialTheme {
        SearchBar(
            query = "Starship",
            onQueryChange = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview
@Composable
private fun SearchBarEmptyPreview() {
    MaterialTheme {
        SearchBar(
            query = "",
            onQueryChange = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}