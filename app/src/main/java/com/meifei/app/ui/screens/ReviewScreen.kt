package com.meifei.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.meifei.app.ui.MainViewModel
import com.meifei.app.ui.components.RecordItem

@Composable
fun ReviewScreen(
    uiState: MainViewModel.UiState,
    onDelete: (Long) -> Unit,
    onClearAll: () -> Unit,
    contentPadding: PaddingValues
) {
    var showClearDialog by remember { mutableStateOf(false) }
    var showPrivacy by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("复盘", style = MaterialTheme.typography.headlineSmall)
            IconButton(onClick = { showPrivacy = true }) {
                Icon(Icons.Default.Info, contentDescription = "隐私说明")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("统计卡片", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                StatsRow(label = "7 天内", stats = uiState.stats7)
                Spacer(modifier = Modifier.height(8.dp))
                StatsRow(label = "30 天内", stats = uiState.stats30)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("称号匹配", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text("称号：${uiState.title.title}", style = MaterialTheme.typography.titleMedium)
                Text("规则：${uiState.title.rule}", style = MaterialTheme.typography.bodyMedium)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("历史起飞记录", fontWeight = FontWeight.Bold)
            IconButton(onClick = { showClearDialog = true }) {
                Icon(Icons.Default.Delete, contentDescription = "清空")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(uiState.sessions, key = { it.id }) { session ->
                RecordItem(session = session, onDelete = { onDelete(session.id) })
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }

    if (showClearDialog) {
        AlertDialog(
            onDismissRequest = { showClearDialog = false },
            title = { Text("清空记录") },
            text = { Text("确认清空全部起飞记录吗？此操作不可撤销。") },
            confirmButton = {
                Button(onClick = {
                    onClearAll()
                    showClearDialog = false
                }) {
                    Text("确认")
                }
            },
            dismissButton = {
                TextButton(onClick = { showClearDialog = false }) {
                    Text("取消")
                }
            }
        )
    }

    if (showPrivacy) {
        AlertDialog(
            onDismissRequest = { showPrivacy = false },
            title = { Text("隐私说明") },
            text = { Text("本应用仅在本地保存起飞记录，不上传任何数据，也不申请定位权限。") },
            confirmButton = {
                TextButton(onClick = { showPrivacy = false }) {
                    Text("知道了")
                }
            }
        )
    }
}

@Composable
private fun StatsRow(label: String, stats: com.meifei.app.util.StatsCalculator.WindowStats) {
    val totalMinutes = stats.totalDurationMs / 60000
    val averageMinutes = stats.averageDurationMs / 60000
    Column {
        Text(
            text = "$label 次数：${stats.count} 次",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "$label 总时长：${totalMinutes} 分钟",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "$label 平均时长：${averageMinutes} 分钟",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
