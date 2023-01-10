package app.mywatchlist.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.mywatchlist.R
import app.mywatchlist.ui.components.MovieGrid
import app.mywatchlist.ui.viewModels.WatchlistViewModel

@Composable
fun Watchlist(navController: NavController, watchlistViewModel: WatchlistViewModel) {

    val uiState by watchlistViewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, 10.dp, 0.dp, 70.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp, 0.dp, 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier
                        .padding(5.dp, 0.dp, 5.dp, 15.dp),
                    text = stringResource(R.string.watchlist),
                    color = MaterialTheme.colors.onBackground,
                    fontSize = MaterialTheme.typography.h4.fontSize,
                    fontWeight = FontWeight.Bold
                )
            }

            if (uiState.loading) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .semantics(mergeDescendants = true) {}
                            .fillMaxWidth(),
                    )
                }
            } else {
                if (uiState.data.isNullOrEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(0.dp, 10.dp, 0.dp, 70.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            stringResource(R.string.no_movies_yet),
                            color = MaterialTheme.colors.onBackground,
                            fontSize = MaterialTheme.typography.h6.fontSize,
                        )
                    }
                } else {
                    MovieGrid(navController, uiState.data!!)
                }
            }
        }
    }
}

