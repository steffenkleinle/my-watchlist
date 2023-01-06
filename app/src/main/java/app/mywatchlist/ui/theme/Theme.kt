package app.mywatchlist.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Color(0xFFEDD050),
    primaryVariant = Color(0xFFFFFF81),
    secondary = Color(0xFFC8C7B9),
    onSecondary = Color(0xFF111A22),
    background = Color(0xFF1B2A37),
    onSurface = Color.White,
    onPrimary = Color(0x6FEDD050)
)

private val LightColorPalette = lightColors(
    primary = Color(0xFFB69500),
    primaryVariant = Color(0xFFEDD050),
    secondary = Color(0xFF7DA1BF),
    onSecondary = Color(0xFF9C9C9C),
    background = Color(0xFFE2E2E2),
    onSurface = Color(0xFF1B2A37),
    onPrimary = Color(0x6FEDD050)

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun MyWatchlistTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}