package xyz.teamgravity.agoravideocall

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RoomViewModel (): ViewModel() {

    private val _roomName = mutableStateOf(TextInputState())
    val roomName: State<TextInputState> = _roomName

    private val _onJoinEvent = Channel<String> {  }
    val onJoinEvent: Flow<String> = _onJoinEvent.receiveAsFlow()

    fun onRoomEnter(name: String) {
        _roomName.value = _roomName.value.copy(text = name)
    }

    fun onJoinRoom() {
        if (roomName.value.text.isBlank()) {
            _roomName.value = _roomName.value.copy(error = "The room can't be empty!")
            return
        }

        viewModelScope.launch { _onJoinEvent.send(_roomName.value.text) }
    }
}