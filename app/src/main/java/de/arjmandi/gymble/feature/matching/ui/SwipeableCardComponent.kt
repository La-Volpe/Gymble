package de.arjmandi.gymble.feature.matching.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import de.arjmandi.gymble.domain.model.SwipeDirection
import de.arjmandi.gymble.feature.matching.model.GymCardUiState

enum class _SwipeDirection {
	Left, Center, Right
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SwipeableCard(
	gymCardUiState: GymCardUiState,
	onSwiped: (SwipeDirection) -> Unit,
) {
	val density = LocalDensity.current
	val swipeThreshold = with(density) { 300.dp.toPx() }

	val anchors = DraggableAnchors {
		_SwipeDirection.Left at -swipeThreshold
		_SwipeDirection.Center at 0f
		_SwipeDirection.Right at swipeThreshold
	}

	val swipeState = remember {
		AnchoredDraggableState(
			initialValue = _SwipeDirection.Center,
			anchors = anchors,
		)
	}

	LaunchedEffect(Unit) {
		swipeState.updateAnchors(anchors)
	}

	LaunchedEffect(swipeState.currentValue) {
		when (swipeState.currentValue) {
			_SwipeDirection.Left -> onSwiped(SwipeDirection.LEFT)
			_SwipeDirection.Right -> onSwiped(SwipeDirection.RIGHT)
			else -> {
				// Center, do nothing
			}
		}
	}

	val offsetX = swipeState.offset
	val alpha by remember {
		derivedStateOf {
			val offsetAbs = kotlin.math.abs(offsetX)
			1f - (offsetAbs / swipeThreshold).coerceIn(0f, 1f)
		}
	}

	Box(
		modifier = Modifier
			.fillMaxWidth()
			.testTag("SwipeableCard")
			.graphicsLayer {
				translationX = offsetX
				this.alpha = alpha
			}
			.anchoredDraggable(
				state = swipeState,
				orientation = Orientation.Horizontal,
			),
	) {
		GymCard(gymCardUiState = gymCardUiState)
	}
}

@Composable
fun GymCardStack(
	gymCards: List<GymCardUiState>,
	onCardSwiped: (GymCardUiState, SwipeDirection) -> Unit,
) {
	Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
		gymCards.asReversed().forEach { card ->
			SwipeableCard(
				gymCardUiState = card,
				onSwiped = { swipeDirection ->
					when (swipeDirection) {
						SwipeDirection.LEFT -> onCardSwiped(card, SwipeDirection.LEFT)
						SwipeDirection.RIGHT -> onCardSwiped(card, SwipeDirection.RIGHT)
					}
				},
			)
		}
	}
}
