package com.example.triviaquiz.repository

import com.example.triviaquiz.data.DataOrException
import com.example.triviaquiz.model.QuestionItem
import com.example.triviaquiz.network.QuestionApi
import javax.inject.Inject

class QuestionRepository @Inject constructor(private val api : QuestionApi){
    //API is like DAO
    /*listOfQuestions = ArrayList<QuestionItem>() could be able to hold questions too
    * but ArrayList<QuestionItem> can't add any metadata about the response we are getting from server
    * So,we used a WRAPPER class to wrap this arraylist into a different object
    * DataOrException class not only wraps ArrayList but also provides Boolean and Exception
    * if Boolean if true, it means the data is loading
    * if any Exceptions or error occurred, Exception will tell us
    */
    private val listOfQuestions =
        DataOrException<ArrayList<QuestionItem>,Boolean,Exception>()


}