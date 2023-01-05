package app.mywatchlist.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.mywatchlist.R
import app.mywatchlist.Screen
import app.mywatchlist.data.models.Watchable
import coil.compose.AsyncImage

@Composable
fun MovieCard(navController: NavController, watchable: Watchable) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .clickable {
                navController.navigate(route = Screen.Details.passId(watchable.id))
            },
    ) {
        if (watchable.posterPath.isNullOrEmpty()) {
            Box(contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(R.drawable.blank_movie_poster),
                    contentDescription = watchable.title,
                )
                Text(
                    text = watchable.title,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/original/" + watchable.posterPath,
                contentDescription = watchable.title,
                placeholder = painterResource(R.drawable.blank_movie_poster),
            )
        }
    }
}