package app.mywatchlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController, watchableId: String){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(painter = painterResource(id = R.drawable.backdrop_example), contentDescription = "Movie Poster")
        Column(modifier = Modifier
            .padding(10.dp, 10.dp, 20.dp, 70.dp)
            .fillMaxWidth(),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()) {
                Column(){
                    Text(
                        text = "Movie Title",
                        color = MaterialTheme.colors.primary,
                        fontSize = MaterialTheme.typography.h4.fontSize,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "2022 · PG-13 · 2h 15min",
                    )
                    Row() {
                        AssistChip(
                            modifier = Modifier
                                .padding(2.dp, 0.dp, 2.dp, 0.dp),
                            onClick = { /* Do something! */ },
                            label = { Text("Action") }
                        )
                        AssistChip(
                            modifier = Modifier
                                .padding(2.dp, 0.dp, 2.dp, 0.dp),
                            onClick = { /* Do something! */ },
                            label = { Text("Drama") }
                        )
                        AssistChip(
                            modifier = Modifier
                                .padding(2.dp, 0.dp, 2.dp, 0.dp),
                            onClick = { /* Do something! */ },
                            label = { Text("Whatever") }
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(10.dp, 0.dp, 0.dp, 0.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ){
                        Icon(Icons.Default.Star, "Vote")
                        Text(
                            text = "7,8/10",
                            fontWeight = FontWeight.Bold,
                            fontSize = MaterialTheme.typography.h6.fontSize,
                        )
                    }
                    Text(
                        text = "(20.000)",
                    )
                }
            }
            Divider(modifier = Modifier
                .padding(0.dp, 10.dp, 0.dp, 10.dp))
            Text(text = "Inspired by the gripping true story of a man who would do anything for his family—and for freedom. When Peter, an enslaved man, risks his life to escape and return to his family, he embarks on a perilous journey of love and endurance.")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun detailPreview(){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(painter = painterResource(id = R.drawable.backdrop_example), contentDescription = "Movie Poster")
            Column(modifier = Modifier
                .padding(10.dp, 10.dp, 20.dp, 70.dp)
                .fillMaxWidth(),
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween) {
                        Column(){
                            Text(
                                text = "Movie Title",
                                color = MaterialTheme.colors.primary,
                                fontSize = MaterialTheme.typography.h4.fontSize,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "2022 · PG-13 · 2h 15min",
                            )
                            Row() {
                                AssistChip(
                                    modifier = Modifier
                                        .padding(2.dp, 0.dp, 2.dp, 0.dp),
                                    onClick = { /* Do something! */ },
                                    label = { Text("Action") }
                                )
                                AssistChip(
                                    modifier = Modifier
                                        .padding(2.dp, 0.dp, 2.dp, 0.dp),
                                    onClick = { /* Do something! */ },
                                    label = { Text("Drama") }
                                )
                                AssistChip(
                                    modifier = Modifier
                                        .padding(2.dp, 0.dp, 2.dp, 0.dp),
                                    onClick = { /* Do something! */ },
                                    label = { Text("Whatever") }
                                )
                            }
                        }
                        Column(
                            horizontalAlignment = Alignment.End
                        ) {
                            Row(){
                                Icon(Icons.Default.Star, "Vote")
                                Text(
                                    text = "7,8/10",
                                    fontWeight = FontWeight.Bold)
                            }
                            Text(text = "(20.000)")
                        }
            }
                Divider(modifier = Modifier
                    .padding(0.dp, 10.dp, 0.dp, 10.dp))
                Text(text = "Inspired by the gripping true story of a man who would do anything for his family—and for freedom. When Peter, an enslaved man, risks his life to escape and return to his family, he embarks on a perilous journey of love and endurance.")
            }


    }
}