package xyz.teamgravity.agoravideocall

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collectLatest
import xyz.teamgravity.agoravideocall.presentation.viewmodel.RoomViewModel

@Composable
fun RoomScreen(
    onNavigate: (String) -> Unit,
    viewmodel: RoomViewModel = viewModel()
) {

    LaunchedEffect(key1 = true) {
        viewmodel.onJoinEvent.collectLatest { roomName ->
            onNavigate("video_screen/$roomName")
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End
    ) {
        TextField(
            value = viewmodel.roomName.value.text,
            onValueChange = viewmodel::onRoomEnter,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Enter a room name") },
            isError = viewmodel.roomName.value.error != null
        )
        viewmodel.roomName.value.error?.let { Text(text = it, color = MaterialTheme.colors.error) }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = viewmodel::onJoinRoom) {
            Text(text = "Join")
        }
    }
}