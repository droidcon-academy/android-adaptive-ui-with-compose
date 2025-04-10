package com.droidcon.adaptiveinbox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.droidcon.adaptiveinbox.components.AdaptiveInboxNavigator
import com.droidcon.adaptiveinbox.ui.theme.AdaptiveInboxTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            AdaptiveInboxTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    AdaptiveInboxNavigator(
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}