package xyz.teamgravity.agoravideocall.presentation.screen

import android.Manifest
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import io.agora.agorauikit_android.AgoraConnectionData
import io.agora.agorauikit_android.AgoraVideoViewer
import xyz.teamgravity.agoravideocall.core.Const
import xyz.teamgravity.agoravideocall.presentation.viewmodel.VideoViewModel

@OptIn(ExperimentalUnsignedTypes::class)
@Composable
fun VideoScreen(
    roomName: String,
    onNavigateUp: () -> Unit = {}
) {

    var agoraView: AgoraVideoViewer? = null

    val viewmodel = viewModel<VideoViewModel>()

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            viewmodel.onPermissionResult(
                acceptedAudioPermission = permissions[Manifest.permission.RECORD_AUDIO] == true,
                acceptedVideoPermission = permissions[Manifest.permission.CAMERA] == true
            )
        }
    )

    LaunchedEffect(key1 = true) {
        permissionLauncher.launch(arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA))
    }

    if (viewmodel.hasAudioPermission.value && viewmodel.hasVideoPermission.value) {
        AndroidView(
            factory = { context ->
                AgoraVideoViewer(
                    context = context, connectionData = AgoraConnectionData(
                        appId = Const.APP_ID,
                        appToken = "df4ed11eded9458b8d47117972db8177"
                    )
                ).also {
                    println("raheem: $roomName")
                    it.join(roomName)
                    agoraView = it
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }

    BackHandler {
        agoraView?.leaveChannel()
        onNavigateUp()
    }
}