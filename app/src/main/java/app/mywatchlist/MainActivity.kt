package app.mywatchlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import app.mywatchlist.screens.MainScreen
import app.mywatchlist.ui.viewModels.WatchablesViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val watchablesViewModel = ViewModelProvider(this)[WatchablesViewModel::class.java]
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                watchablesViewModel.uiState.map { it }.distinctUntilChanged()
                    .collect { uiState ->
                        println("UI State: " + uiState)
                    }
            }
        }

        setContent {
            MainScreen()
        }
    }
}