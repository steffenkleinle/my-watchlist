package app.mywatchlist.ui.components

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import app.mywatchlist.data.models.Watchable
import app.mywatchlist.ui.viewModels.WatchablesViewModel

@Composable
fun MovieGrid(navController: NavController, uiState: List<Watchable>){

    LazyVerticalGrid(columns = GridCells.Fixed(2),
                content = {
                    items(uiState) { watchable ->
                        MovieCard(
                            navController,
                            watchable
                        )
                    }
                })
}