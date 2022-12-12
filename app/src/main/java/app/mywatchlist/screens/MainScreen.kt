package app.mywatchlist


import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen(){
    val navController = rememberNavController()
    Scaffold(
<<<<<<< HEAD
        bottomBar = { BottomBar(navController) }
    ) {
        SetupNavGraph(navController)
=======
        bottomBar = { BottomBar(navController = navController) }
    ) {
        SetupNavGraph(navController = navController)
>>>>>>> 4393578ae54a136a9843bf2b6833e69a40c52c2e
    }
}

@Composable
fun BottomBar(navController: NavHostController){
    val screens = listOf(
        Screen.Home,
        Screen.MyWatchlist,
        Screen.Profile
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation {
        screens.forEach { screen ->
<<<<<<< HEAD
            AddItem(screen, currentDestination, navController)
=======
            AddItem(screen = screen, currentDestination = currentDestination, navController = navController)
>>>>>>> 4393578ae54a136a9843bf2b6833e69a40c52c2e
        }
    }
}

@Composable
<<<<<<< HEAD
private fun RowScope.AddItem(
=======
fun RowScope.AddItem(
>>>>>>> 4393578ae54a136a9843bf2b6833e69a40c52c2e
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
){
    BottomNavigationItem(
        label = {
<<<<<<< HEAD
            Text(screen.title)
        },
        icon = {
            Icon(screen.icon, "navigation_icon")
=======
            Text(text = screen.title)
        },
        icon = {
            Icon(imageVector = screen.icon, contentDescription = "navigation_icon")
>>>>>>> 4393578ae54a136a9843bf2b6833e69a40c52c2e
        },
        selected = currentDestination?.hierarchy?.any{
            it.route == screen.route
        } == true,
        onClick = {
            navController.navigate(screen.route){
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}