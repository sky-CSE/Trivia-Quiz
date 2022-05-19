package com.example.triviaquiz.data
/*
** WRAPPER CLASS , helps to get metadata, also its done as its part of Clean Architecture
 */
data class DataOrException<T, Boolean, E : Exception>(
    var data: T? = null,
    var loading: Boolean? = null,
    var e: E? = null
)
