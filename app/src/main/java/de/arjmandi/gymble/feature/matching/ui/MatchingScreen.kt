package de.arjmandi.gymble.feature.matching.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.arjmandi.gymble.domain.model.SwipeResult
import de.arjmandi.gymble.feature.matching.model.GymCardUiState
import de.arjmandi.gymble.feature.matching.model.MatchingUiState.ErrorState
import de.arjmandi.gymble.feature.matching.model.MatchingUiState.LoadedState
import de.arjmandi.gymble.feature.matching.model.MatchingUiState.LoadingState
import de.arjmandi.gymble.feature.matching.model.toGymCardUiState

@Composable
fun MatchingScreen(viewModel: MatchingViewModel) {
	val uiState by viewModel.uiState.collectAsState()
	val matchState by viewModel.matchEvent.collectAsState()
	var gymCards by remember { mutableStateOf(emptyList<GymCardUiState>()) }

	LaunchedEffect(Unit) {
		viewModel.loadGyms()
	}

	LaunchedEffect(uiState) {
		if (uiState is LoadedState) {
			gymCards = (uiState as LoadedState).gyms.map { it.toGymCardUiState() }
		}
	}

	Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
		when (uiState) {
			is LoadingState -> {
				CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
			}
			is LoadedState -> {
				GymCardStack(
					gymCards = gymCards,
					onCardSwiped = { swipedCard, direction ->
						gymCards = gymCards - swipedCard
						viewModel.handleSwipe(direction)
						if (gymCards.isEmpty()) {
							viewModel.restock() // Reload gyms when all cards are swiped
						}
					},
				)
			}
			is ErrorState -> {
				Text(
					text = "Something Went wrong...",
					modifier = Modifier.align(Alignment.Center),
				)
			}
		}
	}
	if (matchState is SwipeResult.Match) {
		Log.d("TALA", "Match found!")
	}
}
