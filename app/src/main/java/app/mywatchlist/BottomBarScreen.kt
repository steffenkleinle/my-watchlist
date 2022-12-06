package app.mywatchlist

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
)  {
    object Home: BottomBarScreen(
        "movies",
        "Movies",
        Icons.Default.Home
    )
    object MyWatchlist: BottomBarScreen(
        "myWatchlist",
        "My Watchlist",
        Icons.Default.Star
    )
    object Profile: BottomBarScreen(
        "profile",
        "Profile",
        Icons.Default.Person
    )
}
