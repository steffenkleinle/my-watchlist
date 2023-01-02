package app.mywatchlist.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.mywatchlist.R
import app.mywatchlist.Screen
import app.mywatchlist.data.models.Watchable
import app.mywatchlist.ui.viewModels.WatchablesTrendingViewModel
import coil.compose.AsyncImage


@Composable
fun HomeScreen(
    navController: NavController,
    watchablesTrendingViewModel: WatchablesTrendingViewModel
) {
    val uiState by watchablesTrendingViewModel.uiState.collectAsState()
    Log.d("Home Screen UI State: ", uiState.toString())

    var showSearchInput by remember { mutableStateOf(uiState.queryString !== "") }

    fun onChangeSearchInput(inputString: String){
        if (inputString !== ""){
            watchablesViewModel.updateQuery(inputString)
        } else{
            watchablesViewModel.updateQuery()
        }
    }

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
            AnimatedVisibility(
                visible = !showSearchInput
            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 0.dp, 0.dp, 5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier
                            .padding(5.dp, 0.dp, 5.dp, 15.dp),
                        text = "Trending",
                        color = MaterialTheme.colors.primary,
                        fontSize = MaterialTheme.typography.h4.fontSize,
                        fontWeight = FontWeight.Bold
                    )
                    FilledTonalIconButton(
                        onClick = { showSearchInput = true }
                    ) {
                        Icon(
                            Icons.Filled.Search, contentDescription = "Search",
                            modifier = Modifier.padding(3.dp)
                        )
                    }
                }
            }
            AnimatedVisibility(
                    visible = showSearchInput
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp, 0.dp, 5.dp, 5.dp),
                    value = uiState.queryString ?: "",
                    onValueChange = { onChangeSearchInput(it) },
                    label = { Text("Search") },
                    trailingIcon = {
                        FilledTonalIconButton(
                            onClick = {
                                showSearchInput = false
                                onChangeSearchInput("")
                            }
                        ) {
                            Icon(
                                Icons.Filled.Close, contentDescription = "Close",
                                modifier = Modifier.padding(3.dp)
                            )
                        }
                    }
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
            }
            LazyVerticalGrid(columns = GridCells.Fixed(2),
                content = {
                    items(uiState.data ?: listOf()) { watchable ->
                        MovieCard(
                            navController,
                            watchable
                        )
                    }
                })
        }
    }
}

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
            Box(contentAlignment = Alignment.Center){
                Image(
                    painter = painterResource(R.drawable.blank_movie_poster),
                    contentDescription = watchable.title,
                )
                Text(
                    text = watchable.title,
                    textAlign = TextAlign.Center
                )
            }

        }else {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/original/" + watchable.posterPath,
                contentDescription = watchable.title,
                placeholder = painterResource(R.drawable.blank_movie_poster),
            )
        }
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            FilledTonalIconButton(onClick = { /*Do sth*/ }) {
                Icon(
                    Icons.Outlined.Add, contentDescription = "Add to myWatchlist",
                    modifier = Modifier.padding(3.dp)
                )
            }
        }
    }
}