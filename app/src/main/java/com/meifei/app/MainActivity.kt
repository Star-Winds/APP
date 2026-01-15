package com.meifei.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.meifei.app.ui.MainViewModel
import com.meifei.app.ui.MainViewModelFactory
import com.meifei.app.ui.screens.HomeScreen
import com.meifei.app.ui.screens.ReviewScreen
import com.meifei.app.ui.theme.MeiFeiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = (application as MeiFeiApplication).repository
        val factory = MainViewModelFactory(repository)
        setContent {
            MeiFeiApp(factory)
        }
    }
}

@Composable
private fun MeiFeiApp(factory: MainViewModelFactory) {
    val viewModel: MainViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var selectedTab by remember { mutableIntStateOf(0) }

    MeiFeiTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            com.meifei.app.ui.components.MainScaffold(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            ) { padding ->
                when (selectedTab) {
                    0 -> HomeScreen(
                        uiState = uiState,
                        onStart = viewModel::startFlight,
                        onEnd = viewModel::endFlight,
                        onUpdateNotes = viewModel::updateNotes,
                        onTick = viewModel::refreshNow,
                        contentPadding = padding
                    )

                    else -> ReviewScreen(
                        uiState = uiState,
                        onDelete = viewModel::deleteSession,
                        onClearAll = viewModel::deleteAllSessions,
                        contentPadding = padding
                    )
                }
            }
        }
    }
}
