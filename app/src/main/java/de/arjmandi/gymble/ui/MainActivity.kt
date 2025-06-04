package de.arjmandi.gymble.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import de.arjmandi.gymble.data.dto.VibeDto
import de.arjmandi.gymble.domain.model.Gym
import de.arjmandi.gymble.feature.matching.model.GymCardUiState
import de.arjmandi.gymble.feature.matching.model.toGymCardUiState
import de.arjmandi.gymble.feature.matching.ui.GymCard
import de.arjmandi.gymble.feature.matching.ui.GymCardStack
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
// 				GymCardPreview()
// 				GymScreenPreview()
				MatchingScreen(matchingViewModel)
			}
		}
	}
}

@Composable
fun GymCardPreview() {
	val gymCardUiState = GymCardUiState(
		imageUrl = "https://res.cloudinary.com/dlgtohpdw/image/upload/v1748305418/2_geralt_e3xmx7.webp",
		title = "Geralt’s Gym",
		subtitle = "No excuses. Just contracts.",
		description = "SwordFit & Potion Conditioning",
		vibe = listOf("⚔️ Intense", "🤫 Silent"),
		quote = "\"Hmm.\"",
	)
	GymCard(gymCardUiState = gymCardUiState)
}

@Composable
fun GymScreenPreview() {
	var gymCards by remember { mutableStateOf(sampleGyms.shuffled().map { it.toGymCardUiState() }) }
	GymCardStack(
		gymCards = gymCards,
		onCardSwiped = { swipedCard, direction ->
			gymCards = gymCards - swipedCard
		},
	)
}

val sampleGyms = listOf(
	Gym(
		name = "Geralt’s Gym",
		slogan = "No excuses. Just contracts.",
		specialty = "SwordFit & Potion Conditioning",
		vibe = listOf(
			VibeDto(emoji = "⚔️", trait = "Intense").toString(),
			VibeDto(emoji = "🤫", trait = "Silent").toString(),
		),
		quote = "Hmm.",
		imageUrl = "https://res.cloudinary.com/dlgtohpdw/image/upload/v1748305418/2_geralt_e3xmx7.webp",
	),
	Gym(
		name = "Yennefer’s Studio",
		slogan = "Power. Poise. And push.",
		specialty = "Mystic Strength & Arcane Yoga",
		vibe = listOf(
			VibeDto(emoji = "🔮", trait = "Elegant").toString(),
			VibeDto(emoji = "🧘‍♀️", trait = "Intimidating").toString(),
		),
		quote = "Magic won't fix your form. I will.",
		imageUrl = "https://res.cloudinary.com/dlgtohpdw/image/upload/v1748305414/1_yennefer_t4dur3.webp",
	),
	Gym(
		name = "Ezio Fit",
		slogan = "Nothing is true. Everything is trained.",
		specialty = "Parkour & Assassin Mobility",
		vibe = listOf(
			VibeDto(emoji = "🕶️", trait = "Stealthy").toString(),
			VibeDto(emoji = "🤸", trait = "Agile").toString(),
		),
		quote = "Requiescat in pace... after leg day.",
		imageUrl = "https://res.cloudinary.com/dlgtohpdw/image/upload/v1748305418/9_ezio_avvv1s.webp",
	),
	Gym(
		name = "Sadie’s Iron Haven",
		slogan = "Wrangle your limits.",
		specialty = "Grit Training & Rifle Strength",
		vibe = listOf(
			VibeDto(emoji = "🔥", trait = "Gritty").toString(),
			VibeDto(emoji = "😤", trait = "Unapologetic").toString(),
		),
		quote = "I ain’t here to babysit.",
		imageUrl = "https://res.cloudinary.com/dlgtohpdw/image/upload/v1748305418/10_sadie_wlvvfh.webp",
	),
	Gym(
		name = "Kara’s Core Lab",
		slogan = "Built to care. Programmed to shred.",
		specialty = "Android HIIT & Circuit Mindfulness",
		vibe = listOf(
			VibeDto(emoji = "🤖", trait = "Calm").toString(),
			VibeDto(emoji = "🎯", trait = "Focused").toString(),
		),
		quote = "Would you like a protein shake, Todd?",
		imageUrl = "https://res.cloudinary.com/dlgtohpdw/image/upload/v1748305417/11_kara_efswoj.webp",
	),
	Gym(
		name = "Venom Gym",
		slogan = "No pain. No extraction.",
		specialty = "Tactical Endurance & CQC",
		vibe = listOf(
			VibeDto(emoji = "💣", trait = "Silent").toString(),
			VibeDto(emoji = "☠️", trait = "Deadly").toString(),
		),
		quote = "Kept you waiting, huh?",
		imageUrl = "https://res.cloudinary.com/dlgtohpdw/image/upload/v1748305417/12_venom_jwncod.webp",
	),
	Gym(
		name = "Ghost Fit HQ",
		slogan = "Train like no one’s watching.",
		specialty = "Stealth Power & Tactical Mobility",
		vibe = listOf(
			VibeDto(emoji = "🥷", trait = "Discreet").toString(),
			VibeDto(emoji = "💪", trait = "Hardcore").toString(),
		),
		quote = "They won’t see you coming—or leaving.",
		imageUrl = "https://res.cloudinary.com/dlgtohpdw/image/upload/v1748305416/13_ghost_cj110o.webp",
	),
	Gym(
		name = "Elizabeth’s Ascent",
		slogan = "Unlock every door to your potential.",
		specialty = "Dimensional Strength & Elegance",
		vibe = listOf(
			VibeDto(emoji = "🧠", trait = "Smart").toString(),
			VibeDto(emoji = "👑", trait = "Classy").toString(),
		),
		quote = "A little cardio never hurt the multiverse.",
		imageUrl = "https://res.cloudinary.com/dlgtohpdw/image/upload/v1748305416/7_elizabeth_pztp0c.webp",
	),
	Gym(
		name = "Emily’s Method",
		slogan = "Loyal to the grind.",
		specialty = "Blade Agility & Shadow Work",
		vibe = listOf(
			VibeDto(emoji = "🖤", trait = "Precise").toString(),
			VibeDto(emoji = "🎯", trait = "Disciplined").toString(),
		),
		quote = "You either train, or vanish.",
		imageUrl = "https://res.cloudinary.com/dlgtohpdw/image/upload/v1748305416/8_emily_bgaxka.webp",
	),
	Gym(
		name = "Joel’s Forge",
		slogan = "Survive and grind.",
		specialty = "Raw Strength & Survival Sets",
		vibe = listOf(
			VibeDto(emoji = "🪵", trait = "Rugged").toString(),
			VibeDto(emoji = "🧱", trait = "Grit-Heavy").toString(),
		),
		quote = "You lift what you carry.",
		imageUrl = "https://res.cloudinary.com/dlgtohpdw/image/upload/v1748305415/3_joel_cfpz2r.webp",
	),
	Gym(
		name = "Claire’s Core Zone",
		slogan = "Outrun the outbreak.",
		specialty = "Reflex Drills & Resistance",
		vibe = listOf(
			VibeDto(emoji = "🧟‍♀️", trait = "Fast").toString(),
			VibeDto(emoji = "🧰", trait = "Resourceful").toString(),
		),
		quote = "Lock, load, lunge.",
		imageUrl = "https://res.cloudinary.com/dlgtohpdw/image/upload/v1748305415/5_clair_tkhflx.webp",
	),
	Gym(
		name = "Ellie’s Edge",
		slogan = "Play rough. Train harder.",
		specialty = "Stealth HIIT & Bow Training",
		vibe = listOf(
			VibeDto(emoji = "🎸", trait = "Fierce").toString(),
			VibeDto(emoji = "🧨", trait = "Defiant").toString(),
		),
		quote = "I don’t need luck. I need PRs.",
		imageUrl = "https://res.cloudinary.com/dlgtohpdw/image/upload/v1748305415/4_ellie_mochay.webp",
	),
	Gym(
		name = "Trevor’s Gym Cage",
		slogan = "Unstable gains only.",
		specialty = "Chaos Circuit & Rage Sets",
		vibe = listOf(
			VibeDto(emoji = "💥", trait = "Aggressive").toString(),
			VibeDto(emoji = "📢", trait = "Loud").toString(),
		),
		quote = "DON’T SKIP LEGS, YOU COWARD!",
		imageUrl = "https://res.cloudinary.com/dlgtohpdw/image/upload/v1748305415/6_trevor_xgia6h.webp",
	),
)
