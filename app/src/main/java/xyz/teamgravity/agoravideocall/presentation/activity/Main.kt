package xyz.teamgravity.agoravideocall.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import xyz.teamgravity.agoravideocall.RoomScreen
import xyz.teamgravity.agoravideocall.presentation.screen.VideoScreen
import xyz.teamgravity.agoravideocall.presentation.theme.AgoraVideoCallTheme

class Main : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AgoraVideoCallTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp), color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "room_screen"
                    ) {
                        composable(route = "room_screen") {
                            RoomScreen(onNavigate = navController::navigate)
                        }
                        composable(route = "video_screen/{roomName}", arguments = listOf(
                            navArgument(name = "roomName") {
                                type = NavType.StringType
                            }
                        )) {
                            val roomName = it.arguments?.getString("roomName") ?: return@composable
                            VideoScreen(roomName = roomName, onNavigateUp = navController::navigateUp)
                        }
                    }
                }
            }
        }
    }
}