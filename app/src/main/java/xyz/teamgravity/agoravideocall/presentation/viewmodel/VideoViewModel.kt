package xyz.teamgravity.agoravideocall.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class VideoViewModel: ViewModel() {

    private val _hasAudioPermission = mutableStateOf(false)
    val hasAudioPermission: State<Boolean> = _hasAudioPermission

    private val _hasVideoPermission = mutableStateOf(false)
    val hasVideoPermission: State<Boolean> = _hasVideoPermission

    fun onPermissionResult(acceptedAudioPermission: Boolean, acceptedVideoPermission: Boolean) {
        _hasAudioPermission.value = acceptedAudioPermission
        _hasVideoPermission.value = acceptedVideoPermission
    }
}