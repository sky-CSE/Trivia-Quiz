package com.example.triviaquiz.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.triviaquiz.model.QuestionItem
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

    //to select questions in order we have questionIndex
    val questionIndex = remember {
        mutableStateOf(1)
    }

    if (viewModel.data.value.loading == true) {
        CircularProgressIndicator()
    } else {
        //question is picked from here (picked acc. to index)
        val question = try {
            questions?.get(questionIndex.value)
        } catch (ex: Exception) {
            null
        }
        //if list of questions (questions) is not empty i.e. has question
        if (questions != null) {
            QuestionDisplay(
                question = question!!,
                questionIndex = questionIndex,
                viewModel = viewModel,
                onNextClicked = {
                    questionIndex.value = questionIndex.value + 1
                })
        }
    }
}

//@Preview
@Composable
fun QuestionDisplay(
    question: QuestionItem,
    questionIndex: MutableState<Int>,
    viewModel: QuestionViewModel,
    onNextClicked: (Int) -> Unit = {}
) {
    val choicesState = remember(question) {
        question.choices.toMutableList()
    }

    //the index of answer selected by user 
    val answerState = remember(question) {
        mutableStateOf<Int?>(null)
    }

    //whether the answer selected is correct or not
    val correctAnswerState = remember(question) {
        mutableStateOf<Boolean?>(null)
    }

    val checkAnswerState: (Int) -> Unit = remember(question) {
        {
            answerState.value = it //it refers to 'selected answer's index'

            //choicesState[idx] will give the string in that index
            if (choicesState[it] == question.answer) { //question.answer gives correct ans in string
                correctAnswerState.value = true
            } else {
                correctAnswerState.value = false
            }
        }

    }

    val score = remember {
        mutableStateOf(0)
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(), color = AppColors.mDarkPurple
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {

            //ELEMENT 0
            ShowProgress(score, questionIndex.value)

            //ELEMENT 1
            QuestionTracker(questionIndex.value, viewModel.getTotalQuestionCount())

            //ELEMENT 2
            val pathEffect = PathEffect.dashPathEffect(floatArrayOf(50f, 50f), 0f)
            DrawDottedLine(pathEffect = pathEffect)

            //ELEMENT 3
            Column {
                Text(
                    text = question.question,
                    modifier = Modifier
                        .padding(6.dp)
                        .align(alignment = Alignment.Start)
                        .fillMaxHeight(0.3f),//30% of whole screen
                    fontSize = 17.sp,
                    color = AppColors.mOffWhite,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 22.sp
                )

                //for each choice : index of choice , text of choice
                choicesState.forEachIndexed { index, choiceText ->
                    Row(
                        modifier = Modifier
                            .padding(3.dp)
                            .fillMaxWidth()
                            .height(55.dp)
                            .border(
                                width = 4.dp,
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        AppColors.mOffDarkPurple,
                                        AppColors.mOffDarkPurple
                                    )
                                ),
                                shape = RoundedCornerShape(15.dp)
                            )
                            .clip(
                                RoundedCornerShape(
                                    topStartPercent = 50,
                                    topEndPercent = 50,
                                    bottomEndPercent = 50,
                                    bottomStartPercent = 50
                                )
                            )
                            .background(Color.Transparent),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            //radiobutton is selected
                            // when 'index' of current choice = idx of answer(choice) user selected
                            selected = (answerState.value == index),
                            onClick = {
                                checkAnswerState(index)

                                //if answer is correct
                                if (correctAnswerState.value == true) {
                                    score.value += 1
                                }
                            },
                            modifier = Modifier.padding(start = 16.dp),
                            colors = RadioButtonDefaults.colors(
                                selectedColor =
                                //if answer is given right
                                if (correctAnswerState.value == true) {
                                    Color.Green.copy(alpha = 0.7f)
                                } else {
                                    Color.Red.copy(0.7f)
                                },

                                unselectedColor =
                                //SHOWING CORRECT ANSWER
                                if (correctAnswerState.value == false
                                    && index == choicesState.indexOf(question.answer)
                                ) {
                                    Color.Green.copy(alpha = 0.7f)
                                } else {
                                    Color.DarkGray
                                }
                            )
                        ) //end of radiobutton

                        Text(buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Light,
                                    color =
                                    //if its correct ans and user selected it
                                    //user selected bc 'green' to only single choice-text not all
                                    if (correctAnswerState.value == true && index == answerState.value) {
                                        Color.Green
                                    }
                                    //if answer is given wrong and the user selected it
                                    //user selected bc it only red the text which user selected
                                    else if (correctAnswerState.value == false && index == answerState.value) {
                                        Color.Red
                                    }
                                    //SHOWING CORRECT ANS
                                    //if answer given by user is wrong and the index of choice is index of correct answer i.e. correct answer
                                    else if (correctAnswerState.value == false && index == choicesState.indexOf(
                                            question.answer
                                        )
                                    ) {
                                        Color.Green
                                    } else {
                                        AppColors.mOffWhite
                                    }
                                )
                            ) {
                                append(text = choiceText)
                            }
                        }) //end of text
                    }//end of row
                }//end of choices

                Button(
                    onClick = { onNextClicked.invoke(questionIndex.value) },
                    modifier = Modifier
                        .padding(3.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(34.dp),
                    colors = buttonColors(
                        backgroundColor = AppColors.mLightBlue
                    )
                ) {
                    Text(
                        text = "Next",
                        modifier = Modifier.padding(4.dp),
                        color = AppColors.mOffWhite,
                        fontSize = 17.sp
                    )
                }
            }
        }
    }
}


@Preview
@Composable
private fun ShowProgress(score: MutableState<Int> = mutableStateOf(0), questionIndex: Int = 1) {
    val gradient = Brush.linearGradient(colors = listOf(Color(0xFFF95075), Color(0xFFBE6BE5)))

    val progressFactor = remember(questionIndex) {
        questionIndex * 0.005f //tiny factor means more time to fill
    }
    Row(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth()
            .height(45.dp)
            .border(
                width = 2.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        AppColors.mLightPurple,
                        AppColors.mLightPurple
                    )
                ),
                shape = RoundedCornerShape(34.dp)
            )
            .clip(
                RoundedCornerShape(
                    topStartPercent = 50,
                    topEndPercent = 50,
                    bottomEndPercent = 50,
                    bottomStartPercent = 50
                )
            )
            .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically
    )
    {
        //filled progress is given by button here
        Button(
            contentPadding = PaddingValues(1.dp),
            onClick = {},
            modifier = Modifier
                .fillMaxWidth(progressFactor) //added progressFactor here
                .background(brush = gradient),
            enabled = false,
            elevation = null,
            colors = buttonColors(
                backgroundColor = Color.Transparent,
                disabledBackgroundColor = Color.Transparent
            )
        ) {
            Text(
                text = "${score.value}",
                modifier = Modifier
                    .fillMaxHeight(0.7f)
                    .padding(2.dp),
                color = AppColors.mOffWhite,
                textAlign = TextAlign.Start
            )
        }
    }
}

@Composable
fun QuestionTracker(counter: Int = 1, outOf: Int = 100) {

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

@Preview
@Composable
fun DrawDottedLine(pathEffect: PathEffect = PathEffect.dashPathEffect(floatArrayOf(50f,50f),0f)) {
    Canvas(modifier = Modifier
        .fillMaxWidth()
        .height(1.dp),
        onDraw = {
            drawLine(
                color = AppColors.mLightGray,
                start = Offset(0f, 0f),
                end = Offset(size.width, y = 0f),
                pathEffect = pathEffect
            )
        })
}