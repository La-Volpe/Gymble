package de.arjmandi.gymble.feature.matching.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import de.arjmandi.gymble.feature.matching.model.MatchedResultUiState
import de.arjmandi.gymble.feature.matching.model.MatchingUiState.ErrorState
import de.arjmandi.gymble.feature.matching.model.MatchingUiState.LoadedState
import de.arjmandi.gymble.feature.matching.model.MatchingUiState.LoadingState
import de.arjmandi.gymble.feature.matching.model.toGymCardUiState

@Composable
fun MatchingScreen(viewModel: MatchingViewModel) {
	val uiState by viewModel.uiState.collectAsState()
	val matchResultState by viewModel.matchEvent.collectAsState()
	val matchedUiState by viewModel.matchedGymUiState.collectAsState()
	var gymCards by remember { mutableStateOf(emptyList<GymCardUiState>()) }
	var matchedGym by remember { mutableStateOf<MatchedResultUiState>(MatchedResultUiState.NoMatchState) }

	LaunchedEffect(Unit) {
		viewModel.loadGyms()
	}

	LaunchedEffect(uiState) {
		if (uiState is LoadedState) {
			gymCards = (uiState as LoadedState).gyms.map { it.toGymCardUiState() }
		}
	}
	LaunchedEffect(matchedUiState){
		matchedGym = when (matchResultState) {
			SwipeResult.Match -> matchedUiState
			SwipeResult.NoMatch -> MatchedResultUiState.NoMatchState
		}
	}
	if (matchedGym is MatchedResultUiState.MatchedState) {
		MatchedGymOverlay(
			gym = (matchedGym as MatchedResultUiState.MatchedState).gym,
			onDismiss = viewModel.onDismissMatchedGymOverlay,
		)
	} else {
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
							viewModel.handleSwipe(direction, swipedCard)
							if (gymCards.isEmpty()) {
								viewModel.restock()
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
	}
}

@Composable
fun MatchedGymOverlay(
	gym: GymCardUiState,
	onDismiss: () -> Unit,
) {
	Box(modifier = Modifier.fillMaxSize().padding(16.dp),
		contentAlignment = Alignment.Center){
		Column{
			Text(
				text = "Matched with ${gym.title}!",
				style = MaterialTheme.typography.headlineMedium,
				modifier = Modifier.padding(bottom = 16.dp),
			)
			Text(
				text = "Swipe right to connect or left to pass.",
				modifier = Modifier.padding(bottom = 32.dp),
			)
			Button(onClick = onDismiss) {
				Text("Dismiss")
			}
		}
	}

}
