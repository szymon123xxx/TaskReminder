package com.example.taskreminder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskreminder.ui.HomeScreen
import com.example.taskreminder.ui.HomeViewModel
import com.example.taskreminder.ui.theme.TaskReminderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskReminderTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen()
//                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier,
             viewModel: HomeViewModel = hiltViewModel()
) {
//    val context = LocalContext.current
//    val db = Room.databaseBuilder(
//        context = context,
//        AppDatabase::class.java, "database-name"
//    ).build()
//
//    db.eventDao()

    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TaskReminderTheme {
        Greeting("Android")
    }
}