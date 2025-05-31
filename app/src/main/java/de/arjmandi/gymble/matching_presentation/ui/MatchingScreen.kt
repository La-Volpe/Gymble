package de.arjmandi.gymble.matching_presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.arjmandi.gymble.domain.model.SwipeDirection
import de.arjmandi.gymble.matching_presentation.model.MatchingEvent
import de.arjmandi.gymble.matching_presentation.model.MatchingUiState

@Composable
fun MatchingScreen(state: MatchingUiState, onEvent: (MatchingEvent) -> Unit) {
    LaunchedEffect(Unit) {
        onEvent(MatchingEvent.LoadGyms)
    }

    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        if (state.isLoading) {
            CircularProgressIndicator()
        } else if (state.error != null) {
            Text("Error: ${state.error}")
        } else if (state.gyms.isNotEmpty()) {
            val currentGym = state.gyms[state.currentIndex]

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = currentGym.name, style = MaterialTheme.typography.headlineMedium)
                Text(text = currentGym.slogan)
                Text(text = currentGym.specialty)
                Text(text = currentGym.quote)
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Button(onClick = { onEvent(MatchingEvent.Swipe(direction = SwipeDirection.LEFT)) }) {
                        Text("Dislike")
                    }
                    Button(onClick = { onEvent(MatchingEvent.Swipe(direction = SwipeDirection.RIGHT)) }) {
                        Text("Like")
                    }
                }
            }
        }
    }
}