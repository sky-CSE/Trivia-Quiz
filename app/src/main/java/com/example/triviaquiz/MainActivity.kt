package com.example.triviaquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.triviaquiz.screens.TriviaHome
import com.example.triviaquiz.ui.theme.TriviaQuizTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TriviaQuizTheme {
                // A surface container using the 'background' color from the theme

                TriviaHome()

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TriviaQuizTheme {
        TriviaHome()
    }
}