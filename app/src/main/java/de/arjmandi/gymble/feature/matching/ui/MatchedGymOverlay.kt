package de.arjmandi.gymble.feature.matching.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import de.arjmandi.gymble.feature.matching.model.GymCardUiState

@Composable
fun MatchedGymOverlay(
	gym: GymCardUiState,
	onDismiss: () -> Unit,
) {
	var visible by remember { mutableStateOf(true) }

	// You can use this for a bounce or scale effect
	val scale = remember { Animatable(0.8f) }

	LaunchedEffect(Unit) {
		scale.animateTo(
			targetValue = 1f,
			animationSpec = spring(
				stiffness = Spring.StiffnessLow,
				dampingRatio = Spring.DampingRatioMediumBouncy,
			),
		)
	}

	AnimatedVisibility(
		visible = visible,
		enter = fadeIn(animationSpec = tween(500)) + scaleIn(initialScale = 0.8f),
		exit = fadeOut(animationSpec = tween(300)) + scaleOut(targetScale = 0.8f),
	) {
		Box(
			modifier = Modifier
				.fillMaxSize()
				.graphicsLayer {
					scaleX = scale.value
					scaleY = scale.value
				},
			contentAlignment = Alignment.Center,
		) {
			Column {
				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.Center,
				) {
					Text("You matched with a gym!", modifier = Modifier.padding(bottom = 16.dp))
				}
				GymCard(gym)
				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.Center,
				) {
					Button(
						onClick = {
							visible = false
							onDismiss()
						},
					) {
						Text(text = "Awesome! Let's go to ${gym.title}!")
					}
				}
			}
		}
	}
}
