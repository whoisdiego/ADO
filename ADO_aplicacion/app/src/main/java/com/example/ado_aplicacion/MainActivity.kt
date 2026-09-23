package com.example.ado_aplicacion

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import com.example.ado_aplicacion.ui.theme.ADO_aplicacionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ADO_aplicacionTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MockupFlowApp()
                }
            }
        }
    }
}

enum class AppScreen(val assetFileName: String) {
    HOME("HomePage.jpeg"),
    LEVELS("Levels.jpeg"),
    PLAYING("Playing.jpeg"),
    MENU_PLAYING("MenuPlaying.jpeg"),
}

@Composable
fun MockupFlowApp(modifier: Modifier = Modifier) {
    var currentScreen by remember { mutableStateOf(AppScreen.HOME) }
    val context = LocalContext.current

    val assetFileName = currentScreen.assetFileName
    val bitmap = remember(assetFileName) {
        try {
            context.assets.open(assetFileName).use { inputStream ->
                BitmapFactory.decodeStream(inputStream)
            }
        } catch (_: Exception) {
            null
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .clickable {
                // Advance flow: HomePage -> Levels -> Playing -> MenuPlaying -> HomePage
                currentScreen = when (currentScreen) {
                    AppScreen.HOME -> AppScreen.LEVELS
                    AppScreen.LEVELS -> AppScreen.PLAYING
                    AppScreen.PLAYING -> AppScreen.MENU_PLAYING
                    AppScreen.MENU_PLAYING -> AppScreen.HOME
                }
            },
        contentAlignment = Alignment.Center,
    ) {
        bitmap?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = currentScreen.name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit,
            )
        }
    }
}
