package de.arjmandi.gymble.ui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import androidx.compose.ui.test.swipeRight
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.arjmandi.gymble.domain.model.SwipeDirection
import de.arjmandi.gymble.feature.matching.model.GymCardUiState
import de.arjmandi.gymble.feature.matching.ui.SwipeableCard
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SwipeableCardTest {

	@get:Rule
	val composeTestRule = createComposeRule()

	private val testCard = GymCardUiState(
		imageUrl = "https://res.cloudinary.com/dlgtohpdw/image/upload/v1748305418/2_geralt_e3xmx7.webp",
		title = "Geralt’s Gym",
		subtitle = "No excuses. Just contracts.",
		description = "SwordFit & Potion Conditioning",
		vibe = listOf("⚔️ Intense", "🤫 Silent"),
		quote = "\"Hmm.\"",
	)

	@Test
	fun swipeLeft_triggersOnSwipedLeft() {
		val onSwiped = mockk<(SwipeDirection) -> Unit>(relaxed = true)

		composeTestRule.setContent {
			SwipeableCard(
				gymCardUiState = testCard,
				onSwiped = onSwiped,
			)
		}

		composeTestRule
			.onNodeWithTag("SwipeableCard")
			.performTouchInput {
				swipeLeft()
			}

		verify { onSwiped(SwipeDirection.LEFT) }
	}

	@Test
	fun swipeRight_triggersOnSwipedRight() {
		val onSwiped = mockk<(SwipeDirection) -> Unit>(relaxed = true)

		composeTestRule.setContent {
			SwipeableCard(
				gymCardUiState = testCard,
				onSwiped = onSwiped,
			)
		}

		composeTestRule
			.onNodeWithTag("SwipeableCard")
			.performTouchInput {
				swipeRight()
			}

		verify { onSwiped(SwipeDirection.RIGHT) }
	}

	@Test
	fun noSwipe_doesNotTriggerCallback() {
		val onSwiped = mockk<(SwipeDirection) -> Unit>(relaxed = true)

		composeTestRule.setContent {
			SwipeableCard(
				gymCardUiState = testCard,
				onSwiped = onSwiped,
			)
		}

		composeTestRule.waitForIdle()

		verify(exactly = 0) { onSwiped(any()) }
	}
}
