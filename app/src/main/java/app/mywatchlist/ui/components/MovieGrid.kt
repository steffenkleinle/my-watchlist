package app.mywatchlist.ui.components

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
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
    AnimatedVisibility(visible = ((items.loadState.append.toString() == ("Loading(endOfPaginationReached=false)").toString()))){
        LinearProgressIndicator(
            modifier = Modifier
                .semantics(mergeDescendants = true) {}
                .fillMaxWidth()
                .padding(0.dp, 8.dp, 0.dp, 0.dp)
                .zIndex(50f)
        )
    }
}
