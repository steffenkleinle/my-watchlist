package app.mywatchlist

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import app.mywatchlist.ui.viewModels.WatchableViewModel
import coil.compose.AsyncImage
import kotlin.math.round

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavController,
    watchableId: Int?,
    watchableViewModel: WatchableViewModel = viewModel()){

    val watchable by watchableViewModel.uiState.collectAsState()

    if (watchableId != null){
        watchableViewModel.set(watchableId)
    }

    Log.d("Watchable in Detail page", watchable.toString())

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box() {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/original/" + watchable.data?.backdropPath,
                contentDescription = watchable.data?.title,
                placeholder = painterResource(R.drawable.blank_movie_backdrop)
            )
            FilledTonalIconButton(
                modifier = Modifier.padding(3.dp),
                onClick = { navController.popBackStack() }
            ) {
                Icon(Icons.Outlined.ArrowBack, contentDescription = "Back")
            }
        }
        Column(modifier = Modifier
            .padding(10.dp, 10.dp, 20.dp, 70.dp)
            .fillMaxWidth(),
            ) {
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Column(){
                    Text(
                        text = watchable.data?.title ?: "",
                        color = MaterialTheme.colors.primary,
                        fontSize = MaterialTheme.typography.h4.fontSize,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = watchable.data?.releaseDate.toString() ?: "",
                    )
                    Row() {
                        AssistChip(
                            modifier = Modifier
                                .padding(2.dp, 0.dp, 2.dp, 0.dp),
                            onClick = { /* Do something! */ },
                            label = { Text("Action") }
                        )
                        AssistChip(
                            modifier = Modifier
                                .padding(2.dp, 0.dp, 2.dp, 0.dp),
                            onClick = { /* Do something! */ },
                            label = { Text("Drama") }
                        )
                        AssistChip(
                            modifier = Modifier
                                .padding(2.dp, 0.dp, 2.dp, 0.dp),
                            onClick = { /* Do something! */ },
                            label = { Text("Whatever") }
                        )
                    }
                }
            }

            Divider(modifier = Modifier
                .padding(0.dp, 10.dp, 0.dp, 10.dp))

            Text(
                text = "Overview",
                fontSize = MaterialTheme.typography.h6.fontSize,
                color = Color.Black
            )
            Text(text = watchable.data?.overview ?: "")

            Divider(modifier = Modifier
                .padding(0.dp, 10.dp, 0.dp, 10.dp))


                Text(
                    text = "Vote",
                    fontSize = MaterialTheme.typography.h6.fontSize,
                    color = Color.Black
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(Icons.Default.Star, "Vote")
                    if (watchable.data !== null && watchable.data!!.voteAverage !== null) {
                        Text(
                            text = (round(watchable.data!!.voteAverage * 100) / 100).toString() ?: "",
                            fontSize = MaterialTheme.typography.h6.fontSize,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(0.dp, 0.dp, 5.dp, 0.dp)
                        )
                    }
                    Text(text = "(".plus(watchable.data?.voteCount.toString() ?: "",).plus(" Votes)"))
                }
            }
        }
    }



@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun detailPreview() {
    Column() {


        Box() {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/original/dKqa850uvbNSCaQCV4Im1XlzEtQ.jpg",
                contentDescription = "watchable.data?.title",
                placeholder = painterResource(R.drawable.blank_movie_backdrop)
            )
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(Icons.Outlined.ArrowBack, contentDescription = "Back")
            }
        }

        Column() {
            Row(
                modifier = Modifier.fillMaxSize(),
            ) {
                Column(
                    modifier = Modifier
                        .padding(10.dp, 10.dp, 20.dp, 70.dp)
                        .fillMaxWidth(),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column() {
                            Text(
                                text = "Movie Title",
                                color = MaterialTheme.colors.primary,
                                fontSize = MaterialTheme.typography.h4.fontSize,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "2022 · PG-13 · 2h 15min",
                            )
                            Row() {
                                AssistChip(
                                    modifier = Modifier
                                        .padding(2.dp, 0.dp, 2.dp, 0.dp),
                                    onClick = { /* Do something! */ },
                                    label = { Text("Action") }
                                )
                                AssistChip(
                                    modifier = Modifier
                                        .padding(2.dp, 0.dp, 2.dp, 0.dp),
                                    onClick = { /* Do something! */ },
                                    label = { Text("Drama") }
                                )
                                AssistChip(
                                    modifier = Modifier
                                        .padding(2.dp, 0.dp, 2.dp, 0.dp),
                                    onClick = { /* Do something! */ },
                                    label = { Text("Whatever") }
                                )
                            }
                        }
                        Column(
                            horizontalAlignment = Alignment.End
                        ) {
                            Row() {
                                Icon(Icons.Default.Star, "Vote")
                                Text(
                                    text = "7,8/10",
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(text = "(20.000)")
                        }
                    }
                    Divider(
                        modifier = Modifier
                            .padding(0.dp, 10.dp, 0.dp, 10.dp)
                    )
                    Text(text = "Inspired by the gripping true story of a man who would do anything for his family—and for freedom. When Peter, an enslaved man, risks his life to escape and return to his family, he embarks on a perilous journey of love and endurance.")
                }
            }
        }
    }
}