package com.example.mvvm

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import com.example.mvvm.navigation.AppNavigation
import com.example.mvvm.ui.theme.MvvmTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    companion object {

        const val EXTRA_CHARACTER_ID =
            "widget_character_id"
    }

    private var initialCharacterId
            by mutableStateOf<Int?>(
                null
            )

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        installSplashScreen()

        super.onCreate(
            savedInstanceState
        )

        initialCharacterId =
            readCharacterId(
                intent
            )

        setContent {

            MvvmTheme {

                AppRoot(
                    initialCharacterId =
                        initialCharacterId
                )
            }
        }
    }

    override fun onNewIntent(
        intent: Intent
    ) {

        super.onNewIntent(
            intent
        )

        setIntent(
            intent
        )

        initialCharacterId =
            readCharacterId(
                intent
            )
    }

    private fun readCharacterId(
        intent: Intent?
    ): Int? {

        val id =
            intent?.getIntExtra(
                EXTRA_CHARACTER_ID,
                -1
            )
                ?: -1

        return id.takeIf {
            it > 0
        }
    }
}
/*@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setOnExitAnimationListener { splashScreenView ->

            splashScreenView.view
                .animate()
                .alpha(0f)
                .scaleX(1.15f)
                .scaleY(1.15f)
                .setDuration(300L)
                .withEndAction {
                    splashScreenView.remove()
                }
                .start()
        }

        setContent {
            MvvmTheme() {

                //AppNavigation()
                AppRoot()

            }
        }
    }
}*/

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MvvmTheme {
        Greeting("Android")
    }
}