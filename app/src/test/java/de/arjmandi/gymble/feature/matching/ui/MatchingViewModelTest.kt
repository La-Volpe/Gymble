package de.arjmandi.gymble.feature.matching.ui

import de.arjmandi.gymble.domain.model.SwipeDirection
import de.arjmandi.gymble.domain.model.SwipeResult
import de.arjmandi.gymble.feature.matching.MatchingContext
import de.arjmandi.gymble.feature.matching.model.GymCardUiState
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class MatchingViewModelTest {

	private lateinit var viewModel: MatchingViewModel
	private val context: MatchingContext = mockk()

	val matchedGym = GymCardUiState(
		imageUrl = "https://res.cloudinary.com/dlgtohpdw/image/upload/v1748305418/2_geralt_e3xmx7.webp",
		title = "Geralt’s Gym",
		subtitle = "No excuses. Just contracts.",
		description = "SwordFit & Potion Conditioning",
		vibe = listOf("⚔️ Intense", "🤫 Silent"),
		quote = "\"Hmm.\"",
		isMatched = true
	)

	val swipedGym = GymCardUiState(
		imageUrl = "https://res.cloudinary.com/dlgtohpdw/image/upload/v1748305414/1_yennefer_t4dur3.webp",
		title = "Yennefer’s Studio",
		subtitle = "Power. Poise. And push.",
		description = "Mystic Strength & Arcane Yoga",
		vibe = listOf("🔮 Elegant", "🧘‍♀️ Intimidating"),
		quote = "Magic won't fix your form. I will.",
		isMatched = false
	)

	@Before
	fun setUp() {
		viewModel = MatchingViewModel(context)
	}

	@Test
	fun `test swipe left`() {
		every { context.swipe(SwipeDirection.LEFT) } returns SwipeResult.NoMatch
		viewModel.handleSwipe(SwipeDirection.LEFT, swipedGym)
		verify { context.swipe(SwipeDirection.LEFT) }
	}

	@Test
	fun `test swipe right`() {
		every { context.swipe(SwipeDirection.RIGHT) } returns SwipeResult.Match
		viewModel.handleSwipe(SwipeDirection.RIGHT, matchedGym)
		verify { context.swipe(SwipeDirection.RIGHT) }
	}
}
