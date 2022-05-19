package com.example.triviaquiz.repository

import android.util.Log
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

    //listOfQuestions renamed to 'dataOrException' as it is inside it
    private val dataOrException =
        DataOrException<ArrayList<QuestionItem>,Boolean,Exception>()

    suspend fun getAllQuestions() : DataOrException<ArrayList<QuestionItem>,Boolean,Exception>{
        try{
            dataOrException.loading = true
            dataOrException.data = api.getAllQuestions()
            if (dataOrException.data.toString().isNotEmpty()) dataOrException.loading = false
        }catch (exception: Exception){
            dataOrException.e = exception
            Log.d("Exc","getAllQuestions: ${dataOrException.e!!.localizedMessage}")
        }

        return dataOrException
    }


}