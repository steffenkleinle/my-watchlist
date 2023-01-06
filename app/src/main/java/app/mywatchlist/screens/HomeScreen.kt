package app.mywatchlist.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.mywatchlist.R
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import app.mywatchlist.ui.components.MovieGrid
import app.mywatchlist.ui.viewModels.WatchablesViewModel


@Composable
fun HomeScreen(
    navController: NavController,
    watchablesViewModel: WatchablesViewModel
) {
    val watchables = watchablesViewModel.items.collectAsLazyPagingItems()
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
                        color = MaterialTheme.colors.onSurface,
                        fontSize = MaterialTheme.typography.h4.fontSize,
                        fontWeight = FontWeight.Bold
                    )
                    FilledTonalIconButton(
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = MaterialTheme.colors.background,
                        ),
                        onClick = { watchablesViewModel.update("") }
                    ) {
                        Icon(
                            Icons.Filled.Search, contentDescription = "Search",
                            modifier = Modifier.padding(3.dp),
                            tint = MaterialTheme.colors.onBackground
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
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = MaterialTheme.colors.onBackground,
                        trailingIconColor = MaterialTheme.colors.onBackground,
                        focusedIndicatorColor = MaterialTheme.colors.onBackground,
                        cursorColor = MaterialTheme.colors.onBackground
                    ),
                    singleLine = true,
                    value = query ?: "",

                    onValueChange = { watchablesViewModel.update(it) },
                    label = { Text(
                        stringResource(id = R.string.search),
                        color = MaterialTheme.colors.onBackground
                    ) },
                    trailingIcon = {
                        FilledTonalIconButton(
                            onClick = { watchablesViewModel.update(null) },
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = Color.Transparent
                            )
                        ) {
                            Icon(
                                Icons.Filled.Close, contentDescription = "Close",
                                modifier = Modifier.padding(3.dp)
                            )
                        }
                    }
                )
            }
            if (watchables.loadState.refresh is LoadState.Loading) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .semantics(mergeDescendants = true) {}
                            .fillMaxWidth(),
                    )
                }
            } else {
                MovieGrid(navController, watchables)
            }
        }
    }
}

