package app.mywatchlist

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import app.mywatchlist.screens.MainScreen
import app.mywatchlist.utils.ConnectivityObserver
import app.mywatchlist.utils.NetworkConnectivityObserver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val USER_PREFERENCES = "user_preferences"

@InstallIn(SingletonComponent::class)
@Module
object DataStoreModule {

    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = { appContext.preferencesDataStoreFile(USER_PREFERENCES) }
        )
}


@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val connectivityObserver = NetworkConnectivityObserver(applicationContext)

        setContent {
            val connectionStatus by connectivityObserver.observe().collectAsState(
                initial = ConnectivityObserver.Status.Unavailable
            )
            if (connectionStatus === ConnectivityObserver.Status.Lost) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .zIndex(10f)
                        .background(MaterialTheme.colors.error)
                        .padding(0.dp, 3.dp),
                    contentAlignment = Alignment.TopCenter,
                ) {
                    Row {
                        Icon(Icons.Default.Warning, "Lost connection")
                        androidx.compose.material.Text(
                            text = "You're offline",
                            fontSize = MaterialTheme.typography.h6.fontSize,
                        )
                    }
                }
            }

            MainScreen()
        }
    }
}