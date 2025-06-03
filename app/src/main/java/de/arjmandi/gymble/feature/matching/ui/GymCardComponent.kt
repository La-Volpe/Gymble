package de.arjmandi.gymble.feature.matching.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import de.arjmandi.gymble.feature.matching.model.GymCardUiState

@Composable
fun GymCard(gymCardUiState: GymCardUiState) {
	Card(
		modifier = Modifier
			.fillMaxWidth()
			.padding(16.dp),
		shape = RoundedCornerShape(10.dp),
		elevation = CardDefaults.cardElevation(4.dp)
	) {
		Column {
			AsyncImage(
				model = ImageRequest.Builder(LocalContext.current)
					.data(gymCardUiState.imageUrl)
					.crossfade(true)
					.build(),
				contentDescription = "A picture of ${gymCardUiState.title}",
			)
			Column(
				modifier = Modifier
					.fillMaxWidth()
					.padding(16.dp)
			) {
				Text(
					text = gymCardUiState.title,
					style = MaterialTheme.typography.headlineSmall,
					fontWeight = FontWeight.Bold
				)
				Text(
					text = gymCardUiState.subtitle,
					style = MaterialTheme.typography.bodyMedium,
					color = Color.Gray
				)
				Spacer(modifier = Modifier.height(8.dp))
				Text(
					text = gymCardUiState.description,
					style = MaterialTheme.typography.bodyMedium
				)
				Spacer(modifier = Modifier.height(8.dp))
				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.spacedBy(8.dp)
				) {
					gymCardUiState.vibe.forEach { vibeItem ->
						Row(
							verticalAlignment = Alignment.CenterVertically
						) {
							Box(
								modifier = Modifier
									.background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(8.dp))
									.padding(horizontal = 8.dp, vertical = 4.dp)
							){
								Text(text = vibeItem, style = MaterialTheme.typography.bodyMedium)

							}

						}
					}
				}
				Spacer(modifier = Modifier.height(8.dp))
				Text(
					text = gymCardUiState.quote,
					style = MaterialTheme.typography.bodyMedium,
					fontStyle = MaterialTheme.typography.bodyMedium.fontStyle
				)
				Spacer(modifier = Modifier.height(16.dp))
				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.Center
				) {
					IconButton(
						onClick = { /* Handle redo action */ },
						modifier = Modifier
							.size(48.dp)
							.clip(CircleShape)
							.background(Color.Gray)
					) {
						Icon(
							imageVector = Icons.Rounded.Clear,
							contentDescription = "next",
							tint = Color.White
						)
					}
					Spacer(modifier = Modifier.width(8.dp))
					IconButton(
						onClick = { /* Handle favorite action */ },
						modifier = Modifier
							.size(48.dp)
							.clip(CircleShape)
							.background(Color.Blue)
					) {
						Icon(
							imageVector = Icons.Rounded.Favorite,
							contentDescription = "Favorite",
							tint = Color.Red
						)
					}
				}
			}
		}
	}
}