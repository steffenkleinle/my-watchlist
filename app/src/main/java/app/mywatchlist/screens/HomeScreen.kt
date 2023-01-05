package app.mywatchlist.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilledTonalIconButton
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
import app.mywatchlist.ui.viewModels.WatchablesViewModel


@Composable
fun HomeScreen(
    navController: NavController,
    watchablesViewModel: WatchablesViewModel
) {
    val uiState by watchablesViewModel.uiState.collectAsState()
    val query by watchablesViewModel.queryUiState.collectAsState()
    
    val showSearchInput = query != null

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
                        text = stringResource(R.string.trending),
                        color = MaterialTheme.colors.primary,
                        fontSize = MaterialTheme.typography.h4.fontSize,
                        fontWeight = FontWeight.Bold
                    )
                    FilledTonalIconButton(
                        onClick = { watchablesViewModel.update("") }
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
                    value = query ?: "",
                    onValueChange = { watchablesViewModel.update(it) },
                    label = { Text(stringResource(id = R.string.search)) },
                    trailingIcon = {
                        FilledTonalIconButton(
                            onClick = { watchablesViewModel.update(null) }
                        ) {
                            Icon(
                                Icons.Filled.Close, contentDescription = "Close",
                                modifier = Modifier.padding(3.dp)
                            )
                        }
                    }
                )
            }
            if (uiState.loading || uiState.data.isNullOrEmpty()) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .semantics(mergeDescendants = true) {}
                            .fillMaxWidth(),
                    )
                }
            }else{
                MovieGrid(navController, uiState.data!!)
            }
        }
    }
}

