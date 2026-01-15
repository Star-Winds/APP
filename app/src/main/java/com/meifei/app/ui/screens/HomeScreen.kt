package com.meifei.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.meifei.app.ui.MainViewModel
import com.meifei.app.util.TimeUtils
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    uiState: MainViewModel.UiState,
    onStart: (String, String) -> Unit,
    onEnd: () -> Unit,
    onUpdateNotes: (String, String) -> Unit,
    onTick: () -> Unit,
    contentPadding: PaddingValues
) {
    var location by remember(uiState.locationText) { mutableStateOf(uiState.locationText) }
    var note by remember(uiState.note) { mutableStateOf(uiState.note) }

    LaunchedEffect(uiState.isFlying) {
        while (uiState.isFlying) {
            onTick()
            delay(1000)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val buttonColor = if (uiState.isFlying) Color(0xFFD32F2F) else Color(0xFF2E7D32)
        val buttonText = if (uiState.isFlying) "平稳着陆" else "开始起飞"

        Box(
            modifier = Modifier
                .size(180.dp)
                .clip(CircleShape)
                .background(buttonColor)
                .clickable {
                    if (uiState.isFlying) {
                        onEnd()
                    } else {
                        onStart(location, note)
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = buttonText,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.isFlying) {
            val elapsed = TimeUtils.formatDuration(System.currentTimeMillis() - uiState.startAt)
            Text(
                text = "进行中：已持续 $elapsed",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = location,
            onValueChange = {
                location = it
                onUpdateNotes(location, note)
            },
            label = { Text("地点（可选）") },
            modifier = Modifier.fillMaxWidth().height(56.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = note,
            onValueChange = {
                note = it
                onUpdateNotes(location, note)
            },
            label = { Text("备注（可选）") },
            modifier = Modifier.fillMaxWidth().height(56.dp)
        )
    }
}
