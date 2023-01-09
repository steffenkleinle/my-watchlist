package app.mywatchlist.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import app.mywatchlist.DETAIL_ARGUMENT_KEY
import app.mywatchlist.Screen
import app.mywatchlist.WATCHABLE_TO_SHARE
import app.mywatchlist.screens.DetailScreen
import app.mywatchlist.screens.HomeScreen
import app.mywatchlist.screens.ShareScreen
import app.mywatchlist.screens.Watchlist
import app.mywatchlist.ui.viewModels.WatchableDetailViewModel
import app.mywatchlist.ui.viewModels.WatchablesViewModel
import app.mywatchlist.ui.viewModels.WatchlistViewModel


@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(
            route = Screen.Home.route
        ) {
            val viewModel = hiltViewModel<WatchablesViewModel>()
            HomeScreen(navController, viewModel)
        }
        composable(
            route = Screen.Details.route,
            arguments = listOf(
                navArgument(DETAIL_ARGUMENT_KEY) {
                    type = NavType.IntType
                }
            )
        ) {
            val watchableViewModel = hiltViewModel<WatchableDetailViewModel>()
            DetailScreen(
                navController,
                it.arguments?.getInt(DETAIL_ARGUMENT_KEY),
                watchableViewModel
            )
        }
        composable(
            route = Screen.MyWatchlist.route
        ) {
            val viewModel = hiltViewModel<WatchlistViewModel>()
            Watchlist(navController, viewModel)
        }
        composable(
            route = Screen.Share.route
        ){
            ShareScreen(
                navController,
                it.arguments?.getString(WATCHABLE_TO_SHARE)
            )
        }
    }
}
