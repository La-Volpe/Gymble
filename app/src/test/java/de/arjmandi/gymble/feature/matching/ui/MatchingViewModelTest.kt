package de.arjmandi.gymble.feature.matching.ui

import de.arjmandi.gymble.domain.model.SwipeDirection
import de.arjmandi.gymble.domain.model.SwipeResult
import de.arjmandi.gymble.feature.matching.MatchingContext
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class MatchingViewModelTest {

	private lateinit var viewModel: MatchingViewModel
	private val context: MatchingContext = mockk()

	@Before
	fun setUp() {
		viewModel = MatchingViewModel(context)
	}

	@Test
	fun `test swipe left`() {
		every { context.swipe(SwipeDirection.LEFT) } returns SwipeResult.NoMatch
		viewModel.handleSwipe(SwipeDirection.LEFT)
		verify { context.swipe(SwipeDirection.LEFT) }
	}

	@Test
	fun `test swipe right`() {
		every { context.swipe(SwipeDirection.RIGHT) } returns SwipeResult.Match
		viewModel.handleSwipe(SwipeDirection.RIGHT)
		verify { context.swipe(SwipeDirection.RIGHT) }
	}
}
