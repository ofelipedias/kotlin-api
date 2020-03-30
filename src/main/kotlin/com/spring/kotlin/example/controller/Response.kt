package com.spring.kotlin.example.controller

data class Response<T>(
        val errors: ArrayList<String> = arrayListOf(),
        var data: T? = null
)