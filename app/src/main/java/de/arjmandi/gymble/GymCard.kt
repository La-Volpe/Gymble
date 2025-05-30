package de.arjmandi.gymble

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import de.arjmandi.gymble.domain.model.Gym
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun GymCard(gym: Gym, onSwipe: (String) -> Unit, modifier: Modifier = Modifier) {
    var offsetX by remember { mutableStateOf(0f) }

    Card(
        modifier = modifier
            .offset { IntOffset(offsetX.roundToInt(), 0) }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        if (abs(offsetX) > 300) {
                            onSwipe(if (offsetX > 0) "right" else "left")
                        } else {
                            offsetX = 0f
                        }
                    },
                    onDrag = { _, dragAmount -> offsetX += dragAmount.x }
                )
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = rememberAsyncImagePainter(gym.imageUrl),
                contentDescription = gym.name,
                modifier = Modifier.fillMaxWidth().aspectRatio(1f).clip(RoundedCornerShape(12.dp)),
            )
            Spacer(Modifier.height(12.dp))
            Text(gym.name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text("\"${gym.slogan}\"", fontStyle = FontStyle.Italic)
            Text(gym.specialty, modifier = Modifier.background(Color(0xFFE0E0E0)).padding(4.dp).clip(RoundedCornerShape(12.dp)))
//            Text(gym.vibe, modifier = Modifier.background(Color(0xFFE0E0E0)).padding(4.dp).clip(RoundedCornerShape(12.dp)))
            Text("“${gym.quote}”", fontSize = 14.sp)
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            ) {
                IconButton(onClick = { onSwipe("right") }, modifier = Modifier.size(56.dp).background(Color(0xFFE91E63), CircleShape)) {
                    Icon(painterResource(R.drawable.heart), contentDescription = "Like")
                }
                Spacer(Modifier.width(16.dp))
                IconButton(onClick = { onSwipe("left") }, modifier = Modifier.size(56.dp).background(Color(0xFF9E9E9E), CircleShape)) {
                    Icon(painterResource(R.drawable.next), contentDescription = "Next")
                }
            }
        }
    }
}