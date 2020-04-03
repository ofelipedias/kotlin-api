package com.spring.kotlin.example.extension

import com.spring.kotlin.example.document.Launcher
import com.spring.kotlin.example.dto.LauncherDto
import com.spring.kotlin.example.enum.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LauncherDto.toEntity(): Launcher {
    val dateTime = LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    return Launcher(dateTime, Type.valueOf(type!!), employeeId!!, description, location, id)
}