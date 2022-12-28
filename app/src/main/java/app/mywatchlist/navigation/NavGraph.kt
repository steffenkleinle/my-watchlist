package app.mywatchlist.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import app.mywatchlist.*
import app.mywatchlist.screens.HomeScreen
import app.mywatchlist.ui.viewModels.WatchablesViewModel


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
            Log.d("Args", it.arguments?.getInt(DETAIL_ARGUMENT_KEY).toString())
            DetailScreen(navController, it.arguments?.getInt(DETAIL_ARGUMENT_KEY).toString())
        }
        composable(
            route = Screen.MyWatchlist.route
        ) {
            MyWatchlistScreen(navController)
        }
        composable(
            route = Screen.Profile.route
        ) {
            ProfileScreen(navController)
        }
    }
}
