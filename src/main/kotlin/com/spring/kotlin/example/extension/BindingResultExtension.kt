package com.spring.kotlin.example.extension

import com.spring.kotlin.example.controller.Response
import org.springframework.validation.BindingResult

fun <T> BindingResult.hasErrors(response: Response<T>): Boolean {
    if (hasErrors()) {
        allErrors.stream().forEach { e -> response.errors.add(e.defaultMessage!!) }
        return true
    }
    return false
}