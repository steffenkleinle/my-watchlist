package app.mywatchlist

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import app.mywatchlist.data.Watchable
import app.mywatchlist.ui.home.HomeViewModel
import coil.compose.AsyncImage
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


@Composable
fun HomeScreen(navController: NavController, homeViewModel: HomeViewModel = viewModel()) {
    val uiState by homeViewModel.uiState.collectAsState()
    Log.d("Home Screen UI State: ", uiState.watchables.toString())

    var searchInput: String = ""

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, 10.dp, 0.dp, 70.dp),

    )
    {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                modifier = Modifier
                    .padding(5.dp, 0.dp, 5.dp, 10.dp),
                text = "Trending",
                color = MaterialTheme.colors.primary,
                fontSize = MaterialTheme.typography.h4.fontSize,
                fontWeight = FontWeight.Bold
            )
//            TextField(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(5.dp, 0.dp, 5.dp, 0.dp),
//                value = searchInput,
//                onValueChange = { searchInput = it },
//                label = { Text("Search") }
//            )
            uiState.watchables.ifEmpty {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .semantics(mergeDescendants = true) {}
                            .fillMaxWidth(),
                    )
                }
            }
            LazyVerticalGrid(columns = GridCells.Fixed(2),
                content = {
                    items(uiState.watchables) { watchable -> MovieCard(navController, watchable) }
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
fun MovieCard(navController: NavController, watchable: Watchable) {
    Card(modifier = Modifier
        .padding(4.dp)
        .clickable {
            navController.navigate(route = Screen.Details.passId(watchable.id))
        },) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/original/" + watchable.posterPath,
            contentDescription = watchable.title,
            placeholder = painterResource(R.drawable.blank_movie_poster),
        )
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            FilledTonalIconButton(onClick = { /*Do sth*/  }) {
                Icon(
                    Icons.Outlined.Add, contentDescription = "Add to myWatchlist",
                    modifier = Modifier.padding(3.dp)
                )
            }
        }
    }
}