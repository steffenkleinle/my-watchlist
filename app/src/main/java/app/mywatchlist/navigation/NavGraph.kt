package app.mywatchlist

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import app.mywatchlist.screens.DetailScreen
import app.mywatchlist.screens.ProfileScreen
import app.mywatchlist.ui.home.HomeViewModel


@Composable
fun SetupNavGraph(navController: NavHostController, homeViewModel: HomeViewModel = viewModel()) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(
            route = Screen.Home.route
        ) {
            HomeScreen(navController, homeViewModel)
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
