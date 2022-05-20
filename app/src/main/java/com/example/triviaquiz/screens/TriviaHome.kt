package com.example.triviaquiz.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.triviaquiz.components.Questions


@Composable
fun TriviaHome(viewModel: QuestionViewModel = hiltViewModel()) {
    Questions(viewModel)
}