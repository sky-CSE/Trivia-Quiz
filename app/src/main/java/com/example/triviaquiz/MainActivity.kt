package com.example.triviaquiz

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.triviaquiz.screens.QuestionViewModel
import com.example.triviaquiz.ui.theme.TriviaQuizTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TriviaQuizTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TriviaHome()
                }
            }
        }
    }
}

@Composable
fun TriviaHome(viewModel: QuestionViewModel = hiltViewModel()) {
    Questions(viewModel)
}

@Composable
fun Questions(viewModel: QuestionViewModel) {
    //viewModel.data.value = dataOrException
    val questions = viewModel.data.value.data?.toMutableList() //toMutableList() is very imp

    if (viewModel.data.value.loading == true) {
        Log.d("LOADING", "Questions:.... loading...")
    } else {
        Log.d("LOADING", "Questions:....Loading Stopped...")
    }

    questions?.forEach { questionItem ->
        Log.d("RESULT", "Questions: ${questionItem.question}")
        Log.d("SIZE","Questions size : ${questions.size}")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TriviaQuizTheme {
        TriviaHome()
    }
}