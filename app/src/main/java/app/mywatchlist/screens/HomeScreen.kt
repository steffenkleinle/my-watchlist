package app.mywatchlist

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import app.mywatchlist.data.Watchable
import app.mywatchlist.ui.home.HomeViewModel
import coil.compose.AsyncImage


@Composable
fun HomeScreen(navController: NavController, homeViewModel: HomeViewModel = viewModel()) {
    val uiState by homeViewModel.uiState.collectAsState()
    Log.d("Home Screen UI State: ", uiState.watchables.toString())

    Box(
        modifier = Modifier.fillMaxSize()
            .padding(0.dp, 10.dp, 0.dp, 70.dp),
        contentAlignment = Alignment.Center,
    )
    {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            uiState.watchables.ifEmpty {
                Text(text = "loading...")
            }
            LazyVerticalGrid(columns = GridCells.Fixed(2),
                content = {
                    items(uiState.watchables) { watchable -> MovieCard(watchable) }
                })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        navController = rememberNavController()
    )
}

@Composable
fun MovieCard(watchable: Watchable) {
    Card(modifier = Modifier.padding(4.dp)) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/original/" + watchable.posterPath,
            contentDescription = null
        )
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            FilledTonalIconButton(onClick = { /* doSomething() */ }) {
                Icon(
                    Icons.Outlined.Add, contentDescription = "Add to myWatchlist",
                    modifier = Modifier.padding(3.dp)
                )
            }
        }
    }
}