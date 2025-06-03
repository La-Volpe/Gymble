package de.arjmandi.gymble.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import de.arjmandi.gymble.domain.model.Gym
import de.arjmandi.gymble.feature.matching.model.GymCardUiState
import de.arjmandi.gymble.feature.matching.ui.GymCard
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
				GymCardPreview()

			}
		}
	}
}

//@Preview(showBackground = true)
@Composable
fun GymCardPreview() {
	val gymCardUiState = GymCardUiState(
		imageUrl = "https://res.cloudinary.com/dlgtohpdw/image/upload/v1748305418/2_geralt_e3xmx7.webp",
		title = "Geralt’s Gym",
		subtitle = "No excuses. Just contracts.",
		description = "SwordFit & Potion Conditioning",
		vibe = listOf("⚔️ Intense", "🤫 Silent"),
		quote = "\"Hmm.\""
	)
	GymCard(gymCardUiState = gymCardUiState)
}
