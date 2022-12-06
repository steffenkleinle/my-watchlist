package app.mywatchlist

const val DETAIL_ARGUMENT_KEY = "id"
const val DETAIL_ARGUMENT_KEY1 = "name"

sealed class Screen(val route: String){
    object Home: Screen(route = "home_screen")
    object Details: Screen(route = "detail_screen/{$DETAIL_ARGUMENT_KEY}/{$DETAIL_ARGUMENT_KEY1}"){
        fun passId(id: Int): String {
            return this.route.replace(oldValue = "{$DETAIL_ARGUMENT_KEY}", newValue = id.toString())
        }
    }
    object MyWatchlist: Screen(route = "myWatchlist_screen")
    object Profile: Screen(route = "profile_screen")
}
