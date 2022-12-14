package app.mywatchlist.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.mywatchlist.R
import app.mywatchlist.data.models.Watchable
import app.mywatchlist.ui.screens.Screen
import coil.compose.AsyncImage

@Composable
fun MovieCard(
    navController: NavController,
    watchable: Watchable,
) {
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
            Box(
                contentAlignment = Alignment.TopEnd
            ) {
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/original/" + watchable.posterPath,
                    contentDescription = watchable.title,
                    placeholder = painterResource(R.drawable.blank_movie_poster),
                    error = painterResource(R.drawable.blank_movie_poster),
                )
                Checkbox(
                    checked = watchable.watched,
                    enabled = false,
                    onCheckedChange = { /* Do nothing */ },
                    colors = CheckboxDefaults.colors(
                        disabledCheckedColor = if (watchable.favorite) MaterialTheme.colors.primary else MaterialTheme.colors.secondary,
                        disabledUncheckedColor = if (watchable.favorite) MaterialTheme.colors.primary else MaterialTheme.colors.secondary,
                        checkmarkColor = MaterialTheme.colors.background
                    ),
                )
            }
        }
    }
}