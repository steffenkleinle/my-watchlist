package app.mywatchlist

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

const val DETAIL_ARGUMENT_KEY = "id"
const val WATCHABLE_TO_SHARE = "WATCHABLE"

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

    object Details: Screen(
        "detail_screen/{$DETAIL_ARGUMENT_KEY}"
    ){
        fun passId(id: Int): String {
            return this.route.replace("{$DETAIL_ARGUMENT_KEY}", id.toString())
        }
    }
    object Share: Screen(
        "share/{$WATCHABLE_TO_SHARE}",
    ){
        fun passWatchableTitle(title: String): String{
            return this.route.replace("{$WATCHABLE_TO_SHARE}", title)
        }
    }
}
