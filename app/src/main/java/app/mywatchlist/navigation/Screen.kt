package app.mywatchlist

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

const val DETAIL_ARGUMENT_KEY = "id"

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
)

sealed class Screen(val route: String){
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
<<<<<<< HEAD
    object Details: Screen("detail_screen/{$DETAIL_ARGUMENT_KEY}"){
        fun passId(id: Int): String {
            return this.route.replace("{$DETAIL_ARGUMENT_KEY}", id.toString())
=======
    object Details: Screen(route = "detail_screen/{$DETAIL_ARGUMENT_KEY}"){
        fun passId(id: Int): String {
            return this.route.replace(oldValue = "{$DETAIL_ARGUMENT_KEY}", newValue = id.toString())
>>>>>>> 4393578ae54a136a9843bf2b6833e69a40c52c2e
        }
    }
}
