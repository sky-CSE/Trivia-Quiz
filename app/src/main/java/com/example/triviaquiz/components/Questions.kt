package com.example.triviaquiz.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.triviaquiz.screens.QuestionViewModel
import com.example.triviaquiz.util.AppColors


@Composable
fun Questions(viewModel: QuestionViewModel) {
    /**
     * Important note: since, Question class returns an
     * ArrayList, we must convert the values of that type
     * into a MutableList() in order to get the size(), and
     * any other methods... if we don't convert to MutableList
     * we receive a nasty "NonSuchMethod" exception.
     *
     */
    //viewModel.data.value = dataOrException
    val questions = viewModel.data.value.data?.toMutableList() //toMutableList() is very imp

    if (viewModel.data.value.loading == true) {
        CircularProgressIndicator()
        //Log.d("LOADING", "Questions:.... loading...")
    } else {
        //Log.d("LOADING", "Questions:....Loading Stopped...")
    }

    questions?.forEach { questionItem ->
        Log.d("RESULT", "Questions: ${questionItem.question}")
        Log.d("SIZE", "Questions size : ${questions.size}")
    }
}

@Preview
@Composable
fun QuestionDisplay() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(4.dp), color = AppColors.mDarkPurple
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            QuestionTracker()
        }
    }
}

@Preview
@Composable
fun QuestionTracker(counter: Int = 10, outOf: Int = 100) {

    Text(text = buildAnnotatedString {
        withStyle(style = ParagraphStyle(textIndent = TextIndent.None)) {
            withStyle(
                style = SpanStyle(
                    color = AppColors.mLightGray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 27.sp
                )
            ) {
                append("Question $counter/")
                withStyle(
                    style = SpanStyle(
                        color = AppColors.mLightGray,
                        fontWeight = FontWeight.Light,
                        fontSize = 14.sp
                    )
                ) {
                    append("$outOf")
                }
            }
        }
    }, modifier = Modifier.padding(20.dp))
}