package de.arjmandi.gymble.domain.model

sealed class SwipeResult {
	object Match : SwipeResult()
	object NoMatch : SwipeResult()
}
