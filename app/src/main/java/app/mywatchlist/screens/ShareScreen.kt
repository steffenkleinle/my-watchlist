package app.mywatchlist.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.mywatchlist.R
import kotlinx.coroutines.launch

@Composable
fun ShareScreen(
    navController: NavController,
    watchableTitle: String?
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp, 10.dp, 20.dp, 70.dp),
    ) {
        Column() {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilledTonalIconButton(
                    onClick = { navController.popBackStack() },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colors.background,
                        contentColor = MaterialTheme.colors.onBackground
                    )
                ) {
                    Icon(
                        Icons.Outlined.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
                Text(
                    text = stringResource(R.string.share),
                    color = MaterialTheme.colors.onBackground,
                    fontSize = MaterialTheme.typography.h3.fontSize,
                    fontWeight = FontWeight.Bold
                )
            }
            Row(
                modifier = Modifier.padding(0.dp, 20.dp, 0.dp, 0.dp)
            ) {
                Text(
                    text = watchableTitle ?: "WATCHABLE_TITLE",
                    color = MaterialTheme.colors.onBackground,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    fontWeight = FontWeight.Bold,
                )
            }
            Row(
                modifier = Modifier
                    .padding(0.dp, 90.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colors.primary,
                        contentColor = MaterialTheme.colors.background
                    ),
                    onClick = {
                       /*dkdk*/
                        })
                     {
                    Icon(Icons.Default.Person, stringResource(R.string.select_Contact))
                    Text(
                        text = stringResource(R.string.select_Contact),
                    )
                }
            }
        }

    }
}