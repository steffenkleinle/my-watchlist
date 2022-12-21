package app.mywatchlist.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import app.mywatchlist.DETAIL_ARGUMENT_KEY
import app.mywatchlist.Screen
import app.mywatchlist.screens.DetailScreen
import app.mywatchlist.screens.HomeScreen
import app.mywatchlist.screens.MyWatchlistScreen
import app.mywatchlist.screens.ProfileScreen


@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(
            route = Screen.Home.route
        ) {
            HomeScreen(navController)
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
            DetailScreen(navController)
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
