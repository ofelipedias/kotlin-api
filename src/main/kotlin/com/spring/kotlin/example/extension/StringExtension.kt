package com.spring.kotlin.example.extension

import java.lang.Exception
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun String.isValidDate(dateTimeFormatter: DateTimeFormatter): Boolean {
    return try {
        LocalDateTime.parse(this, dateTimeFormatter)
        true
    } catch (e: Exception) {
        false
    }
}