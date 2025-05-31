package de.arjmandi.gymble.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import de.arjmandi.gymble.feature.matching.ui.MatchingScreen
import de.arjmandi.gymble.feature.matching.ui.MatchingViewModel
import de.arjmandi.gymble.ui.theme.GymbleTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
	private val matchingViewModel: MatchingViewModel by viewModel()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		enableEdgeToEdge()
		setContent {
			GymbleTheme {
				val state = matchingViewModel.stateModel.state
				MatchingScreen(
					state = state,
					onEvent = matchingViewModel::onEvent,
				)
			}
		}
	}
}
