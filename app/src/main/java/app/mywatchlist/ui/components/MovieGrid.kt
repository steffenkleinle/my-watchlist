package app.mywatchlist.ui.components

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import app.mywatchlist.data.models.Watchable

@Composable
fun MovieGrid(navController: NavController, uiState: List<Watchable>) {

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

@Composable
fun MovieGrid(navController: NavController, items: LazyPagingItems<Watchable>) {

    LazyVerticalGrid(columns = GridCells.Fixed(2),
        content = {
            items(items.itemCount) {
                MovieCard(
                    navController,
                    items[it]!!
                )
            }
        })
}
