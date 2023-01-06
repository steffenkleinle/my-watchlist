package app.mywatchlist.screens

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import app.mywatchlist.R
import app.mywatchlist.data.models.Genre
import app.mywatchlist.ui.viewModels.WatchableDetailViewModel
import app.mywatchlist.ui.viewModels.WatchlistViewModel
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import kotlin.math.round

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavController,
    watchableId: Int?,
    watchableDetailViewModel: WatchableDetailViewModel = viewModel(),
    watchlistViewModel: WatchlistViewModel = viewModel()
) {

    val watchable by watchableDetailViewModel.uiState.collectAsState()
    var genreList: List<Genre> = emptyList()

    if (watchableId != null) {
        watchableDetailViewModel.setId(watchableId)
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    if (!watchable.data?.genres.isNullOrEmpty()) {
        genreList = watchable.data?.genres?.toList() ?: emptyList()
    }

    if(watchable.loading){
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            CircularProgressIndicator(
                color = MaterialTheme.colors.primary
            )
        }
    } else {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Box() {
            if (watchable.data?.backdropPath.isNullOrEmpty()) {
                Box(
                    contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(R.drawable.blank_movie_backdrop),
                        contentDescription = watchable.data?.title,
                    )
                    Text(
                        text = watchable.data?.title ?: "",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.onSurface
                    )
                }
            }
            AsyncImage(
                model = "https://image.tmdb.org/t/p/original/" + watchable.data?.backdropPath,
                contentDescription = watchable.data?.title,
                placeholder = painterResource(R.drawable.blank_movie_backdrop)
            )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    FilledTonalIconButton(
                        modifier = Modifier.padding(3.dp),
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(Icons.Outlined.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                }
        }
        Column(
            modifier = Modifier
                .padding(10.dp, 10.dp, 20.dp, 70.dp)
                .fillMaxWidth(),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                            Checkbox(
                                colors = CheckboxDefaults.colors(
                                    checkedColor = MaterialTheme.colors.primary,
                                    uncheckedColor = MaterialTheme.colors.primary,
                                    checkmarkColor = MaterialTheme.colors.background
                                ),
                                checked = watchable.data?.watched ?: false,
                                onCheckedChange = {
                                    if (watchable.data?.watched == false) {
                                        watchlistViewModel.addWatched(watchableId ?: -1)
                                    } else {
                                        watchlistViewModel.removeWatched(watchableId ?: -1)
                                    }
                                }
                            )
                        Text(
                            text = watchable.data?.title ?: "",
                            color = MaterialTheme.colors.onSurface,
                            fontSize = MaterialTheme.typography.h4.fontSize,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    Divider(
                        modifier = Modifier
                            .padding(0.dp, 10.dp, 0.dp, 10.dp)
                    )
                    Text(
                        text = stringResource(R.string.runtime_plus_release, watchable.data?.releaseDate.toString(), watchable.data?.runtime.toString()),
                        color = MaterialTheme.colors.onSurface
                    )
                    LazyRow() {
                        itemsIndexed(genreList) { index, item ->
                            AssistChip(
                                modifier = Modifier
                                    .padding(2.dp, 0.dp, 2.dp, 0.dp),
                                onClick = { /* Do something! */ },
                                label = { Text(
                                    text = item.name,
                                    color = MaterialTheme.colors.onSurface
                                ) }
                            )
                        }
                    }
                }
            }
            Divider(
                modifier = Modifier
                    .padding(0.dp, 10.dp, 0.dp, 10.dp)
            )

            Text(
                text = stringResource(R.string.overview),
                fontSize = MaterialTheme.typography.h6.fontSize,
                color = MaterialTheme.colors.onSurface
            )
            Text(
                text = watchable.data?.tagline ?: "",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onSurface
            )
            Text(
                text = watchable.data?.overview ?: "",
                color = MaterialTheme.colors.onSurface
            )

            Divider(
                modifier = Modifier
                    .padding(0.dp, 10.dp, 0.dp, 10.dp)
            )
            Text(
                text = stringResource(R.string.vote),
                fontSize = MaterialTheme.typography.h6.fontSize,
                color = MaterialTheme.colors.onSurface
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    Icons.Default.Star,
                    stringResource(R.string.vote),
                    tint = MaterialTheme.colors.onSurface
                )
                if (watchable.data != null) {
                    Text(
                        text = (round(watchable.data!!.voteAverage * 100) / 100).toString(),
                        fontSize = MaterialTheme.typography.h6.fontSize,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(0.dp, 0.dp, 5.dp, 0.dp),
                        color = MaterialTheme.colors.onSurface
                    )
                }
                Text(
                    text = stringResource(R.string.votecount, watchable.data?.voteCount.toString()),
                    color = MaterialTheme.colors.onSurface
                )
            }
            if (watchable.data?.favorite == true){
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colors.primary,
                            contentColor = MaterialTheme.colors.background
                        ),
                        onClick = {
                        watchlistViewModel.removeFavorite(watchableId ?: -1)
                        scope.launch {
                            snackbarHostState.showSnackbar(message = "Removed from watchlist")
                        }
                    }) {
                        Icon(Icons.Default.Delete, "Remove")
                        Text(
                            text = stringResource(R.string.remove_from_watchlist),
                        )
                    }
                }
                SnackbarHost(
                    hostState = snackbarHostState,
                    snackbar = { SnackbarData -> Snackbar(
                        snackbarData = SnackbarData,
                        containerColor = MaterialTheme.colors.onPrimary,
                        contentColor = MaterialTheme.colors.onBackground
                    ) }
                )
            }else{
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colors.primary,
                            contentColor = MaterialTheme.colors.background
                        ),
                        onClick = {
                        watchlistViewModel.addFavorite(watchableId ?: -1)
                        scope.launch {
                            snackbarHostState.showSnackbar(message = "Added to watchlist")
                        }
                    }) {
                        Icon(Icons.Default.Add, "Add")
                        Text(stringResource(R.string.add_to_Watchlist))
                    }
                }
                SnackbarHost(
                    hostState = snackbarHostState,
                    snackbar = { SnackbarData -> Snackbar(
                        snackbarData = SnackbarData,
                        containerColor = MaterialTheme.colors.onPrimary,
                        contentColor = MaterialTheme.colors.onBackground
                    ) }
                )
            }
        }
        }
    }
}