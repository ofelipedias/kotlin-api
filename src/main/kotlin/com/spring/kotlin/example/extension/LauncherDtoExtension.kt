package com.spring.kotlin.example.extension

import com.spring.kotlin.example.document.Launcher
import com.spring.kotlin.example.dto.LauncherDto
import com.spring.kotlin.example.enum.Type
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LauncherDto.toEntity(): Launcher {
    val dateTime = LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    return Launcher(dateTime, Type.valueOf(type!!), employeeId!!, description, location, id)
}

fun LauncherDto.dateValidate(bindingResult: BindingResult) {
    try {
        LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    } catch (e: Exception) {
        bindingResult.addError(ObjectError("launcher", "Date time format not accepted"))
    }
}